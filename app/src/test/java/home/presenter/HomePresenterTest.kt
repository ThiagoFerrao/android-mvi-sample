package home.presenter

import home.model.HomeState
import home.model.HomeViewModel
import home.util.TestHomeState
import home.util.TestHomeViewModel
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomePresenterTest {

    private lateinit var presenter: HomePresenter

    private lateinit var input: PublishSubject<HomeState>
    private lateinit var output: TestObserver<HomeViewModel>

    @Before
    fun before() {
        presenter = HomePresenter()
        input = PublishSubject.create()
        output = presenter.adapt(input).test()
    }

    @Test
    fun should_not_emit_view_model_if_state_wasnt_emitted() {
        output.assertNoValues()
    }

    @Test
    fun should_emit_correct_view_model_after_state_was_emitted() {
        input.onNext(TestHomeState.data)

        output.assertValueCount(1)
        output.assertValue(TestHomeViewModel.withData)
    }

    @Test
    fun should_emit_correct_multiple_view_models_after_multiple_states_were_emitted() {
        input.onNext(TestHomeState.data)
        input.onNext(TestHomeState.disableButton)
        input.onNext(TestHomeState.error)

        output.assertValueCount(3)
        output.assertValues(
            TestHomeViewModel.withData,
            TestHomeViewModel.withDisableButton,
            TestHomeViewModel.withError
        )
    }

    @After
    fun after() {
        input.onComplete()
        output.dispose()
    }
}