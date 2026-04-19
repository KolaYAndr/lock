package org.homesharing.lockbox.presentation.contracts.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import org.homesharing.lockbox.domain.repository.DocumentsRepository

internal class ContractsViewModelImpl(
    documentsRepository: DocumentsRepository,
) : ContractsViewModel() {
    override val contracts = documentsRepository.getDocumentsPagingFlow().cachedIn(viewModelScope)
}
