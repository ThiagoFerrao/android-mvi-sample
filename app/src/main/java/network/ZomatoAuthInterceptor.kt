package network

import okhttp3.Interceptor
import okhttp3.Response
import thiagocruz.testingthings.BuildConfig

class ZomatoAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            addHeader("Accept", "application/json")
            addHeader("user-key", BuildConfig.ZOMATO_USER_KEY)
        }.build()

        return chain.proceed(request)
    }
}