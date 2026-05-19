//package org.homesharing.lockbox.presentation.detailed_lockbox.access_modal_sheet
//
//import androidx.compose.runtime.Immutable
//import androidx.lifecycle.ViewModel
//import kotlinx.coroutines.flow.SharedFlow
//import kotlinx.coroutines.flow.StateFlow
//import org.homesharing.lockbox.domain.model.access.Access
//
//internal abstract class AccessModalSheetViewModel: ViewModel() {
//    abstract val uiState: StateFlow<AccessModalSheetUiState>
//    abstract val tokenData: SharedFlow<String>
//    abstract fun addGuest()
//    abstract fun emitLink(link: String)
//    abstract fun revokeToken(tokenId: String)
//    abstract fun onChangeStartDate(newStart: Long)
//    abstract fun onChangeStartTime(newStartTime: Long)
//    abstract fun onChangeEndDate(newEnd: Long)
//    abstract fun onChangeEndTime(newEndTime: Long)
//    abstract fun onChangeGuestName(newGuestName: String)
//    abstract fun toStep(step: AccessModalSheetStep)
//}
//
//@Immutable
//internal data class AccessModalSheetUiState(
//    val step: AccessModalSheetStep = AccessModalSheetStep.GuestList,
//    val accesses: List<Access> = emptyList(),
//    val loading: Boolean = false,
//    val guestName: String = "",
//    val selectedStartDate: Long? = null,
//    val selectedStartTime: Long? = null,
//    val selectedEndDate: Long? = null,
//    val selectedEndTime: Long? = null,
//)
//
//internal sealed interface AccessModalSheetStep {
//    data object GuestList : AccessModalSheetStep
//    data object AddGuest : AccessModalSheetStep
//    data class GuestInfo(val access: Access) : AccessModalSheetStep
//}
