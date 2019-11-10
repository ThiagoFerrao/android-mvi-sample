package home.view.list

import home.di.HomeDiffUtilType
import home.model.RestaurantViewModel

class HomeDiffUtil : HomeDiffUtilType() {

    override fun areItemsTheSame(oldItem: RestaurantViewModel, newItem: RestaurantViewModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RestaurantViewModel, newItem: RestaurantViewModel): Boolean =
        oldItem == newItem
}