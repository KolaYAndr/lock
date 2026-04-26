package org.homesharing.lockbox.domain.api.response

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.homesharing.lockbox.data.network.api.response.ApiResponse

internal suspend fun <Dto : Any, Domain : Any> parseLockBoxApiResponse(
    apiCall: suspend () -> ApiResponse<Dto>,
    mapper: (Dto) -> Domain,
): Response<Domain> = withContext(Dispatchers.IO) {
    try {
        when (val apiResponse = apiCall()) {
            is ApiResponse.Failure -> Response.Failure(apiResponse.message)
            is ApiResponse.Success -> withContext(Dispatchers.Default) {
                Response.Success(mapper(apiResponse.data))
            }
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        e.printStackTrace()
        Response.Failure(e.message ?: "Unknown error")
    }
}

internal suspend fun <Dto : Any> parseLockBoxApiResponse(
    apiCall: suspend () -> ApiResponse<Dto>,
): Response<Dto> = withContext(Dispatchers.IO) {
    try {
        when (val apiResponse = apiCall()) {
            is ApiResponse.Failure -> Response.Failure(apiResponse.message)
            is ApiResponse.Success -> Response.Success(apiResponse.data)
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        e.printStackTrace()
        Response.Failure(e.message ?: "Unknown error")
    }
}