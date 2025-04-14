package org.homesharing.lockbox.domain.api.response

import org.homesharing.lockbox.data.network.api.ApiResponse

suspend fun <Dto, D> parseLockBoxApiResponse(
    apiCall: suspend () -> ApiResponse<Dto>,
    mapper: (Dto) -> D
): Response<D> =
    try {
        val apiResponse = apiCall()
        if (apiResponse.error) {
            Response.Failure(apiResponse.errorText!!)
        } else {
            val mapped = mapper(apiResponse.body!!)
            Response.Success(mapped)
        }
    } catch (e: Exception) {
        Response.Failure(e.cause?.message.toString())
    }

suspend fun <Dto> parseLockBoxApiResponse(
    apiCall: suspend () -> ApiResponse<Dto>
): Response<Dto> = try {
        Response.Success(apiCall().body!!)
    } catch (e: Exception) {
        Response.Failure(e.cause?.message.toString())
    }