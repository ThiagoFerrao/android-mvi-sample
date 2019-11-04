package home.model

import network.ZomatoRestaurant

data class HomeViewModel(
    val restaurantList: List<ZomatoRestaurant>,
    val isButtonEnable: Boolean,
    val errorMessage: String?
)