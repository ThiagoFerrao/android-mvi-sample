package home.presenter

import home.di.HomePresenterType
import home.model.HomeState
import home.model.HomeViewModel

class HomePresenter : HomePresenterType() {

    override fun stateToViewModel(state: HomeState): HomeViewModel {
        return HomeViewModel(
            restaurantList = state.data.orEmpty(),
            isButtonEnable = state.isButtonEnable,
            errorMessage = state.errorMessage
        )
    }
}