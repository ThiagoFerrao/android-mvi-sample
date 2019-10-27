package base

import android.content.Context
import android.content.Intent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

interface RxFactory<Command, Mutation, State, ViewModel> {
    fun intent(context: Context): Intent
    fun presenter(): RxPresenting<State, ViewModel>
    fun interactor(): RxInteracting<Command, Mutation, State>
}

interface RxViewing<Command, Mutation, State, ViewModel> {
    val layoutResource: Int

    val factory: RxFactory<Command, Mutation, State, ViewModel>

    var input: Observable<ViewModel>
    val output: PublishSubject<Command>
    val disposer: CompositeDisposable

    fun createBindings(input: Observable<ViewModel>): ArrayList<Disposable>
    fun render(viewModel: ViewModel)

    companion object {
        val NO_LAYOUT: Int = 0
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