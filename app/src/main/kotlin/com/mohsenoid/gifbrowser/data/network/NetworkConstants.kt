package com.mohsenoid.gifbrowser.data.network

object NetworkConstants {

    const val ENDPOINT_SEARCH: String = "search"

    const val PARAM_API_KEY = "api_key"
    const val PARAM_QUERY = "q"
    const val PARAM_LIMIT = "limit"
    const val PARAM_OFFSET = "offset"

    const val HTTP_CACHE_PATH = "http-cache"

    const val NETWORK_CONNECTION_TIMEOUT_SEC = 30 // 30 sec
    const val CACHE_SIZE_MB = 10
    const val CACHE_MAX_AGE_MIN = 2
    const val CACHE_MAX_STALE_DAY = 7
    const val RETRY_COUNT = 3
}
