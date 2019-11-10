package home.model

data class HomeViewModel(
    val restaurantList: List<RestaurantViewModel>,
    val isButtonEnable: Boolean,
    val errorMessage: String?
)

data class RestaurantViewModel(
    val id: String,
    val name: String,
    val cuisines: String,
    val timings: String,
    val address: String,
    val showInfo: Boolean
)