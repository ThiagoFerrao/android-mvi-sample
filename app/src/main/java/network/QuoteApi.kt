package network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {
    @GET(".")
    fun fetchQuotes(@Query("cat") category: String, @Query("count") count: String = "1"): Single<List<QuoteResponse>>
}