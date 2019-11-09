package home.interactor.usecase

import home.di.SearchUseCaseType
import home.model.EmptyResponse
import home.model.HomeError
import home.model.HomeMutation
import io.reactivex.Observable
import network.ZomatoApi

class SearchUseCase(private val api: ZomatoApi) : SearchUseCaseType() {

    override fun execute(parameters: String): Observable<HomeMutation> =
        api.fetchRestaurants(parameters)
            .map { response -> response.restaurants.map { it.restaurant } }
            .map<HomeMutation> {
                if (it.isEmpty()) { throw EmptyResponse }
                else HomeMutation.UpdateData(it)
            }
            .onErrorReturn { HomeError.treat(it) }
            .startWith(HomeMutation.DisableButton)
}