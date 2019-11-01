package network

import android.util.Log
import com.moczul.ok2curl.CurlInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import thiagocruz.testingthings.BuildConfig
import java.util.concurrent.TimeUnit

val networkModule = module {

    // Converters
    single<Converter.Factory> { GsonConverterFactory.create() }

    // Adapters
    single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }

    // Interceptors
    single { HttpLoggingInterceptor() }
    single { CurlInterceptor { curl -> Log.d("Request Curl", curl) } }
    single { QuoteAuthInterceptor() }

    // OkHttpClient
    single {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                get<HttpLoggingInterceptor>().level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(get<HttpLoggingInterceptor>())
                addInterceptor(get<CurlInterceptor>())
            }
            addInterceptor(get<QuoteAuthInterceptor>())
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    // Retrofit
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl("http://quotes.rest/qod.json")
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .build()
    }

    // Endpoint
    single<QuoteApi> { get<Retrofit>().create(QuoteApi::class.java) }
}