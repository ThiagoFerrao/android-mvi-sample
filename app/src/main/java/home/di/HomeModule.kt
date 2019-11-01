package home.di

import base.RxInteracting
import base.RxPresenting
import home.interactor.HomeInteractor
import home.interactor.usecase.QuoteButtonTapUseCase
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
        scoped { QuoteButtonTapUseCase(get()) }
        scoped { HomeState(data = null, buttonText = "Get a Quote", isButtonEnable = true) }
        scoped<RxInteracting<HomeCommand, HomeMutation, HomeState>> {
            HomeInteractor(
                get(),
                get<QuoteButtonTapUseCase>()
            )
        }
        scoped<RxPresenting<HomeState, HomeViewModel>> { HomePresenter() }
    }
}