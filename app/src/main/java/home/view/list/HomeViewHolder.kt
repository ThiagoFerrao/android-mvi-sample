package home.view.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_view_holder.view.*
import network.ZomatoRestaurant

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(data: ZomatoRestaurant) {
        itemView.itemName.text = data.name
    }
}