package org.homesharing.lockbox.data.network.api

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class LockBoxBoxesApiTest {

    private lateinit var api: LockBoxBoxesApi
    private lateinit var client: HttpClient

    private val headers = headersOf(
        HttpHeaders.ContentType,
        "${ContentType.Application.Json.contentType}/${ContentType.Application.Json.contentSubtype}"
    )

    private val mockRequestHandler: MockRequestHandler = { request ->
        val path = request.url.toString()
        when {
            path == "$BOXES_URL?size=10&page=0" && request.method == HttpMethod.Get ->
                respond(
                    content =
                        """
                        {
                            "body": {
                                "boxes": [
                                    {
                                        "address": "A1",
                                        "alias": "BoxA",
                                        "owner_sub": "o1",
                                        "battery_level": 50,
                                        "mac_address": "MAC1",
                                        "opened": true,
                                        "signal_strength": -40,
                                        "timestamp": "2025-06-10T12:00:00Z"
                                    }
                                ],
                                "total_boxes": 1,
                                "current_page": 0,
                                "boxes_on_page": 1
                            },
                            "error": false,
                            "error_text": null
                        }
                    """,
                    headers = headers
                )

            path == "$BOXES_URL/MAC1" && request.method == HttpMethod.Get ->
                respond(
                    content =
                        """
                        {
                            "body": {
                                "address": "A1",
                                "alias": "BoxA",
                                "owner_sub": "o1",
                                "battery_level": 50,
                                "mac_address": "MAC1",
                                "opened": false,
                                "signal_strength": -70,
                                "timestamp": "2025-06-11T08:00:00Z"
                            },
                            "error": false,
                            "error_text": null
                        }
                        """,
                    headers = headers
                )

            path == "$BOXES_URL/MAC1/$STATUS" ->
                respond(
                    content =
                        """
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
                    headers = headers
                )

            path == "$BOXES_URL/MAC1" && request.method == HttpMethod.Patch ->
                respond(
                    content =
                        """
                        {
                            "body": {
                                "mac_address": "MAC1",
                                "alias": "BoxB",
                                "address": "B2"
                            },                      
                            "error": false,
                            "error_text": null
                        }""",
                    headers = headers
                )

            request.method == HttpMethod.Post && path == "$BOXES_URL/$AUTH/$OPEN/MAC1" -> respond(
                content = """
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
                headers = headers
            )


            request.method == HttpMethod.Delete && path == "$BOXES_URL/MAC1" -> respond(
                content = """
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
                headers = headers
            )


            request.method == HttpMethod.Get && path == "$BOXES_URL/$ACCESS/MAC1/$ACCESSES" -> respond(
                content = """
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
                headers = headers
            )


            request.method == HttpMethod.Post && path == "$BOXES_URL/$ACCESS/MAC1/$ACCESSES" -> respond(
                content = """
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
                headers = headers
            )


            request.method == HttpMethod.Delete && path == "$BOXES_URL/$ACCESS/MAC1/$ACCESSES/tok-id" -> respond(
                content = """
                            {
                              "body": "revoked",
                              "error": false,
                              "error_text": null
                            }
                        """,
                headers = headers
            )


            else -> error("Unhandled ${request.method} ${request.url}")
        }
    }

    @BeforeTest
    fun setUp() {
        val mockEngine = MockEngine(mockRequestHandler)

        client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    },
                )
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }

        api = LockBoxBoxesApi(client)
    }

    @Test
    fun `getBoxes returns BoxListResponseDto on success`() = runBlocking {
        val result = api.getBoxes("token", page = 0, size = 10)

        assertFalse(result.error)
        assertNull(result.errorText)
        assertNotNull(result.body)
        assertEquals(1, result.body.boxes.size)
        assertEquals(1, result.body.totalBoxes)
        assertEquals(0, result.body.currentPage)
        assertEquals(1, result.body.boxesOnPage)
    }

    @Test
    fun `getBoxByMac returns BoxResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val expectedAddress = "A1"
        val expectedAlias = "BoxA"

        val result = api.getBoxByMac(token, mac)

        assertFalse(result.error)
        assertNull(result.errorText)
        assertNotNull(result.body)

        assertEquals(expectedAddress, result.body.address)
        assertEquals(expectedAlias, result.body.alias)
    }

    @Test
    fun `getBoxStatusByMac returns BoxStatusResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"

        val result = api.getBoxStatusByMac(token, mac)

        assertFalse(result.error)
        assertNull(result.errorText)
        assertNotNull(result.body)

        assertEquals(mac, result.body.macAddress)
    }

    @Test
    fun `updateBoxByMac returns UpdateBoxResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val newAlias = "BoxB"
        val newAddress = "B2"

        val result = api.updateBoxByMac(token, mac, alias = newAlias, address = newAddress)

        assertFalse(result.error)
        assertNull(result.errorText)
        assertNotNull(result.body)

        assertEquals(newAlias, result.body.alias)
        assertEquals(newAddress, result.body.address)
    }

    @Test
    fun `openBox returns OpenBoxResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val expectedCall = "CALL123"

        val result = api.openBox(token, mac)

        assertFalse(result.error)
        assertNull(result.errorText)
        assertNotNull(result.body)
        assertEquals(expectedCall, result.body.call)
    }

    @Test
    fun `deleteBox returns UpdateBoxResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val expectedAlias = "BoxC"
        val expectedAddress = "C3"

        val result = api.deleteBox(token, mac)

        assertFalse(result.error)
        assertNull(result.errorText)
        assertNotNull(result.body)

        assertEquals(expectedAlias, result.body.alias)
        assertEquals(expectedAddress, result.body.address)
    }

    @Test
    fun `getAccessedList returns AccessResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val expectedSize = 1

        val result = api.getAccessedList(token, mac)

        assertFalse(result.error)
        assertNull(result.errorText)
        assertNotNull(result.body)

        assertEquals(expectedSize, result.body.listOfAccesses.size)
    }

    @Test
    fun `createAccess returns AccessDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val alias = "A2"
        val start = "2025-06-11T10:00:00"
        val end = "2025-06-11T11:00:00"
        val expectedUuid = "id2"

        val result = api.createAccess(token, mac, alias = alias, startTime = start, endTime = end)

        assertFalse(result.error)
        assertNull(result.errorText)
        assertNotNull(result.body)

        assertEquals(alias, result.body.alias)
        assertEquals(expectedUuid, result.body.uuid)
    }

    @Test
    fun `revokeAccess returns AccessRevokeResponseDto on success`() = runBlocking {
        val token = "token"
        val mac = "MAC1"
        val tokenId = "tok-id"
        val expectedResult = "revoked"

        val result = api.revokeAccess(token, mac, tokenId)

        assertFalse(result.error)
        assertNull(result.errorText)
        assertNotNull(result.body)

        assertEquals(expectedResult, result.body.result)
    }
}
