package home.interactor.usecase

import base.RxUseCase
import home.model.HomeMutation
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import network.QuoteApi
import network.QuoteResponse

class QuoteButtonTapUseCase(private val api: QuoteApi) : RxUseCase<Unit, HomeMutation>() {

    override fun execute(parameters: Unit): Observable<HomeMutation> =
        api.fetchQuotes("famous", "1")
            .map { response ->
                HomeMutation.UpdateData(data = response.first()) ?: HomeMutation.DisableButton
            }
            .toObservable()
            .onErrorReturn { error -> HomeMutation.UpdateData(data = QuoteResponse("Error", "", "")) }
            .startWith(HomeMutation.DisableButton)
            .subscribeOn(Schedulers.io())
}