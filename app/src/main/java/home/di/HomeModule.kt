package home.di

import base.RxInteracting
import base.RxPresenting
import home.interactor.HomeInteractor
import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import home.model.HomeViewModel
import home.presenter.HomePresenter
import home.view.HomeActivity
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module(override = true) {
    scope(named<HomeActivity>()) {
        scoped<RxPresenting<HomeState, HomeViewModel>> { HomePresenter() }
        scoped {  HomeState("Toque") }
        scoped<RxInteracting<HomeCommand, HomeMutation, HomeState>> { HomeInteractor(get()) }
    }
}