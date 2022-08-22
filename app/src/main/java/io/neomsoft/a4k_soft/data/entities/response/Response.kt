package io.neomsoft.a4k_soft.data.entities.response

sealed class Response<out T>(
    val cacheData: T? = null
) {

    data class Success<T>(val data: T) : Response<T>()

    class Empty<T> : Response<T>()

    class Loading<T>(cacheData: T? = null) : Response<T>(cacheData)

    class ErrorNetworkConnection<T>(cacheData: T? = null) : Response<T>(cacheData)
}
