package org.homesharing.lockbox.domain.api.response

import kotlinx.coroutines.CancellationException
import org.homesharing.lockbox.data.network.api.response.ApiResponse

internal suspend fun <Dto : Any, Domain : Any> parseLockBoxApiResponse(
    apiCall: suspend () -> ApiResponse<Dto>,
    mapper: (Dto) -> Domain,
): Response<Domain> =
    try {
        when (val apiResponse = apiCall()) {
            is ApiResponse.Failure -> Response.Failure(apiResponse.message)
            is ApiResponse.Success -> Response.Success(mapper(apiResponse.data))
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        e.printStackTrace()
        Response.Failure(e.message ?: "Unknown error")
    }

internal suspend fun <Dto : Any> parseLockBoxApiResponse(
    apiCall: suspend () -> ApiResponse<Dto>,
): Response<Dto> =
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