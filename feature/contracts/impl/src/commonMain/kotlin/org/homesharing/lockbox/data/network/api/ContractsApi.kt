package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.contract.ContractCreateRequestDto
import org.homesharing.lockbox.data.network.dto.contract.ContractDto
import org.homesharing.lockbox.data.network.dto.contract_list.ContractListDto

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
}
