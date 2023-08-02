package com.nebka.cryptoeye.data.db


import androidx.room.Dao

@Dao
interface GeneralDao

/**
 * Extension function to call DAO methods
 *
 * @param block will execute related method
 * @param onError will be called whenever exceptions occur
 */
inline fun <T : GeneralDao, R> T.executeQuery(
    block: T.() -> R,
    onError: (String?) -> Unit = {}
): R? {

    return try {
        block()
    } catch (e: Exception) {
        onError(e.message)
        null
    }
}
