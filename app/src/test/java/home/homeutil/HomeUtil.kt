package home.homeutil

import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import home.model.HomeViewModel
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
}

object TestHomeViewModel {
    val withData = HomeViewModel(
        restaurantList,
        true,
        null
    )
    val withDisableButton = HomeViewModel(
        restaurantList,
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
    val test = HomeCommand.ButtonTap("test")
    val search = HomeCommand.ButtonTap("search")
}

object TestHomeMutation {
    val data = HomeMutation.UpdateData(restaurantList)
    val error = HomeMutation.Error(testErrorMessage)
    val disableButton = HomeMutation.DisableButton
}

private const val testErrorMessage = "Unable to use the parameters defined in the request"

private val restaurantList = listOf<ZomatoRestaurant>(
    ZomatoRestaurant(
        "6713153",
        "Porto Brazil",
        ZomatoLocation("Rua Doutor Álvaro Alvim, 210, Vila Mariana, São Paulo"),
        "Brazilian, Bar Food",
        "11:00 to 24:00 (Mon-Sat),Closed (Sun)"
    ),
    ZomatoRestaurant(
        "6714814",
        "Batata Brazil",
        ZomatoLocation("Osasco Plaza Shopping - Térreo, Rua Tenente Avelar Pires de Azevedo, 81, Centro, Osasco"),
        "American, Fast Food",
        "10:00 to 22:00 (Mon-Sat),11:00 to 22:00 (Sun)"
    ),
    ZomatoRestaurant(
        "6703023",
        "Porto Brazil Hamburgueria",
        ZomatoLocation("Rua Doutor Alvaro Alvim, 210, Vila Mariana, São Paulo"),
        "American",
        "11:00 to 24:00 (Mon-Sat),Closed (Sun)"
    ),
    ZomatoRestaurant(
        "6706290",
        "Garage Sale Brazil",
        ZomatoLocation("Alameda Barão de Limeira, 71, República, São Paulo"),
        "Cafe",
        "09:00 to 17:00 (Mon-Fri),09:00 to 16:00 (Sat),Closed (Sun)"
    )
)