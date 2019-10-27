package base

import io.reactivex.Observable

abstract class RxPresenter<State, ViewModel> : RxPresenting<State, ViewModel> {

    override fun adapt(input: Observable<State>): Observable<ViewModel> {
        return input.map { stateToViewModel(it) }
    }
}