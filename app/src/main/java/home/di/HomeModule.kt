package home.di

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import home.interactor.HomeInteractor
import home.interactor.usecase.SearchUseCase
import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import home.model.HomeViewModel
import home.presenter.HomePresenter
import home.view.HomeActivity
import home.view.list.HomeAdapter
import home.view.list.HomeDiffUtil
import io.reactivex.Observable
import io.reactivex.Observer
import network.ZomatoRestaurant
import org.koin.core.qualifier.named
import org.koin.dsl.module
import rxbase.*

typealias SearchUseCaseType =  RxUseCase<String, HomeMutation>
typealias HomeInteractorType = RxInteractor<HomeCommand, HomeMutation, HomeState>
typealias HomePresenterType = RxPresenter<HomeState, HomeViewModel>
typealias HomeAdapterType = RxListAdapter<HomeCommand, ZomatoRestaurant, HomeViewHolderType>
typealias HomeViewHolderType = RxViewHolder<HomeCommand, ZomatoRestaurant>
typealias HomeDiffUtilType = DiffUtil.ItemCallback<ZomatoRestaurant>
typealias HomeActivityType = RxActivity<HomeCommand, HomeMutation, HomeState, HomeViewModel>

val homeModule = module {
    scope(named<HomeActivity>()) {

        // Interactor Injection
        scoped {
            HomeState(
                data = null,
                isButtonEnable = true,
                errorMessage = null
            )
        }

        scoped<SearchUseCaseType> {
            SearchUseCase(
                api = get()
            )
        }

        scoped<HomeInteractorType> {
            HomeInteractor(
                initialState = get(),
                schedulerProvider = get(),
                searchUseCase = get()
            )
        }

        // Presenter Injection
        scoped<HomePresenterType> {
            HomePresenter()
        }

        // RecyclerView Injection
        scoped<RecyclerView.LayoutManager> {
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        scoped<HomeDiffUtilType> {
            HomeDiffUtil()
        }
        
        scoped<HomeAdapterType> { (output: Observer<HomeCommand>) ->
            HomeAdapter(
                diffUtil = get(),
                viewOutput = output
            )
        }

        // View Input Process Injection
        scoped { (output: Observable<HomeCommand>) ->
            get<HomePresenterType>().adapt(get<HomeInteractorType>().process(output))
        }
    }
}