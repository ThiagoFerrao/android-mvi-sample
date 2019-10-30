package home.presenter

import home.model.HomeState
import home.model.HomeViewModel
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
        output = TestObserver.create()
    }

    @Test
    fun should_not_emit_view_model_if_state_wasnt_emitted() {
        output = presenter.adapt(input).test()

        output.assertNoValues()
    }

    @Test
    fun should_emit_correct_view_model_after_state_was_emitted() {
        val buttonTitle = "Clique"

        output = presenter.adapt(input).test()
        input.onNext(HomeState(buttonTitle))

        output.assertValueCount(1)
        output.assertValue(HomeViewModel(buttonTitle))
    }

    @Test
    fun should_emit_correct_multiple_view_models_after_multiple_states_were_emitted() {
        val buttonTitleClick = "Clique"
        val buttonTitleTap = "Toque"

        output = presenter.adapt(input).test()
        input.onNext(HomeState(buttonTitleTap))
        input.onNext(HomeState(buttonTitleClick))
        input.onNext(HomeState(buttonTitleTap))
        input.onNext(HomeState(buttonTitleClick))
        input.onNext(HomeState(buttonTitleClick))

        output.assertValueCount(5)
        output.assertValues(
            HomeViewModel(buttonTitleTap),
            HomeViewModel(buttonTitleClick),
            HomeViewModel(buttonTitleTap),
            HomeViewModel(buttonTitleClick),
            HomeViewModel(buttonTitleClick)
        )
    }

    @After
    fun after() {
        input.onComplete()
        output.dispose()
    }
}