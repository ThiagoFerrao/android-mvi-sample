package home.presenter

import base.RxPresenter
import home.model.HomeState
import home.model.HomeViewModel

class HomePresenter : RxPresenter<HomeState, HomeViewModel>() {

    override fun stateToViewModel(state: HomeState): HomeViewModel {
        return HomeViewModel(buttonTitle = state.buttonTitle)
    }
}