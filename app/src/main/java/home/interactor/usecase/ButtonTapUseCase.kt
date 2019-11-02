package home.interactor.usecase

import base.RxUseCase
import home.model.HomeMutation
import io.reactivex.Observable
import network.ZomatoApi
import util.treatMessage

class ButtonTapUseCase(private val api: ZomatoApi) : RxUseCase<String, HomeMutation>() {

    override fun execute(parameters: String): Observable<HomeMutation> =
        api.fetchRestaurants(parameters)
            .map { it.restaurants.first() }
            .map<HomeMutation> { HomeMutation.UpdateData(it.restaurant) }
            .onErrorReturn { HomeMutation.Error(it.treatMessage) }
            .startWith(HomeMutation.DisableButton)
}