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
import org.homesharing.lockbox.data.network.dto.contract.ContractCancelRequestDto
import org.homesharing.lockbox.data.network.dto.contract.ContractCreateRequestDto
import org.homesharing.lockbox.data.network.dto.contract.ContractDto
import org.homesharing.lockbox.data.network.dto.contract.ContractRejectRequestDto
import org.homesharing.lockbox.data.network.dto.contract_confirmation.ContractConfirmRequestDto
import org.homesharing.lockbox.data.network.dto.contract_confirmation.ContractConfirmationRequestDto
import org.homesharing.lockbox.data.network.dto.contract_confirmation.OtpRequestResponseDto
import org.homesharing.lockbox.data.network.dto.contract_list.ContractListDto
import org.homesharing.lockbox.data.network.dto.contract_pdf.ContractPdfDto
import org.homesharing.lockbox.data.network.dto.contract_version.ContractVersionDto
import org.homesharing.lockbox.data.network.dto.contract_version.ContractVersionListDto

// API endpoints
private const val CONTRACTS = "contracts"
private const val PDF = "pdf"
private const val PREPARE = "prepare"
private const val VERSIONS = "versions"
private const val CONFIRMATION_CODE = "confirmation-code"
private const val CONFIRM = "confirm"
private const val REJECT = "reject"
private const val CANCEL = "cancel"

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

    override suspend fun getContractPdf(id: String): ApiResponse<ContractPdfDto> {
        return client.get {
            url("$CONTRACTS_URL/$id/$PDF")
        }.toApiResponse<ContractPdfDto>(json)
    }

    override suspend fun prepareContract(id: String): ApiResponse<ContractVersionDto> {
        return client.post {
            url("$CONTRACTS_URL/$id/$PREPARE")
        }.toApiResponse<ContractVersionDto>(json)
    }

    override suspend fun getContractVersions(id: String): ApiResponse<ContractVersionListDto> {
        return client.get {
            url("$CONTRACTS_URL/$id/$VERSIONS")
        }.toApiResponse<ContractVersionListDto>(json)
    }

    override suspend fun getContractVersion(
        id: String,
        versionId: String,
    ): ApiResponse<ContractVersionDto> {
        return client.get {
            url("$CONTRACTS_URL/$id/$VERSIONS/$versionId")
        }.toApiResponse<ContractVersionDto>(json)
    }

    override suspend fun requestConfirmationCode(
        id: String,
        request: ContractConfirmationRequestDto,
    ): ApiResponse<OtpRequestResponseDto> {
        return client.post {
            url("$CONTRACTS_URL/$id/$CONFIRMATION_CODE")
            setBody(request)
        }.toApiResponse<OtpRequestResponseDto>(json)
    }

    override suspend fun confirmContract(
        id: String,
        request: ContractConfirmRequestDto,
    ): ApiResponse<ContractDto> {
        return client.post {
            url("$CONTRACTS_URL/$id/$CONFIRM")
            setBody(request)
        }.toApiResponse<ContractDto>(json)
    }

    override suspend fun rejectContract(
        id: String,
        request: ContractRejectRequestDto,
    ): ApiResponse<ContractDto> {
        return client.post {
            url("$CONTRACTS_URL/$id/$REJECT")
            setBody(request)
        }.toApiResponse<ContractDto>(json)
    }

    override suspend fun cancelContract(
        id: String,
        request: ContractCancelRequestDto,
    ): ApiResponse<ContractDto> {
        return client.post {
            url("$CONTRACTS_URL/$id/$CANCEL")
            setBody(request)
        }.toApiResponse<ContractDto>(json)
    }
}
