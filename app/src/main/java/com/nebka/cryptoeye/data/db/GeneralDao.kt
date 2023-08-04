package com.nebka.cryptoeye.data.db


import androidx.room.Dao

@Dao
interface GeneralDao

/**
 * Extension function to call DAO methods
 *
 * @param block will execute related method
 * @param onError will be called whenever exceptions occur
 *
 * @return [DatabaseResult]
 */
inline fun <T : GeneralDao, R : Any> T.executeQuery(
    block: T.() -> R,
    onError: (String?) -> Unit = {}
): DatabaseResult<R> = try {
    block().let {
        DatabaseResult.Success(it)
    }
} catch (e: Exception) {
    onError(e.message)
    DatabaseResult.Error(e.message)
}
