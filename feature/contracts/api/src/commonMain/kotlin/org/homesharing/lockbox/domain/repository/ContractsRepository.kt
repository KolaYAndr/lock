package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.contract.Contract
import org.homesharing.lockbox.domain.model.contract.ContractParty
import org.homesharing.lockbox.domain.model.contract_confirmation.OtpRequestResponse
import org.homesharing.lockbox.domain.model.contract_list.ContractList
import org.homesharing.lockbox.domain.model.contract_pdf.ContractPdf
import org.homesharing.lockbox.domain.model.contract_version.ContractVersion
import org.homesharing.lockbox.domain.model.contract_version.ContractVersionList

interface ContractsRepository {
    /**
     * Получает пагинированный список контрактов текущего пользователя (`GET /api/contracts`).
     * Требует авторизации.
     *
     * Использует cursor-пагинацию.
     *
     * @param limit размер страницы (1..100, по умолчанию 10)
     * @param cursor курсор следующей страницы; при `null` возвращается первая страница
     * @return [Response] со страницей контрактов
     */
    suspend fun getContracts(
        limit: Int = 10,
        cursor: String? = null,
    ): Response<ContractList>

    /**
     * Создаёт новый контракт (черновик) для текущего пользователя (`POST /api/contracts`).
     * Требует авторизации.
     *
     * Валидирует данные сторон, квартиры и бронирования. `remainingPrice` из [booking]
     * не отправляется (readOnly).
     *
     * @param contractCity город заключения контракта
     * @param contractDate дата заключения контракта (ISO date)
     * @param landlordInfo данные арендодателя
     * @param renterInfo данные арендатора
     * @param flat данные квартиры
     * @param booking данные бронирования
     * @return [Response] с созданным контрактом
     */
    suspend fun createContract(
        contractCity: String,
        contractDate: String,
        landlordInfo: Contract.Profile,
        renterInfo: Contract.Profile,
        flat: Contract.Flat,
        booking: Contract.Booking,
    ): Response<Contract>

    /**
     * Получает детальную информацию о контракте по идентификатору (`GET /api/contracts/{id}`).
     * Требует авторизации. Доступно участнику контракта (арендодателю или гостю).
     *
     * @param id идентификатор контракта (uuid)
     * @return [Response] с детальной информацией о контракте
     */
    suspend fun getContract(
        id: String,
    ): Response<Contract>

    /**
     * Обновляет данные существующего контракта (`PUT /api/contracts/{id}`).
     * Требует авторизации.
     *
     * `remainingPrice` из [booking] не отправляется (readOnly).
     *
     * @param id идентификатор контракта (uuid)
     * @param contractCity город заключения контракта
     * @param contractDate дата заключения контракта (ISO date)
     * @param landlordInfo данные арендодателя
     * @param renterInfo данные арендатора
     * @param flat данные квартиры
     * @param booking данные бронирования
     * @return [Response] с обновлённым контрактом
     */
    suspend fun updateContract(
        id: String,
        contractCity: String,
        contractDate: String,
        landlordInfo: Contract.Profile,
        renterInfo: Contract.Profile,
        flat: Contract.Flat,
        booking: Contract.Booking,
    ): Response<Contract>

    /**
     * Получает статус генерации PDF-файла текущей версии контракта и presigned S3 URL
     * для скачивания (`GET /api/contracts/{id}/pdf`).
     * Требует авторизации.
     *
     * Если `current_version_id` ещё не создан, сервер возвращает `documentStatus = Pending`.
     *
     * @param id идентификатор контракта (uuid)
     * @return [Response] со статусом PDF и ссылкой на скачивание
     */
    suspend fun getContractPdf(
        id: String,
    ): Response<ContractPdf>

    /**
     * Фиксирует иммутабельную версию контракта (snapshot) для черновика (`POST /api/contracts/{id}/prepare`).
     * Требует авторизации. Доступно владельцу контракта.
     *
     * Проверяет полноту профилей и ставит в очередь генерацию PDF.
     *
     * @param id идентификатор контракта (uuid)
     * @return [Response] с зафиксированной версией контракта
     */
    suspend fun prepareContract(
        id: String,
    ): Response<ContractVersion>

    /**
     * Возвращает список исторических версий контракта (`GET /api/contracts/{id}/versions`).
     * Требует авторизации.
     *
     * Владелец видит все версии, гость — только версии со своим участием.
     *
     * @param id идентификатор контракта (uuid)
     * @return [Response] со списком версий контракта
     */
    suspend fun getContractVersions(
        id: String,
    ): Response<ContractVersionList>

    /**
     * Возвращает конкретную версию контракта (`GET /api/contracts/{id}/versions/{version_id}`).
     * Требует авторизации.
     *
     * Включает snapshot, подтверждения и ссылку на PDF (если готов).
     *
     * @param id идентификатор контракта (uuid)
     * @param versionId идентификатор версии контракта (uuid)
     * @return [Response] с версией контракта
     */
    suspend fun getContractVersion(
        id: String,
        versionId: String,
    ): Response<ContractVersion>

    /**
     * Запрашивает и отправляет на email участника 6-значный OTP-код для подтверждения
     * указанной версии контракта (`POST /api/contracts/{id}/confirmation-code`).
     * Требует авторизации.
     *
     * @param id идентификатор контракта (uuid)
     * @param versionId идентификатор версии контракта (uuid)
     * @param party сторона контракта ([ContractParty.Landlord] или [ContractParty.Guest])
     * @return [Response] с параметрами повторной отправки и срока жизни кода
     */
    suspend fun requestConfirmationCode(
        id: String,
        versionId: String,
        party: ContractParty,
    ): Response<OtpRequestResponse>

    /**
     * Подтверждает указанную версию контракта от имени стороны (`POST /api/contracts/{id}/confirm`)
     * с проверкой 6-значного OTP-кода. Требует авторизации.
     *
     * После подтверждения обеими сторонами статус контракта переходит в `Signed`.
     *
     * @param id идентификатор контракта (uuid)
     * @param versionId идентификатор версии контракта (uuid)
     * @param party сторона контракта ([ContractParty.Landlord] или [ContractParty.Guest])
     * @param code 6-значный OTP-код подтверждения
     * @return [Response] с обновлённым контрактом
     */
    suspend fun confirmContract(
        id: String,
        versionId: String,
        party: ContractParty,
        code: String,
    ): Response<Contract>

    /**
     * Отклоняет указанную версию контракта (`POST /api/contracts/{id}/reject`),
     * переводя контракт в статус `Rejected` с опциональной причиной. Требует авторизации.
     *
     * @param id идентификатор контракта (uuid)
     * @param versionId идентификатор версии контракта (uuid)
     * @param reason опциональная причина отклонения
     * @return [Response] с обновлённым контрактом
     */
    suspend fun rejectContract(
        id: String,
        versionId: String,
        reason: String? = null,
    ): Response<Contract>
}
