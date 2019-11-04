package home.di

import base.RxInteracting
import base.RxPresenting
import home.interactor.HomeInteractor
import home.interactor.usecase.SearchUseCase
import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import home.model.HomeViewModel
import home.presenter.HomePresenter
import home.view.HomeActivity
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module {
    scope(named<HomeActivity>()) {
        scoped { SearchUseCase(api = get()) }
        scoped { HomeState(data = null, isButtonEnable = true, errorMessage = null) }
        scoped<RxInteracting<HomeCommand, HomeMutation, HomeState>> {
            HomeInteractor(
                initialState = get(),
                schedulerProvider = get(),
                searchUseCase = get<SearchUseCase>()
            )
        }
        scoped<RxPresenting<HomeState, HomeViewModel>> { HomePresenter() }
    }
}