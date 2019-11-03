package home.util

import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import home.model.HomeViewModel
import network.ZomatoLocation
import network.ZomatoRestaurant

object TestHomeState {
    val data = HomeState(
        ZomatoRestaurant("id", "name", ZomatoLocation("address"), "cuisines", "timings"),
        "buttonText",
        true,
        null
    )
    val disableButton = HomeState(
        ZomatoRestaurant("id", "name", ZomatoLocation("address"), "cuisines", "timings"),
        "buttonText",
        false,
        null
    )
    val error = HomeState(
        null,
        "buttonText",
        true,
        "errorMessage"
    )
}

object TestHomeViewModel {
    val withData = HomeViewModel("name", "buttonText", true)
    val withDisableButton = HomeViewModel("name", "buttonText", false)
    val withError = HomeViewModel("errorMessage", "buttonText", true)
}

object TestHomeCommand {
    val test = HomeCommand.ButtonTap("test")
    val search = HomeCommand.ButtonTap("search")
}

object TestHomeMutation {
    val data = HomeMutation.UpdateData(
        ZomatoRestaurant("id", "name", ZomatoLocation("address"), "cuisines", "timings")
    )
    val error = HomeMutation.Error("errorMessage")
    val disableButton = HomeMutation.DisableButton
}