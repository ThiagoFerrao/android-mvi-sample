package network

import okhttp3.Interceptor
import okhttp3.Response

class QuoteAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            addHeader("X-Theysaidso-Api-Secret", "")
        }.build()

        return chain.proceed(request)
    }
}