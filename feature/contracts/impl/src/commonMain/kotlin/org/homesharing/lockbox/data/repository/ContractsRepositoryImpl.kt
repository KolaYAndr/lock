package org.homesharing.lockbox.data.repository

import org.homesharing.lockbox.data.network.api.ContractsApi
import org.homesharing.lockbox.data.network.dto.contract.ContractCreateRequestDto
import org.homesharing.lockbox.data.network.dto.contract.ContractDto
import org.homesharing.lockbox.data.network.dto.contract_confirmation.ContractConfirmationRequestDto
import org.homesharing.lockbox.data.network.dto.contract_confirmation.OtpRequestResponseDto
import org.homesharing.lockbox.data.network.dto.contract_list.ContractListDto
import org.homesharing.lockbox.data.network.dto.contract_pdf.ContractPdfDto
import org.homesharing.lockbox.data.network.dto.contract_version.ContractVersionDto
import org.homesharing.lockbox.data.network.dto.contract_version.ContractVersionListDto
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.contract.Contract
import org.homesharing.lockbox.domain.model.contract.ContractParty
import org.homesharing.lockbox.domain.model.contract.toDomain
import org.homesharing.lockbox.domain.model.contract.toDto
import org.homesharing.lockbox.domain.model.contract.toRequestDto
import org.homesharing.lockbox.domain.model.contract_confirmation.OtpRequestResponse
import org.homesharing.lockbox.domain.model.contract_confirmation.toDomain
import org.homesharing.lockbox.domain.model.contract_list.ContractList
import org.homesharing.lockbox.domain.model.contract_list.toDomain
import org.homesharing.lockbox.domain.model.contract_pdf.ContractPdf
import org.homesharing.lockbox.domain.model.contract_pdf.toDomain
import org.homesharing.lockbox.domain.model.contract_version.ContractVersion
import org.homesharing.lockbox.domain.model.contract_version.ContractVersionList
import org.homesharing.lockbox.domain.model.contract_version.toDomain
import org.homesharing.lockbox.domain.repository.ContractsRepository

internal class ContractsRepositoryImpl(
    private val api: ContractsApi,
) : ContractsRepository {
    override suspend fun getContracts(
        limit: Int,
        cursor: String?,
    ): Response<ContractList> {
        return parseLockboxApiResponse(
            apiCall = { api.getContracts(limit = limit, cursor = cursor) },
            mapper = ContractListDto::toDomain,
        )
    }

    override suspend fun createContract(
        contractCity: String,
        contractDate: String,
        landlordInfo: Contract.Profile,
        renterInfo: Contract.Profile,
        flat: Contract.Flat,
        booking: Contract.Booking,
    ): Response<Contract> {
        val request = ContractCreateRequestDto(
            contractCity = contractCity,
            contractDate = contractDate,
            landlordInfo = landlordInfo.toRequestDto(),
            renterInfo = renterInfo.toRequestDto(),
            flat = flat.toRequestDto(),
            booking = booking.toRequestDto(),
        )

        return parseLockboxApiResponse(
            apiCall = { api.createContract(request) },
            mapper = ContractDto::toDomain,
        )
    }

    override suspend fun getContract(id: String): Response<Contract> {
        return parseLockboxApiResponse(
            apiCall = { api.getContract(id) },
            mapper = ContractDto::toDomain,
        )
    }

    override suspend fun updateContract(
        id: String,
        contractCity: String,
        contractDate: String,
        landlordInfo: Contract.Profile,
        renterInfo: Contract.Profile,
        flat: Contract.Flat,
        booking: Contract.Booking,
    ): Response<Contract> {
        val request = ContractCreateRequestDto(
            contractCity = contractCity,
            contractDate = contractDate,
            landlordInfo = landlordInfo.toRequestDto(),
            renterInfo = renterInfo.toRequestDto(),
            flat = flat.toRequestDto(),
            booking = booking.toRequestDto(),
        )

        return parseLockboxApiResponse(
            apiCall = { api.updateContract(id = id, request = request) },
            mapper = ContractDto::toDomain,
        )
    }

    override suspend fun getContractPdf(id: String): Response<ContractPdf> {
        return parseLockboxApiResponse(
            apiCall = { api.getContractPdf(id) },
            mapper = ContractPdfDto::toDomain,
        )
    }

    override suspend fun prepareContract(id: String): Response<ContractVersion> {
        return parseLockboxApiResponse(
            apiCall = { api.prepareContract(id) },
            mapper = ContractVersionDto::toDomain,
        )
    }

    override suspend fun getContractVersions(id: String): Response<ContractVersionList> {
        return parseLockboxApiResponse(
            apiCall = { api.getContractVersions(id) },
            mapper = ContractVersionListDto::toDomain,
        )
    }

    override suspend fun getContractVersion(
        id: String,
        versionId: String,
    ): Response<ContractVersion> {
        return parseLockboxApiResponse(
            apiCall = { api.getContractVersion(id = id, versionId = versionId) },
            mapper = ContractVersionDto::toDomain,
        )
    }

    override suspend fun requestConfirmationCode(
        id: String,
        versionId: String,
        party: ContractParty,
    ): Response<OtpRequestResponse> {
        val request = ContractConfirmationRequestDto(
            versionId = versionId,
            party = party.toDto(),
        )

        return parseLockboxApiResponse(
            apiCall = { api.requestConfirmationCode(id = id, request = request) },
            mapper = OtpRequestResponseDto::toDomain,
        )
    }
}
