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
import io.reactivex.Observer
import network.ZomatoRestaurant
import org.koin.core.qualifier.named
import org.koin.dsl.module
import rxbase.RxInteracting
import rxbase.RxListAdapter
import rxbase.RxPresenting
import rxbase.RxViewHolder

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

        scoped {
            SearchUseCase(
                api = get()
            )
        }

        scoped<RxInteracting<HomeCommand, HomeMutation, HomeState>> {
            HomeInteractor(
                initialState = get(),
                schedulerProvider = get(),
                searchUseCase = get<SearchUseCase>()
            )
        }

        // Presenter Injection
        scoped<RxPresenting<HomeState, HomeViewModel>> {
            HomePresenter()
        }

        // RecyclerView Injection
        scoped<RecyclerView.LayoutManager> {
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        }

        scoped<DiffUtil.ItemCallback<ZomatoRestaurant>>{
            HomeDiffUtil()
        }
        
        scoped<RxListAdapter<HomeCommand, ZomatoRestaurant, RxViewHolder<HomeCommand, ZomatoRestaurant>>>
        { (output: Observer<HomeCommand>) ->
            HomeAdapter(
                diffUtil = get(),
                viewOutput = output
            )
        }
    }
}