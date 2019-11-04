package home.view.list

import androidx.recyclerview.widget.DiffUtil
import network.ZomatoRestaurant

class HomeDiffUtil : DiffUtil.ItemCallback<ZomatoRestaurant>() {

    override fun areItemsTheSame(oldItem: ZomatoRestaurant, newItem: ZomatoRestaurant): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ZomatoRestaurant, newItem: ZomatoRestaurant): Boolean =
        oldItem == newItem
}