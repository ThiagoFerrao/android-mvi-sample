package network

import com.google.gson.annotations.SerializedName

// Categories Responses

data class ZomatoCategoriesResponse(
    @SerializedName("categories")
    val categories: List<ZomatoCategoryResponse>
)

data class ZomatoCategoryResponse(
    @SerializedName("categories")
    val category: ZomatoCategory
)

data class ZomatoCategory(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

// Restaurants Responses

data class ZomatoRestaurantsResponse(
    @SerializedName("restaurants")
    val restaurants: List<ZomatoRestaurantResponse>
)

data class ZomatoRestaurantResponse(
    @SerializedName("restaurant")
    val restaurant: ZomatoRestaurant
)

data class ZomatoRestaurant(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("location")
    val location: ZomatoLocation,
    @SerializedName("cuisines")
    val cuisines: String,
    @SerializedName("timings")
    val timings: String,

    var showInfo: Boolean = false
)

data class ZomatoLocation(
    @SerializedName("address")
    val address: String
)