package home.model

object HomeError {
    fun treat(error: Throwable): HomeMutation =
        HomeMutation.Error(error.defaultMessage)
}

object EmptyResponse: Throwable()

private val Throwable.defaultMessage: String
    get() {
        if (this is EmptyResponse) {
            return "Unable to find any restaurant"
        }
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