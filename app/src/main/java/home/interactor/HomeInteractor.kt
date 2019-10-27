package home.interactor

import base.RxInteractor
import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import io.reactivex.Observable

class HomeInteractor(override val initialState: HomeState) :
    RxInteractor<HomeCommand, HomeMutation, HomeState>(initialState) {

    override fun mutation(command: HomeCommand, currentState: HomeState): Observable<HomeMutation> {
        return when (command) {
            HomeCommand.BUTTON_TAP -> {
                val mutation = if (currentState.buttonTitle == "Clique")
                    HomeMutation.BUTTON_TITLE("Toque")
                else HomeMutation.BUTTON_TITLE("Clique")

                Observable.just(mutation)
            }
        }
    }

    override fun reduce(mutation: HomeMutation, currentState: HomeState): HomeState {
        val newState = currentState.copy()

        when (mutation) {
            is HomeMutation.BUTTON_TITLE -> newState.buttonTitle = mutation.title
        }
        return newState
    }
}