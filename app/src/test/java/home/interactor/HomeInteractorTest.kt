package home.interactor

import base.RxUseCase
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import home.di.testHomeInteractorModule
import home.homeutil.TestHomeCommand
import home.homeutil.TestHomeMutation
import home.homeutil.TestHomeState
import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import kotlin.test.assertTrue

class HomeInteractorTest : AutoCloseKoinTest() {

    private val interactor: HomeInteractor by inject()
    private val buttonTapUseCase: RxUseCase<String, HomeMutation> by inject()

    private lateinit var processInput: PublishSubject<HomeCommand>
    private lateinit var processOutput: TestObserver<HomeState>

    @Before
    fun before() {
        startKoin {
            modules(testHomeInteractorModule)
        }
        processInput = PublishSubject.create()
        processOutput = interactor.process(processInput).test()
    }

    @Test
    fun should_emit_correct_initial_state_before_any_command_is_emitted() {
        processOutput.assertValueCount(1)
        processOutput.assertValue(interactor.initialState)
    }

    @Test
    fun should_execute_buttonTapUseCase_when_ButtonTap_command_is_emitted() {
        processInput.onNext(TestHomeCommand.search)

        verify(buttonTapUseCase).execute(TestHomeCommand.search.searchValue)
    }

    @Test
    fun should_emit_correct_state_when_mutation_is_emitted() {
        whenever(buttonTapUseCase.execute(TestHomeCommand.buttonTap.searchValue)).thenReturn(
            Observable.just(TestHomeMutation.data),
            Observable.just(TestHomeMutation.disableButton),
            Observable.just(TestHomeMutation.error)
        )

        processInput.onNext(TestHomeCommand.buttonTap)
        processInput.onNext(TestHomeCommand.buttonTap)
        processInput.onNext(TestHomeCommand.buttonTap)

        processOutput.assertValueCount(4)
        processOutput.assertValues(
            interactor.initialState,
            TestHomeState.data,
            TestHomeState.disableButton,
            TestHomeState.error
        )
    }

    @Test
    fun should_not_emit_state_when_usecase_dont_emit_besides_initial_state() {
        whenever(buttonTapUseCase.execute(TestHomeCommand.buttonTap.searchValue))
            .thenReturn(Observable.empty())

        processInput.onNext(TestHomeCommand.buttonTap)

        processOutput.assertValueCount(1)
        processOutput.assertValues(interactor.initialState)
    }

    @Test
    fun should_emit_info_mutation_when_item_button_command_is_emitted() {
        val testCommand = TestHomeCommand.itemButtonTap
        val testState = TestHomeState.data
        val testMutation = interactor.mutation(testCommand, testState).test()

        testMutation.assertValueCount(1)
        testMutation.assertValue(TestHomeMutation.updateInfo)
    }

    @Test
    fun should_emit_correct_state_when_item_button_command_is_emitted() {
        val testMutation = TestHomeMutation.updateInfo
        val testState = TestHomeState.data
        val testNewState = interactor.reduce(testMutation, testState)

        assertTrue { testNewState.equals(TestHomeState.infoChange) }
    }

    @After
    fun after() {
        processInput.onComplete()
        processOutput.dispose()
    }
}