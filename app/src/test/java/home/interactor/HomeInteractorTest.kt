package home.interactor

import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class HomeInteractorTest {

    private lateinit var interactor: HomeInteractor

    private lateinit var processInput: PublishSubject<HomeCommand>
    private lateinit var processOutput: TestObserver<HomeState>
    private lateinit var mutationOutput: TestObserver<HomeMutation>

    @Before
    fun before() {
        val initialButtonTitle = "Clique"

        interactor = HomeInteractor(HomeState(initialButtonTitle))
        processInput = PublishSubject.create()
        processOutput = TestObserver.create()
        mutationOutput = TestObserver.create()
    }

    @Test
    fun should_emit_correct_initial_state_before_any_command_was_emitted() {
        val buttonTitleClick = "Clique"

        processOutput = interactor.process(processInput).test()

        processOutput.assertValueCount(1)
        processOutput.assertValue(HomeState(buttonTitleClick))
    }

    @Test
    fun should_emit_correct_state_after_command_was_emitted() {
        val buttonTitleClick = "Clique"
        val buttonTitleTap = "Toque"

        processOutput = interactor.process(processInput).test()
        processInput.onNext(HomeCommand.BUTTON_TAP)

        processOutput.assertValueCount(2)
        processOutput.assertValues(
            HomeState(buttonTitleClick),
            HomeState(buttonTitleTap)
        )
    }

    @Test
    fun should_emit_correct_mutation_after_calling_the_interactor_mutation_function() {
        val buttonTitleClick = "Clique"
        val buttonTitleTap = "Toque"

        mutationOutput = interactor
            .mutation(HomeCommand.BUTTON_TAP, HomeState(buttonTitleClick))
            .test()

        mutationOutput.assertValueCount(1)

        val value = mutationOutput.values().first()
        assertTrue { value is HomeMutation.BUTTON_TITLE }
        assertTrue { (value as HomeMutation.BUTTON_TITLE).title == buttonTitleTap }
    }

    @Test
    fun should_emit_correct_mutation_after_calling_the_interactor_mutation_function_with_other_result() {
        val buttonTitleClick = "Clique"
        val buttonTitleTap = "Toque"

        mutationOutput = interactor
            .mutation(HomeCommand.BUTTON_TAP, HomeState(buttonTitleTap))
            .test()

        mutationOutput.assertValueCount(1)

        val value = mutationOutput.values().first()
        assertTrue { value is HomeMutation.BUTTON_TITLE }
        assertTrue { (value as HomeMutation.BUTTON_TITLE).title == buttonTitleClick }
    }

    @Test
    fun should_return_correct_state_after_calling_the_interactor_reduce_function() {
        val buttonTitleClick = "Clique"
        val buttonTitleTap = "Toque"

        val reducerOutput = interactor.reduce(
            HomeMutation.BUTTON_TITLE(buttonTitleClick),
            HomeState(buttonTitleTap)
        )

        assertTrue { reducerOutput == HomeState(buttonTitleClick) }
    }

    @After
    fun after() {
        processInput.onComplete()
        processOutput.dispose()
        mutationOutput.dispose()
    }
}


//fun process(input: Observable<Command>): Observable<State>
//fun mutation(command: Command, currentState: State): Observable<Mutation>
//fun reduce(mutation: Mutation, currentState: State): State