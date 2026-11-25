package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
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
import org.homesharing.lockbox.data.network.dto.contract.ContractCreateRequestDto
import org.homesharing.lockbox.data.network.dto.contract.ContractDto
import org.homesharing.lockbox.data.network.dto.contract_list.ContractListDto

// API endpoints
private const val CONTRACTS = "contracts"

// API arguments
private const val LIMIT = "limit"
private const val CURSOR = "cursor"

// API url
private const val CONTRACTS_URL = "$GO_BASE_ENDPOINT/$CONTRACTS"

internal class ContractsApiImpl(
    private val client: HttpClient,
    private val json: Json,
) : ContractsApi {
    override suspend fun getContracts(
        limit: Int,
        cursor: String?,
    ): ApiResponse<ContractListDto> {
        return client.get {
            url(CONTRACTS_URL)
            parameter(LIMIT, limit)
            cursor?.let { parameter(CURSOR, it) }
        }.toApiResponse<ContractListDto>(json)
    }

    override suspend fun createContract(
        request: ContractCreateRequestDto,
    ): ApiResponse<ContractDto> {
        return client.post {
            url(CONTRACTS_URL)
            setBody(request)
        }.toApiResponse<ContractDto>(json)
    }

    override suspend fun getContract(id: String): ApiResponse<ContractDto> {
        return client.get {
            url("$CONTRACTS_URL/$id")
        }.toApiResponse<ContractDto>(json)
    }

    override suspend fun updateContract(
        id: String,
        request: ContractCreateRequestDto,
    ): ApiResponse<ContractDto> {
        return client.put {
            url("$CONTRACTS_URL/$id")
            setBody(request)
        }.toApiResponse<ContractDto>(json)
    }
}
