package home.presenter

import home.model.HomeState
import home.model.HomeViewModel
import rxbase.RxPresenter

class HomePresenter : RxPresenter<HomeState, HomeViewModel>() {

    override fun stateToViewModel(state: HomeState): HomeViewModel {
        return HomeViewModel(
            restaurantList = state.data.orEmpty(),
            isButtonEnable = state.isButtonEnable,
            errorMessage = state.errorMessage
        )
    }
}