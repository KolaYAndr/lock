package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandler
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.access_revoke.AccessRevokeResponseDto
import org.homesharing.lockbox.data.network.dto.access.AccessDto
import org.homesharing.lockbox.data.network.dto.access.AccessResponseDto
import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto
import org.homesharing.lockbox.data.network.dto.box_list.BoxListResponseDto
import org.homesharing.lockbox.data.network.dto.box_status.BoxStatusResponseDto
import org.homesharing.lockbox.data.network.dto.create_linking_code.CreateLinkingCodeDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxResponseDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("UNCHECKED_CAST")
class LockBoxBoxesApiTest {
    private lateinit var api: LockBoxBoxesApi

    @BeforeTest
    fun setUp() {
        api = LockBoxBoxesApi(mockBoxesClient, json)
    }

    @Test
    fun `getBoxes returns BoxListResponseDto on success`() = runBlocking {
        val result =
            api.getBoxes("token", page = 0, size = 10) as ApiResponse.Success<BoxListResponseDto>

        assertEquals(1, result.data.boxes.size)
        assertEquals(1, result.data.total)
        assertEquals(0, result.data.page)
        assertEquals(1, result.data.size)
    }

    @Test
    fun `getBoxByMac returns BoxResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val expectedAddress = "A1"
        val expectedAlias = "BoxA"

        val result = api.getBoxByMac(token, mac) as ApiResponse.Success<BoxResponseDto>

        assertEquals(expectedAddress, result.data.address)
        assertEquals(expectedAlias, result.data.alias)
    }

    @Test
    fun `getBoxStatusByMac returns BoxStatusResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"

        val result = api.getBoxStatusByMac(token, mac) as ApiResponse.Success<BoxStatusResponseDto>

        assertEquals(mac, result.data.macAddress)
    }

    @Test
    fun `updateBoxByMac returns UpdateBoxResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val newAlias = "BoxB"
        val newAddress = "B2"

        val result = api.updateBoxByMac(
            token,
            mac,
            alias = newAlias,
            address = newAddress
        ) as ApiResponse.Success<UpdateBoxResponseDto>

        assertEquals(newAlias, result.data.alias)
        assertEquals(newAddress, result.data.address)
    }

    @Test
    fun `openBox returns OpenBoxResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val expectedCall = "CALL123"

        val result = api.openBox(token, mac) as ApiResponse.Success<OpenBoxResponseDto>

        assertEquals(expectedCall, result.data.call)
    }

    @Test
    fun `deleteBox returns UpdateBoxResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val expectedAlias = "BoxC"
        val expectedAddress = "C3"

        val result = api.deleteBox(token, mac) as ApiResponse.Success<UpdateBoxResponseDto>

        assertEquals(expectedAlias, result.data.alias)
        assertEquals(expectedAddress, result.data.address)
    }

    @Test
    fun `getAccessedList returns AccessResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val expectedSize = 1

        val result = api.getAccessedList(token, mac) as ApiResponse.Success<AccessResponseDto>

        assertEquals(expectedSize, result.data.listOfAccesses.size)
    }

    @Test
    fun `createAccess returns AccessDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val alias = "A2"
        val start = "2025-06-11T10:00:00"
        val end = "2025-06-11T11:00:00"
        val expectedUuid = "id2"

        val result = api.createAccess(
            token,
            mac,
            alias = alias,
            startTime = start,
            endTime = end
        ) as ApiResponse.Success<AccessDto>

        assertEquals(alias, result.data.alias)
        assertEquals(expectedUuid, result.data.uuid)
    }

    @Test
    fun `revokeAccess returns AccessRevokeResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val tokenId = "tok-id"
        val expectedResult = "revoked"

        val result =
            api.revokeAccess(token, mac, tokenId) as ApiResponse.Success<AccessRevokeResponseDto>

        assertEquals(expectedResult, result.data.result)
    }

    @Test
    fun `createLinkingCode returns CreateLinkingCode on success`() = runBlocking {
        val token = "token"
        val expectedCode = 298545

        val result = api.createLinkingCode(token) as ApiResponse.Success<CreateLinkingCodeDto>

        assertEquals(expectedCode, result.data.code)
    }

    companion object {
        private val urlToContent: Map<String, String> = mapOf(
            "$BOXES_URL/$BOXES?size=10&page=0/${HttpMethod.Get.value}" to """
                {
                 "boxes": [
                   {
                     "box": {
                       "macAddress": "AA:BB:CC:DD:EE:FF",
                       "ownerSub": "user-abcdef123456",
                       "address": "ул. Ленина, д. 10, офис 5",
                       "alias": "Офисный бокс"
                     },
                     "status": {
                       "macAddress": "AA:BB:CC:DD:EE:FF",
                       "batteryLevel": 86.5,
                       "opened": false,
                       "signalStrength": -67,
                       "timestamp": "2025-07-15T10:23:45+02:00"
                     }
                   }
                 ],
                 "total": 1,
                 "page": 0,
                 "size": 1
                }
    """,

            "$BOXES_URL/MAC1/${HttpMethod.Get.value}" to """
        {
            "body": {
                "address": "A1",
                "alias": "BoxA",
                "owner_sub":"o1",
                "battery_level": 50,
                "mac_address":"MAC1",
                "opened":false,
                "signal_strength":-70,
                "timestamp":"2025-06-11T08:00:00Z"
            },
            "error": false,
            "error_text": null
        }
    """,

            "$BOXES_URL/MAC1/$STATUS/${HttpMethod.Get.value}" to """
        {
            "body": {
                "mac_address": "MAC1",
                "battery_level": 20.23939334833422,
                "signal_strength": -61.65466085511442,
                "opened": false,
                "timestamp": "2025-02-20T01:38:59"
            },
            "error": false,
            "error_text": null
        }
    """,

            "$BOXES_URL/MAC1/${HttpMethod.Patch.value}" to """
        {
            "body": {
                "mac_address": "MAC1",
                "alias": "BoxB",
                "address": "B2"
            },
            "error": false,
            "error_text": null
        }
    """,

            "$BOXES_URL/$AUTH/$OPEN/MAC1/${HttpMethod.Post.value}" to """
        {
            "body": {
                "call": "CALL123",
                "id": 1,
                "issuer_sub": "iss",
                "issuer_token": null,
                "mac_address": "MAC1"
            },
            "error": false,
            "error_text": null
        }
    """,

            "$BOXES_URL/MAC1/${HttpMethod.Delete.value}" to """
        {
            "body": {
                "address": "C3",
                "alias": "BoxC",
                "mac_address": "MAC1"
            },
            "error": false,
            "error_text": null
        }
    """,

            "$BOXES_URL/$ACCESS/MAC1/$ACCESSES/${HttpMethod.Get.value}" to """
        {
            "body": [
                {
                    "alias": "A1",
                    "start_time": "2025-06-10T08:00:00",
                    "end_time": "2025-06-10T09:00:00",
                    "mac_address": "MAC1",
                    "token": "tok",
                    "uuid": "id1"
                }
            ],
            "error": false,
            "error_text": null
        }
    """,

            "$BOXES_URL/$ACCESS/MAC1/$ACCESSES/${HttpMethod.Post.value}" to """
        {
            "body": {
                "alias": "A2",
                "start_time": "2025-06-11T10:00:00",
                "end_time": "2025-06-11T11:00:00",
                "mac_address": "MAC1",
                "token": "tok2",
                "uuid": "id2"
            },
            "error": false,
            "error_text": null
        }
    """,

            "$BOXES_URL/$ACCESS/MAC1/$ACCESSES/tok-id/${HttpMethod.Delete.value}" to """
        {
            "body": "revoked",
            "error": false,
            "error_text": null
        }
    """,

            "$BOXES_URL/$SIGNUP/${HttpMethod.Post.value}" to """
                {
                    "body": {
                        "code": 298545
                    },
                    "error": false,
                    "error_text": null
                }
            """,

            "$BOXES_URL/$DEVICES/$BOXES/$SIGNUP/${HttpMethod.Post.value}" to """
                {
                  "macAddress": "AA:BB:CC:DD:EE:FF",
                  "ownerSub": "user-abcdef123456",
                  "address": "ул. Ленина, д. 10, офис 5",
                  "alias": "Офисный бокс"
                }
            """
        )

        private val mockRequestHandler: MockRequestHandler = { request ->
            val baseUrl = request.url.toString()
            val key = "$baseUrl/${request.method.value}"

            val content = urlToContent[key]
                ?: error("Unhandled request: ${request.url} ${request.method}")

            respond(content = content, headers = headers)
        }

        private val headers = headersOf(
            HttpHeaders.ContentType,
            "${ContentType.Application.Json.contentType}/${ContentType.Application.Json.contentSubtype}"
        )

        private val mockEngine = MockEngine(mockRequestHandler)

        private val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }

        val mockBoxesClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(
                    json = json,
                )
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}
