package network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ZomatoApi {
    @GET("categories")
    fun fetchCategories(): Observable<ZomatoCategoriesResponse>

    @GET("search")
    fun fetchRestaurants(@Query("q") searchValue: String, @Query("count") count: Int = 1): Observable<ZomatoRestaurantsResponse>
}