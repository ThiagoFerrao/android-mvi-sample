package home.di

import base.RxUseCase
import com.nhaarman.mockitokotlin2.mock
import home.homeutil.TestHomeState
import home.interactor.HomeInteractor
import home.interactor.usecase.SearchUseCase
import home.model.HomeMutation
import network.SchedulerProvider
import network.TestSchedulerProvider
import network.ZomatoApi
import network.ZomatoAuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val testHomeInteractorModule = module {
    single { TestHomeState.data }
    single<SchedulerProvider> { TestSchedulerProvider() }
    single<RxUseCase<String, HomeMutation>> { mock() }
    single { HomeInteractor(get(), get(), get()) }
}

val testButtonTapUseCaseModule = module {
    single<RxUseCase<String, HomeMutation>> { SearchUseCase(get()) }
}

val testNetworkModule = module {
    single { MockWebServer() }
    single {
        OkHttpClient.Builder()
            .addInterceptor(ZomatoAuthInterceptor())
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .build()
    }
    single<ZomatoApi> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(get<MockWebServer>().url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ZomatoApi::class.java)
    }
}