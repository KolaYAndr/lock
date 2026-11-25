package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.contract.Contract
import org.homesharing.lockbox.domain.model.contract_list.ContractList

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
}
