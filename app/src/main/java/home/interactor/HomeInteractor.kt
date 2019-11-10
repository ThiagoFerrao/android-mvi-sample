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

            is HomeCommand.ItemButtonTap -> Observable.just(HomeMutation.UpdateShowInfo(command.itemId))
        }
    }

    override fun reduce(mutation: HomeMutation, currentState: HomeState): HomeState {
        val newState = currentState.copy()
        newState.isButtonEnable = true

        when (mutation) {

            is HomeMutation.UpdateData -> {
                newState.data = mutation.data
                newState.errorMessage = null
            }

            is HomeMutation.UpdateShowInfo -> {
                val newData = newState.data?.map {
                    if (it.id == mutation.restaurantId) it.copy(showInfo = !it.showInfo)
                    else it
                }
                newState.data = newData
            }

            is HomeMutation.DisableButton -> newState.isButtonEnable = false

            is HomeMutation.Error -> {
                newState.data = null
                newState.errorMessage = mutation.message
            }
        }
        return newState
    }
}