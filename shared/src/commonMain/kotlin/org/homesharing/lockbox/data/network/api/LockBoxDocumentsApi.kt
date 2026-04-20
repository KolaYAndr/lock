package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.GO_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.contact_file.ContractFileDto
import org.homesharing.lockbox.data.network.dto.contract_detailed.ContractDetailedDto
import org.homesharing.lockbox.data.network.dto.contract_list.ContractListDto
import org.homesharing.lockbox.data.network.dto.flat.FlatDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatListDto

private const val FLATS = "flats"
private const val CONTRACTS = "contracts"
private const val PDF = "pdf"

private const val CURSOR = "cursor"
private const val LIMIT = "limit"

private const val FLATS_URL = "$GO_BASE_ENDPOINT/$FLATS"
private const val CONTRACTS_URL = "$GO_BASE_ENDPOINT/$CONTRACTS"

internal class LockBoxDocumentsApi(
    private val client: HttpClient,
    private val json: Json,
) : DocumentsApi {
    override suspend fun getContracts(cursor: String?, limit: Int): ApiResponse<ContractListDto> {
        return client.get {
            url(CONTRACTS_URL)
            parameter(LIMIT, limit)
            cursor?.let { parameter(CURSOR, it) }
        }.toApiResponse<ContractListDto>(json)
    }

    override suspend fun deleteContract(id: String): ApiResponse<Unit> {
        return client.delete {
            url("$CONTRACTS_URL/$id")
        }.toApiResponse<Unit>(json)
    }

    override suspend fun getContract(id: String): ApiResponse<ContractDetailedDto> {
        return client.get {
            url("$CONTRACTS_URL/$id")
        }.toApiResponse<ContractDetailedDto>(json)
    }

    override suspend fun getContractFile(id: String): ApiResponse<ContractFileDto> {
        return client.get {
            url("$CONTRACTS_URL/$id/$PDF")
        }.toApiResponse<ContractFileDto>(json)
    }

    override suspend fun getFlats(cursor: String, limit: Int): ApiResponse<FlatListDto> {
        return client.get {
            parameter(CURSOR, cursor)
            parameter(LIMIT, limit)
            url(FLATS_URL)
        }.toApiResponse<FlatListDto>(json)
    }

    override suspend fun createFlat(): ApiResponse<FlatDto> {
        return client.post {
            url(FLATS_URL)
        }.toApiResponse<FlatDto>(json)
    }
}