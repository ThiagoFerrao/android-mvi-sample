package home.model

import network.ZomatoRestaurant

sealed class HomeMutation {
    object DisableButton : HomeMutation()
    data class UpdateData(val data: ZomatoRestaurant) : HomeMutation()
    data class Error(val message: String) : HomeMutation()
}