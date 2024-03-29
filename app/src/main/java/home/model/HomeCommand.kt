package home.model

sealed class HomeCommand {
    data class ButtonTap(val searchValue: String) : HomeCommand()
    data class ItemButtonTap(val itemId: String) : HomeCommand()
}