package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.domain.api.response.Response
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
}
