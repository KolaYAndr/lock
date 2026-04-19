package org.homesharing.lockbox.domain.api.response

import kotlinx.coroutines.CancellationException
import org.homesharing.lockbox.data.network.api.response.ApiResponse

@Suppress("UNCHECKED_CAST")
internal suspend fun <Dto : Any, Domain : Any> parseLockBoxApiResponse(
    apiCall: suspend () -> ApiResponse<Dto>,
    mapper: (Dto) -> Domain,
): Response<Domain> =
    try {
        when (val apiResponse = apiCall()) {
            is ApiResponse.Failure -> Response.Failure(apiResponse.message)
            is ApiResponse.Success<*> -> {
                val mapped = mapper(apiResponse.data as Dto)
                Response.Success(mapped)
            }
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        print(e)
        Response.Failure(e.cause?.message.toString())
    }

@Suppress("UNCHECKED_CAST")
internal suspend fun <Dto : Any> parseLockBoxApiResponse(
    apiCall: suspend () -> ApiResponse<Dto>,
): Response<Dto> =
    try {
        when (val apiResponse = apiCall()) {
            is ApiResponse.Failure -> Response.Failure(apiResponse.message)
            is ApiResponse.Success<*> -> Response.Success(apiResponse.data as Dto)
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        print(e)
        Response.Failure(e.cause?.message.toString())
    }
