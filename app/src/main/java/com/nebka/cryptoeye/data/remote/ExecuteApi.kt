package com.nebka.cryptoeye.data.remote

import retrofit2.HttpException
import retrofit2.Response


/**
 * A general function to execute API requests.
 *
 * @param execute the API function defined in the API interface
 */
suspend fun <T : Any> executeApi(
    execute: suspend () -> Response<T>
): NetworkResult<T> = try {
    val response = execute()
    val body = response.body()

    if (response.isSuccessful && body != null) NetworkResult.Success(body)
    else NetworkResult.Error(code = response.code(), message = response.message())
} catch (e: HttpException) {
    NetworkResult.Error(code = e.code(), message = e.message())
} catch (e: Throwable) {
    NetworkResult.Exception(e)
}