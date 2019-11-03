package home.homeutil

import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import home.model.HomeViewModel
import network.ZomatoLocation
import network.ZomatoRestaurant

object TestHomeState {
    val data = HomeState(
        ZomatoRestaurant(
            "6713153",
            "Porto Brazil",
            ZomatoLocation("Rua Doutor Álvaro Alvim, 210, Vila Mariana, São Paulo"),
            "Brazilian, Bar Food",
            "11:00 to 24:00 (Mon-Sat),Closed (Sun)"
        ),
        "button",
        true,
        null
    )
    val disableButton = HomeState(
        ZomatoRestaurant(
            "6713153",
            "Porto Brazil",
            ZomatoLocation("Rua Doutor Álvaro Alvim, 210, Vila Mariana, São Paulo"),
            "Brazilian, Bar Food",
            "11:00 to 24:00 (Mon-Sat),Closed (Sun)"
        ),
        "button",
        false,
        null
    )
    val error = HomeState(
        null,
        "button",
        true,
        "Unable to use the parameters defined in the request"
    )
}

object TestHomeViewModel {
    val withData = HomeViewModel("Porto Brazil", "button", true)
    val withDisableButton = HomeViewModel("Porto Brazil", "button", false)
    val withError = HomeViewModel("Unable to use the parameters defined in the request", "button", true)
}

object TestHomeCommand {
    val test = HomeCommand.ButtonTap("test")
    val search = HomeCommand.ButtonTap("search")
}

object TestHomeMutation {
    val data = HomeMutation.UpdateData(
        ZomatoRestaurant(
            "6713153",
            "Porto Brazil",
            ZomatoLocation("Rua Doutor Álvaro Alvim, 210, Vila Mariana, São Paulo"),
            "Brazilian, Bar Food",
            "11:00 to 24:00 (Mon-Sat),Closed (Sun)"
        )
    )
    val error = HomeMutation.Error("Unable to use the parameters defined in the request")
    val disableButton = HomeMutation.DisableButton
}