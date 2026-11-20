package org.homesharing.lockbox.data.repository

import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.data.network.api.AccountApi
import org.homesharing.lockbox.data.network.dto.avatar.AvatarDto
import org.homesharing.lockbox.data.network.dto.personal_document.PersonalDocumentListDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoRequestDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountDto
import org.homesharing.lockbox.data.network.token.TokenStorage
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.avatar.Avatar
import org.homesharing.lockbox.domain.model.avatar.toDomain
import org.homesharing.lockbox.domain.model.personal_document.PersonalDocumentList
import org.homesharing.lockbox.domain.model.personal_document.PersonalDocumentType
import org.homesharing.lockbox.domain.model.personal_document.toDomain
import org.homesharing.lockbox.domain.model.personal_document.toDto
import org.homesharing.lockbox.domain.model.profile_info.ProfileInfo
import org.homesharing.lockbox.domain.model.profile_info.toDomain
import org.homesharing.lockbox.domain.model.role.UserRole
import org.homesharing.lockbox.domain.model.role.toDto
import org.homesharing.lockbox.domain.model.update_account.UpdateAccount
import org.homesharing.lockbox.domain.model.update_account.toDomain
import org.homesharing.lockbox.domain.repository.AccountRepository

internal class AccountRepositoryImpl(
    private val api: AccountApi,
    private val tokenStorage: TokenStorage,
) : AccountRepository {
    override suspend fun logOut() = tokenStorage.clearInfo()

    override val loggedIn: Flow<Boolean> = tokenStorage.loggedIn

    override suspend fun getImageLink(): Response<Avatar> =
        parseLockboxApiResponse(
            apiCall = { api.getAvatar() },
            mapper = AvatarDto::toDomain
        )

    override suspend fun sendImage(data: ByteArray): Response<Unit> =
        parseLockboxApiResponse {
            api.sendImage(data = data)
        }

    override suspend fun deactivateUser(): Response<UpdateAccount> =
        parseLockboxApiResponse(
            apiCall = { api.deactivateUser() },
            mapper = UpdateAccountDto::toDomain
        )

    override suspend fun getProfileInfo(): Response<ProfileInfo> {
        return parseLockboxApiResponse(
            apiCall = {
                api.getProfileInfo()
            },
            mapper = ProfileInfoDto::toDomain
        )
    }

    override suspend fun updateProfileInfo(
        firstName: String?,
        lastName: String?,
        middleName: String?,
        phoneNumber: String?,
        email: String?,
        livingAddress: String?,
        birthDate: String?,
        passportSeries: String?,
        passportNumber: String?,
        passportIssuer: String?,
        passportIssuedAt: String?,
        passportDivisionCode: String?,
    ): Response<ProfileInfo> {
        val contactInfo = if (phoneNumber != null || email != null) {
            ProfileInfoRequestDto.ContactInfoRequestDto(
                phoneNumber = phoneNumber,
                email = email,
            )
        } else {
            null
        }

        val passport = if (
            passportSeries != null ||
            passportNumber != null ||
            passportIssuer != null ||
            passportIssuedAt != null ||
            passportDivisionCode != null
        ) {
            ProfileInfoRequestDto.LegalInfoRequestDto.PassportRequestDto(
                passportSeries = passportSeries,
                passportNumber = passportNumber,
                issuer = passportIssuer,
                issuedAt = passportIssuedAt,
                divisionCode = passportDivisionCode,
            )
        } else {
            null
        }

        val legalInfo = if (livingAddress != null || birthDate != null || passport != null) {
            ProfileInfoRequestDto.LegalInfoRequestDto(
                livingAddress = livingAddress,
                birthDate = birthDate,
                passport = passport,
            )
        } else {
            null
        }

        val request = ProfileInfoRequestDto(
            firstName = firstName,
            lastName = lastName,
            middleName = middleName,
            contactInfo = contactInfo,
            legalInfo = legalInfo,
        )

        return parseLockboxApiResponse(
            apiCall = { api.updateProfileInfo(request) },
            mapper = ProfileInfoDto::toDomain
        )
    }

    override suspend fun getPersonalDocuments(): Response<PersonalDocumentList> {
        return parseLockboxApiResponse(
            apiCall = { api.getPersonalDocuments() },
            mapper = PersonalDocumentListDto::toDomain,
        )
    }

    override suspend fun getPersonalDocument(
        id: String
    ): Response<PersonalDocumentList.PersonalDocument> {
        return parseLockboxApiResponse(
            apiCall = { api.getPersonalDocument(id) },
            mapper = PersonalDocumentListDto.PersonalDocumentDto::toDomain,
        )
    }

    override suspend fun uploadPersonalDocument(
        data: ByteArray,
        fileName: String,
        contentType: String,
        docType: PersonalDocumentType?,
    ): Response<PersonalDocumentList.PersonalDocument> {
        return parseLockboxApiResponse(
            apiCall = {
                api.uploadPersonalDocument(
                    data = data,
                    fileName = fileName,
                    contentType = contentType,
                    docType = docType?.toDto(),
                )
            },
            mapper = PersonalDocumentListDto.PersonalDocumentDto::toDomain,
        )
    }

    override suspend fun deletePersonalDocument(id: String): Response<Unit> {
        return parseLockboxApiResponse {
            api.deletePersonalDocument(id)
        }
    }

    override suspend fun becomeLandlord(): Response<ProfileInfo> {
        return parseLockboxApiResponse(
            apiCall = { api.becomeLandlord() },
            mapper = ProfileInfoDto::toDomain
        )
    }

    override suspend fun updateActiveRole(role: UserRole): Response<ProfileInfo> {
        return parseLockboxApiResponse(
            apiCall = { api.updateActiveRole(role.toDto()) },
            mapper = ProfileInfoDto::toDomain
        )
    }
}
