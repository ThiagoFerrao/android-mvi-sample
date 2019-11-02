package home.model

sealed class HomeCommand {
    data class ButtonTap(val searchValue: String) : HomeCommand()
}