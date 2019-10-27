package home.factory

import android.content.Context
import android.content.Intent
import base.RxFactory
import base.RxInteracting
import base.RxPresenting
import home.interactor.HomeInteractor
import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import home.model.HomeViewModel
import home.presenter.HomePresenter
import home.view.HomeActivity

object HomeFactory : RxFactory<HomeCommand, HomeMutation, HomeState, HomeViewModel> {

    override fun intent(context: Context): Intent {
        return Intent(context, HomeActivity::class.java)
    }

    override fun presenter(): RxPresenting<HomeState, HomeViewModel> {
        return HomePresenter()
    }

    override fun interactor(): RxInteracting<HomeCommand, HomeMutation, HomeState> {
        val initialState = HomeState(buttonTitle = "Toque")
        return HomeInteractor(initialState)

    }
}