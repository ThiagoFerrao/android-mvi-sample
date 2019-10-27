package home.model

sealed class HomeMutation {
    data class BUTTON_TITLE(val title: String) : HomeMutation()
}