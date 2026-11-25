package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.GO_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.contract.ContractDto
import org.homesharing.lockbox.data.network.dto.contract.ContractDraftDto
import org.homesharing.lockbox.data.network.dto.contract.ContractPageDto
import org.homesharing.lockbox.data.network.dto.contract.ContractPdfDto

private const val CONTRACTS_URL = "$GO_BASE_ENDPOINT/contracts"

internal class ContractApiImpl(
    private val client: HttpClient,
    private val json: Json,
) : ContractApi {
    override suspend fun getContracts(
        limit: Int,
        cursor: String?,
    ): ApiResponse<ContractPageDto> =
        client.get {
            url("$CONTRACTS_URL")
            parameter("limit", limit)
            parameter("cursor", cursor)
        }.toApiResponse(json)

    override suspend fun createContract(request: ContractDraftDto): ApiResponse<ContractDto> =
        client.post {
            url("$CONTRACTS_URL")
            setBody(request)
        }.toApiResponse(json)

    override suspend fun getContract(id: String): ApiResponse<ContractDto> =
        client.get {
            url("$CONTRACTS_URL/$id")
        }.toApiResponse(json)

    override suspend fun updateContract(
        id: String,
        request: ContractDraftDto,
    ): ApiResponse<ContractDto> =
        client.put {
            url("$CONTRACTS_URL/$id")
            setBody(request)
        }.toApiResponse(json)

    override suspend fun deleteContract(id: String): ApiResponse<Unit> =
        client.delete {
            url("$CONTRACTS_URL/$id")
        }.toApiResponse(json)

    override suspend fun getContractPdf(id: String): ApiResponse<ContractPdfDto> =
        client.get {
            url("$CONTRACTS_URL/$id/pdf")
        }.toApiResponse(json)
}
