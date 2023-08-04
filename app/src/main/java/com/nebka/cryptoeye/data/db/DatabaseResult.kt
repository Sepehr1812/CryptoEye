package com.nebka.cryptoeye.data.db

sealed class DatabaseResult<T : Any> {
    class Success<T : Any>(val data: T) : DatabaseResult<T>()
    class Error<T : Any>(val message: String?) : DatabaseResult<T>()
}
