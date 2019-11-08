package home.view.list

import home.di.HomeDiffUtilType
import network.ZomatoRestaurant

class HomeDiffUtil : HomeDiffUtilType() {

    override fun areItemsTheSame(oldItem: ZomatoRestaurant, newItem: ZomatoRestaurant): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ZomatoRestaurant, newItem: ZomatoRestaurant): Boolean =
        oldItem == newItem
}