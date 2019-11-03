package home.interactor.usecase

import base.RxUseCase
import home.di.testButtonTapUseCaseModule
import home.di.testNetworkModule
import home.model.HomeMutation
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class ButtonTapUseCaseTest : AutoCloseKoinTest() {

    private val buttonTapUseCase: RxUseCase<String, HomeMutation> by inject()
    private val server: MockWebServer by inject()

    private lateinit var mutationOutput: TestObserver<HomeMutation>

    @Before
    fun before() {
        startKoin {
            modules(
                listOf(testNetworkModule, testButtonTapUseCaseModule)
            )
        }
        server.start()
    }

    @Test
    fun should() {

    }

    @After
    fun after() {
        server.shutdown()
        mutationOutput.dispose()
    }
}