package home.model

import network.ZomatoRestaurant

sealed class HomeMutation {
    data class UpdateData(val data: List<ZomatoRestaurant>) : HomeMutation()
    data class UpdateShowInfo(val restaurantId: String) : HomeMutation()
    object DisableButton : HomeMutation()
    data class Error(val message: String) : HomeMutation()
}