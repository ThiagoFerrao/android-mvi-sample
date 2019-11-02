package home.interactor.usecase

import base.RxUseCase
import home.model.HomeMutation
import io.reactivex.Observable
import network.ZomatoApi

class ButtonTapUseCase(private val api: ZomatoApi) : RxUseCase<String, HomeMutation>() {

    override fun execute(parameters: String): Observable<HomeMutation> =
        api.fetchRestaurants(parameters)
            .map { it.restaurants.first() }
            .map<HomeMutation> { HomeMutation.UpdateData(data = it.restaurant) }
            .onErrorReturn { e -> HomeMutation.Error(message = e.message ?: "Error during request :(") }
            .startWith(HomeMutation.DisableButton)
}