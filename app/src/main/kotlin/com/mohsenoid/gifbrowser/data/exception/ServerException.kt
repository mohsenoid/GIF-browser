package com.mohsenoid.gifbrowser.data.exception

class ServerException(code: Int, error: String?) : Exception("API call error code: $code\n$error")
