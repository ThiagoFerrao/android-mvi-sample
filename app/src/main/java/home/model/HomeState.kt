package home.model

import network.QuoteResponse

data class HomeState(
    var data: QuoteResponse?,
    var buttonText: String,
    var isButtonEnable: Boolean
)