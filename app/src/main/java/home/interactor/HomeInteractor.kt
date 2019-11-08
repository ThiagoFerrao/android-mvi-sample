package home.interactor

import home.di.HomeInteractorType
import home.di.SearchUseCaseType
import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import io.reactivex.Observable
import network.SchedulerProvider

class HomeInteractor(
    override val initialState: HomeState,
    override val schedulerProvider: SchedulerProvider,
    private val searchUseCase: SearchUseCaseType
) : HomeInteractorType(initialState, schedulerProvider) {

    override fun mutation(command: HomeCommand, currentState: HomeState): Observable<HomeMutation> {
        return when (command) {
            is HomeCommand.ButtonTap -> searchUseCase.execute(command.searchValue)
        }
    }

    override fun reduce(mutation: HomeMutation, currentState: HomeState): HomeState {
        val newState = currentState.copy()
        newState.isButtonEnable = true

        when (mutation) {
            is HomeMutation.DisableButton -> newState.isButtonEnable = false
            is HomeMutation.UpdateData -> {
                newState.data = mutation.data
                newState.errorMessage = null
            }
            is HomeMutation.Error -> {
                newState.data = null
                newState.errorMessage = mutation.message
            }
        }
        return newState
    }
}