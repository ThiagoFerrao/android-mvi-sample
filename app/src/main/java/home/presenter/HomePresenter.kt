package home.presenter

import home.di.HomePresenterType
import home.model.HomeState
import home.model.HomeViewModel
import home.model.RestaurantViewModel
import network.ZomatoRestaurant

class HomePresenter : HomePresenterType() {

    override fun stateToViewModel(state: HomeState): HomeViewModel {
        return HomeViewModel(
            restaurantList = state.data?.map { it.toViewModel() }.orEmpty(),
            isButtonEnable = state.isButtonEnable,
            errorMessage = state.errorMessage
        )
    }
}

private fun ZomatoRestaurant.toViewModel() =
    RestaurantViewModel(
        id = id,
        name = name,
        cuisines = cuisines,
        timings = timings,
        address = location.address,
        showInfo = showInfo
    )