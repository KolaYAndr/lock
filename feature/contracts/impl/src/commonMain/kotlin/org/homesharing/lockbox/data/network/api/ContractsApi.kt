package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
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

internal interface ContractsApi {
    /**
     * Получает пагинированный список контрактов текущего пользователя (`GET /api/contracts`).
     * Требует авторизации (Bearer token).
     *
     * Использует cursor-пагинацию.
     *
     * @param limit размер страницы (1..100, по умолчанию 10)
     * @param cursor курсор следующей страницы из `next_cursor`; при `null` возвращается первая страница
     * @return [ApiResponse] со страницей контрактов [ContractListDto] (200 OK)
     */
    suspend fun getContracts(
        limit: Int = 10,
        cursor: String? = null,
    ): ApiResponse<ContractListDto>

    /**
     * Создаёт новый контракт (черновик) для текущего пользователя (`POST /api/contracts`).
     * Требует авторизации (Bearer token).
     *
     * Валидирует данные сторон, квартиры и бронирования.
     *
     * @param request данные создаваемого контракта [ContractCreateRequestDto]
     * @return [ApiResponse] с созданным контрактом [ContractDto] (201 Created)
     */
    suspend fun createContract(
        request: ContractCreateRequestDto,
    ): ApiResponse<ContractDto>

    /**
     * Получает детальную информацию о контракте по идентификатору (`GET /api/contracts/{id}`).
     * Требует авторизации (Bearer token). Доступно участнику контракта (арендодателю или гостю).
     *
     * @param id идентификатор контракта (uuid)
     * @return [ApiResponse] с детальной информацией о контракте [ContractDto] (200 OK)
     */
    suspend fun getContract(
        id: String,
    ): ApiResponse<ContractDto>

    /**
     * Обновляет данные существующего контракта (`PUT /api/contracts/{id}`).
     * Требует авторизации (Bearer token).
     *
     * @param id идентификатор контракта (uuid)
     * @param request новые данные контракта [ContractCreateRequestDto]
     * @return [ApiResponse] с обновлённым контрактом [ContractDto] (200 OK)
     */
    suspend fun updateContract(
        id: String,
        request: ContractCreateRequestDto,
    ): ApiResponse<ContractDto>

    /**
     * Получает статус генерации PDF-файла текущей версии контракта и presigned S3 URL
     * для скачивания (`GET /api/contracts/{id}/pdf`).
     * Требует авторизации (Bearer token).
     *
     * Если `current_version_id` ещё не создан, сервер возвращает `document_status = pending`.
     *
     * @param id идентификатор контракта (uuid)
     * @return [ApiResponse] со статусом PDF и ссылкой на скачивание [ContractPdfDto] (200 OK)
     */
    suspend fun getContractPdf(
        id: String,
    ): ApiResponse<ContractPdfDto>

    /**
     * Фиксирует иммутабельную версию контракта (snapshot) для черновика (`POST /api/contracts/{id}/prepare`).
     * Требует авторизации (Bearer token). Доступно владельцу контракта.
     *
     * Проверяет полноту профилей и ставит в очередь генерацию PDF.
     *
     * @param id идентификатор контракта (uuid)
     * @return [ApiResponse] с зафиксированной версией контракта [ContractVersionDto] (200 OK)
     */
    suspend fun prepareContract(
        id: String,
    ): ApiResponse<ContractVersionDto>

    /**
     * Возвращает список исторических версий контракта (`GET /api/contracts/{id}/versions`).
     * Требует авторизации (Bearer token).
     *
     * Владелец видит все версии, гость — только версии со своим участием.
     *
     * @param id идентификатор контракта (uuid)
     * @return [ApiResponse] со списком версий контракта [ContractVersionListDto] (200 OK)
     */
    suspend fun getContractVersions(
        id: String,
    ): ApiResponse<ContractVersionListDto>

    /**
     * Возвращает конкретную версию контракта (`GET /api/contracts/{id}/versions/{version_id}`).
     * Требует авторизации (Bearer token).
     *
     * Включает snapshot, подтверждения и ссылку на PDF (если готов).
     *
     * @param id идентификатор контракта (uuid)
     * @param versionId идентификатор версии контракта (uuid)
     * @return [ApiResponse] с версией контракта [ContractVersionDto] (200 OK)
     */
    suspend fun getContractVersion(
        id: String,
        versionId: String,
    ): ApiResponse<ContractVersionDto>

    /**
     * Запрашивает и отправляет на email участника 6-значный OTP-код для подтверждения
     * указанной версии контракта (`POST /api/contracts/{id}/confirmation-code`).
     * Требует авторизации (Bearer token).
     *
     * @param id идентификатор контракта (uuid)
     * @param request данные запроса OTP-кода [ContractConfirmationRequestDto]
     * @return [ApiResponse] с параметрами повторной отправки и срока жизни кода [OtpRequestResponseDto] (202 Accepted)
     */
    suspend fun requestConfirmationCode(
        id: String,
        request: ContractConfirmationRequestDto,
    ): ApiResponse<OtpRequestResponseDto>

    /**
     * Подтверждает указанную версию контракта от имени стороны (`POST /api/contracts/{id}/confirm`)
     * с проверкой 6-значного OTP-кода. Требует авторизации (Bearer token).
     *
     * После подтверждения обеими сторонами статус контракта переходит в `signed`.
     *
     * @param id идентификатор контракта (uuid)
     * @param request данные подтверждения контракта [ContractConfirmRequestDto]
     * @return [ApiResponse] с обновлённым контрактом [ContractDto] (200 OK)
     */
    suspend fun confirmContract(
        id: String,
        request: ContractConfirmRequestDto,
    ): ApiResponse<ContractDto>

    /**
     * Отклоняет указанную версию контракта (`POST /api/contracts/{id}/reject`),
     * переводя контракт в статус `rejected` с опциональной причиной. Требует авторизации (Bearer token).
     *
     * @param id идентификатор контракта (uuid)
     * @param request данные отклонения контракта [ContractRejectRequestDto]
     * @return [ApiResponse] с обновлённым контрактом [ContractDto] (200 OK)
     */
    suspend fun rejectContract(
        id: String,
        request: ContractRejectRequestDto,
    ): ApiResponse<ContractDto>

    /**
     * Отменяет контракт владельцем (`POST /api/contracts/{id}/cancel`),
     * переводя его в статус `cancelled` с опциональной причиной. Требует авторизации (Bearer token).
     *
     * @param id идентификатор контракта (uuid)
     * @param request данные отмены контракта [ContractCancelRequestDto] (минимум `{}`)
     * @return [ApiResponse] с обновлённым контрактом [ContractDto] (200 OK)
     */
    suspend fun cancelContract(
        id: String,
        request: ContractCancelRequestDto,
    ): ApiResponse<ContractDto>
}
