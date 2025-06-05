package org.homesharing.lockbox.domain.api.response

import kotlinx.coroutines.CancellationException
import org.homesharing.lockbox.data.network.api.ApiResponse

internal suspend fun <Dto, D> parseLockBoxApiResponse(
    apiCall: suspend () -> ApiResponse<Dto>,
    mapper: (Dto) -> D,
): Response<D> =
    try {
        val apiResponse = apiCall()

        if (apiResponse.error) Response.Failure(apiResponse.errorText!!)
        else {
            val mapped = mapper(apiResponse.body!!)
            Response.Success(mapped)
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Response.Failure(e.cause?.message.toString())
    }

internal suspend fun <Dto> parseLockBoxApiResponse(
    apiCall: suspend () -> ApiResponse<Dto>,
): Response<Dto> =
    try {
        val apiResponse = apiCall()

        if (apiResponse.error) Response.Failure(apiResponse.errorText!!)
        else Response.Success(apiResponse.body!!)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Response.Failure(e.cause?.message.toString())
    }
