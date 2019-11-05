package rxbase

import io.reactivex.Observable

interface RxPresenting<State, ViewModel> {
    fun adapt(input: Observable<State>): Observable<ViewModel>
    fun stateToViewModel(state: State): ViewModel
}

abstract class RxPresenter<State, ViewModel> : RxPresenting<State, ViewModel> {

    override fun adapt(input: Observable<State>): Observable<ViewModel> {
        return input.map { stateToViewModel(it) }
    }
}