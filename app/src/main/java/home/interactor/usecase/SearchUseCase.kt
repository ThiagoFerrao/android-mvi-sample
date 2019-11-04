package home.interactor.usecase

import base.RxUseCase
import home.model.HomeMutation
import io.reactivex.Observable
import network.ZomatoApi
import util.treatMessage

class SearchUseCase(private val api: ZomatoApi) : RxUseCase<String, HomeMutation>() {

    override fun execute(parameters: String): Observable<HomeMutation> =
        api.fetchRestaurants(parameters)
            .map { response -> response.restaurants.map { it.restaurant } }
            .map<HomeMutation> {
                if (it.isEmpty()) {
                    HomeMutation.Error("Unable to find any restaurant")
                } else HomeMutation.UpdateData(it)
            }
            .onErrorReturn { HomeMutation.Error(it.treatMessage) }
            .startWith(HomeMutation.DisableButton)
}