package home.model

import network.QuoteResponse

sealed class HomeMutation {
    object DisableButton : HomeMutation()
    data class UpdateData(val data: QuoteResponse) : HomeMutation()
}