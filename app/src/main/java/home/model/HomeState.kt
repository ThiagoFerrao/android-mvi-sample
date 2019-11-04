package home.model

import network.ZomatoRestaurant

data class HomeState(
    var data: List<ZomatoRestaurant>?,
    var isButtonEnable: Boolean,
    var errorMessage: String?
)