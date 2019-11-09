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
    single { ZomatoAuthInterceptor() }

    // OkHttpClient
    single {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                get<HttpLoggingInterceptor>().level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(get<HttpLoggingInterceptor>())
                addInterceptor(get<CurlInterceptor>())
            }
            addInterceptor(get<ZomatoAuthInterceptor>())
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
        }.build()
    }

    // Retrofit
    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.ZOMATO_BASE_URL)
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .build()
    }

    // Endpoint
    single<ZomatoApi> { get<Retrofit>().create(ZomatoApi::class.java) }

    // Scheduler
    single<SchedulerProvider> { MainSchedulerProvider() }
}