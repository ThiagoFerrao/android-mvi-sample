package home.model

import network.ZomatoRestaurant

data class HomeState(
    var data: ZomatoRestaurant?,
    var buttonText: String,
    var isButtonEnable: Boolean,
    var errorMessage: String?
)