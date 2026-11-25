package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
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
}
