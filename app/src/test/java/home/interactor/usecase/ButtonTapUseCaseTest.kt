package home.interactor.usecase

import base.RxUseCase
import home.di.testButtonTapUseCaseModule
import home.di.testNetworkModule
import home.homeutil.TestHomeMutation
import home.model.HomeMutation
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import testutil.mockErrorResponse
import testutil.mockSuccessResponse
import testutil.mockTimeoutResponse
import testutil.startSilently

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
        server.startSilently()
        mutationOutput = TestObserver.create()
    }

    @Test
    fun should_emit_correct_mutation_when_receive_success_response() {
        server.mockSuccessResponse("zomito/restaurants_success.json")
        mutationOutput = buttonTapUseCase.execute("search").test()

        mutationOutput.assertValueCount(2)
        mutationOutput.assertValues(
            TestHomeMutation.disableButton,
            TestHomeMutation.data
        )
    }

    @Test
    fun should_emit_correct_mutation_when_receive_error_response() {
        server.mockErrorResponse(400)
        mutationOutput = buttonTapUseCase.execute("search").test()

        mutationOutput.assertValueCount(2)
        mutationOutput.assertValues(
            TestHomeMutation.disableButton,
            TestHomeMutation.error
        )
    }

    @Test
    fun should_emit_correct_mutation_when_receive_timeout_response() {
        server.mockTimeoutResponse()
        mutationOutput = buttonTapUseCase.execute("search").test()

        mutationOutput.assertValueCount(2)
        mutationOutput.assertValues(
            TestHomeMutation.disableButton,
            HomeMutation.Error("timeout")
        )
    }

    @After
    fun after() {
        server.shutdown()
        mutationOutput.dispose()
    }
}