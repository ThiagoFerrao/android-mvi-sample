package base

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

interface RxViewing<Command, Mutation, State, ViewModel> {
    val layoutResource: Int

    val presenter: RxPresenting<State, ViewModel>
    val interactor: RxInteracting<Command, Mutation, State>

    val input: Observable<ViewModel>
    val output: PublishSubject<Command>
    val disposer: CompositeDisposable

    fun processBinding(): Observable<ViewModel>
    fun createBindings(input: Observable<ViewModel>): ArrayList<Disposable>
    fun render(viewModel: ViewModel)

    companion object {
        const val NO_LAYOUT: Int = 0
    }
}

interface RxPresenting<State, ViewModel> {
    fun adapt(input: Observable<State>): Observable<ViewModel>
    fun stateToViewModel(state: State): ViewModel
}

interface RxInteracting<Command, Mutation, State> {
    val initialState: State

    fun process(input: Observable<Command>): Observable<State>
    fun mutation(command: Command, currentState: State): Observable<Mutation>
    fun reduce(mutation: Mutation, currentState: State): State
}

interface RxUseCasing<Parameters, Mutation> {
    fun execute(parameters: Parameters): Observable<Mutation>
}