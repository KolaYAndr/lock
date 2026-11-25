package org.homesharing.lockbox

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.homesharing.lockbox.di.sharedAppModules
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.contract.ContractPdf
import org.homesharing.lockbox.domain.model.dashboard.GuestDashboard
import org.homesharing.lockbox.domain.model.flat_detail.FlatDetail
import org.homesharing.lockbox.domain.model.flat_detail.FlatStatus
import org.homesharing.lockbox.domain.model.profile_info.ProfileInfo
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.domain.repository.ContractRepository
import org.homesharing.lockbox.domain.repository.DashboardRepository
import org.homesharing.lockbox.domain.repository.FlatRepository
import org.homesharing.lockbox.domain.repository.InvitationRepository
import org.koin.core.Koin
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GoApiMigrationTest {
    @Test
    fun archiveDecodesFlatWithoutOptionalFields() = runTest {
        withRepositories(
            body = """
                {"id":"flat-1","name":"Flat","premise_type":"residential","address":{"city":"Moscow"},
                "full_address":"Moscow","rooms":[],"status":"archived","photos":[],
                "invites_count":0,"active_invites_count":0,"has_documents":false}
            """.trimIndent(),
            inspectRequest = {
                assertEquals(HttpMethod.Post, it.method)
                assertEquals("/api/flats/flat-1/archive", it.url.encodedPath)
            },
        ) {
            val response = assertIs<Response.Success<*>>(get<FlatRepository>().archiveFlat("flat-1"))
            val flat = assertIs<FlatDetail>(response.data)
            assertEquals(FlatStatus.Archived, flat.status)
            assertNull(flat.invites)
        }
    }

    @Test
    fun reorderSendsOrderedPhotoIdsAsJson() = runTest {
        withRepositories(
            body = """{"items":[{"id":"two","url":"https://example.test/two","position":0}]}""",
            inspectRequest = {
                assertEquals(HttpMethod.Put, it.method)
                assertEquals("/api/flats/flat-1/photos/order", it.url.encodedPath)
                val requestBody = assertIs<TextContent>(it.body)
                assertEquals(
                    Json.parseToJsonElement("""{"photo_ids":["two","one"]}""").jsonObject,
                    Json.parseToJsonElement(requestBody.text).jsonObject,
                )
            },
        ) {
            val response = get<FlatRepository>().reorderPhotos("flat-1", listOf("two", "one"))
            val photos = assertIs<Response.Success<List<FlatDetail.Photo>>>(response).data
            assertEquals("two", photos.single().id)
        }
    }

    @Test
    fun uploadPhotoUsesMultipartAndDecodesCreatedPhoto() = runTest {
        withRepositories(
            body = """{"id":"photo-1","url":"https://example.test/photo","position":0}""",
            status = HttpStatusCode.Created,
            inspectRequest = {
                assertEquals(HttpMethod.Post, it.method)
                assertEquals("/api/flats/flat-1/photos", it.url.encodedPath)
                assertTrue(it.body.contentType?.match(ContentType.MultiPart.FormData) == true)
            },
        ) {
            val response = get<FlatRepository>().uploadPhoto("flat-1", byteArrayOf(1), "photo.jpg", "image/jpeg")
            val photo = assertIs<Response.Success<FlatDetail.Photo>>(response).data
            assertEquals("photo-1", photo.id)
        }
    }

    @Test
    fun guestInvitePreservesBackendErrorCodeAndEncodesToken() = runTest {
        withRepositories(
            body = """{"error_code":"INVITE_EXPIRED","message":"Invite expired"}""",
            status = HttpStatusCode.Gone,
            inspectRequest = {
                assertEquals(HttpMethod.Get, it.method)
                assertEquals("/api/invites/by-token/a%2Fb%3Fx", it.url.encodedPath)
            },
        ) {
            val response = get<InvitationRepository>().getGuestInvite("a/b?x")
            assertEquals("INVITE_EXPIRED", assertIs<Response.Failure>(response).errorCode)
        }
    }

    @Test
    fun contractPdfReturnsStatusAndDownloadUrl() = runTest {
        withRepositories(
            body = """{"id":"contract-1","document_status":"created","download_url":"https://example.test/contract.pdf"}""",
            inspectRequest = {
                assertEquals(HttpMethod.Get, it.method)
                assertEquals("/api/contracts/contract-1/pdf", it.url.encodedPath)
            },
        ) {
            val response = get<ContractRepository>().getContractPdf("contract-1")
            val pdf = assertIs<Response.Success<ContractPdf>>(response).data
            assertEquals("created", pdf.documentStatus)
            assertEquals("https://example.test/contract.pdf", pdf.downloadUrl)
        }
    }

    @Test
    fun deleteContractHandlesNoContent() = runTest {
        withRepositories(
            body = "",
            status = HttpStatusCode.NoContent,
            inspectRequest = {
                assertEquals(HttpMethod.Delete, it.method)
                assertEquals("/api/contracts/contract-1", it.url.encodedPath)
            },
        ) {
            assertEquals(Response.Success(Unit), get<ContractRepository>().deleteContract("contract-1"))
        }
    }

    @Test
    fun dashboardDecodesMinimalGuestState() = runTest {
        withRepositories(
            body = """{"stage":"no_invite"}""",
            inspectRequest = { assertEquals("/api/guest/dashboard", it.url.encodedPath) },
        ) {
            val response = get<DashboardRepository>().getGuestDashboard()
            val dashboard = assertIs<Response.Success<GuestDashboard>>(response).data
            assertEquals("no_invite", dashboard.stage)
            assertNull(dashboard.invite)
        }
    }

    @Test
    fun profileDecodesThePublishedMinimalResponse() = runTest {
        withRepositories(
            body = """{"id":"user-1"}""",
            inspectRequest = { assertEquals("/api/profile-info", it.url.encodedPath) },
        ) {
            val response = get<AccountRepository>().getProfileInfo()
            val profile = assertIs<Response.Success<ProfileInfo>>(response).data
            assertEquals("user-1", profile.id)
            assertNull(profile.activeRole)
            assertNull(profile.contactInfo)
        }
    }

    private suspend fun withRepositories(
        body: String,
        status: HttpStatusCode = HttpStatusCode.OK,
        inspectRequest: (HttpRequestData) -> Unit,
        test: suspend Koin.() -> Unit,
    ) {
        val codec = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
        val client = HttpClient(MockEngine {
            inspectRequest(it)
            respond(body, status, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()))
        }) {
            install(ContentNegotiation) { json(codec) }
            defaultRequest { contentType(ContentType.Application.Json) }
        }
        val application = koinApplication {
            modules(sharedAppModules + module {
                single<HttpClient> { client }
                single<Json> { codec }
            })
        }
        try {
            application.koin.test()
        } finally {
            application.close()
            client.close()
        }
    }
}
