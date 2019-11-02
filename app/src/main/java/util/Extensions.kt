package util

val Throwable.treatMessage: String
    get() {
        if (this is retrofit2.HttpException) {
            return when (this.code()) {
                400, 405 -> "Unable to use the parameters defined in the request"
                401, 403 -> "Request unauthorized, verify your API key"
                408 -> "Request timeout occurred, verify your internet connection"
                else -> "An error occurred"
            }
        }
        return this.localizedMessage ?: "An error occurred"
    }