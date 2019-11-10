package home.homeutil

import home.model.*
import network.ZomatoLocation
import network.ZomatoRestaurant

object TestHomeState {
    val data = HomeState(
        restaurantList,
        true,
        null
    )
    val disableButton = HomeState(
        restaurantList,
        false,
        null
    )
    val error = HomeState(
        null,
        true,
        testErrorMessage
    )
    val infoChange = HomeState(
        restaurantInfoList,
        true,
        null
    )
}

object TestHomeViewModel {
    val withData = HomeViewModel(
        restaurantViewModelList,
        true,
        null
    )
    val withDisableButton = HomeViewModel(
        restaurantViewModelList,
        false,
        null
    )
    val withError = HomeViewModel(
        listOf(),
        true,
        testErrorMessage
    )
}

object TestHomeCommand {
    val buttonTap = HomeCommand.ButtonTap("test")
    val itemButtonTap = HomeCommand.ItemButtonTap("6713153")
    val search = HomeCommand.ButtonTap("search")
}

object TestHomeMutation {
    val data = HomeMutation.UpdateData(restaurantList)
    val error = HomeMutation.Error(testErrorMessage)
    val disableButton = HomeMutation.DisableButton
    val updateInfo = HomeMutation.UpdateShowInfo("6713153")
}

private const val testErrorMessage = "Unable to use the parameters defined in the request"

private val restaurantList = listOf(
    ZomatoRestaurant(
        "6713153",
        "Porto Brazil",
        ZomatoLocation("Rua Doutor Álvaro Alvim, 210, Vila Mariana, São Paulo"),
        "Brazilian, Bar Food",
        "11:00 to 24:00 (Mon-Sat),Closed (Sun)",
        false
    ),
    ZomatoRestaurant(
        "6714814",
        "Batata Brazil",
        ZomatoLocation("Osasco Plaza Shopping - Térreo, Rua Tenente Avelar Pires de Azevedo, 81, Centro, Osasco"),
        "American, Fast Food",
        "10:00 to 22:00 (Mon-Sat),11:00 to 22:00 (Sun)",
        false
    )
)

private val restaurantViewModelList = listOf(
    RestaurantViewModel(
        "6713153",
        "Porto Brazil",
        "Brazilian, Bar Food",
        "11:00 to 24:00 (Mon-Sat),Closed (Sun)",
        "Rua Doutor Álvaro Alvim, 210, Vila Mariana, São Paulo",
        false
    ),
    RestaurantViewModel(
        "6714814",
        "Batata Brazil",
        "American, Fast Food",
        "10:00 to 22:00 (Mon-Sat),11:00 to 22:00 (Sun)",
        "Osasco Plaza Shopping - Térreo, Rua Tenente Avelar Pires de Azevedo, 81, Centro, Osasco",
        false
    )
)

private val restaurantInfoList = listOf(
    ZomatoRestaurant(
        "6713153",
        "Porto Brazil",
        ZomatoLocation("Rua Doutor Álvaro Alvim, 210, Vila Mariana, São Paulo"),
        "Brazilian, Bar Food",
        "11:00 to 24:00 (Mon-Sat),Closed (Sun)",
        true
    ),
    ZomatoRestaurant(
        "6714814",
        "Batata Brazil",
        ZomatoLocation("Osasco Plaza Shopping - Térreo, Rua Tenente Avelar Pires de Azevedo, 81, Centro, Osasco"),
        "American, Fast Food",
        "10:00 to 22:00 (Mon-Sat),11:00 to 22:00 (Sun)"
    )
)