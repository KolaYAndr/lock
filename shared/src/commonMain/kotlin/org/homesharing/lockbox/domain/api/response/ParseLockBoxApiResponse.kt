package org.homesharing.lockbox.domain.api.response

import kotlinx.coroutines.CancellationException
import org.homesharing.lockbox.data.network.api.response.ApiResponse

@Suppress("UNCHECKED_CAST")
internal suspend fun <Dto, D> parseLockBoxApiResponse(
    apiCall: suspend () -> ApiResponse,
    mapper: (Dto) -> D,
): Response<D> =
    try {
        val apiResponse = apiCall()

        when (apiResponse) {
            is ApiResponse.Failure -> Response.Failure(apiResponse.message)
            is ApiResponse.Success<*> -> {
                val mapped = mapper(apiResponse.data as Dto)
                Response.Success(mapped)
            }
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Response.Failure(e.cause?.message.toString())
    }

@Suppress("UNCHECKED_CAST")
internal suspend fun <Dto> parseLockBoxApiResponse(
    apiCall: suspend () -> ApiResponse,
): Response<Dto> =
    try {
        val apiResponse = apiCall()

        when (apiResponse) {
            is ApiResponse.Failure -> Response.Failure(apiResponse.message)
            is ApiResponse.Success<*> -> Response.Success(apiResponse.data as Dto)
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Response.Failure(e.cause?.message.toString())
    }
