package home.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import network.ZomatoRestaurant
import thiagocruz.testingthings.R

class HomeAdapter : ListAdapter<ZomatoRestaurant, HomeViewHolder>(HomeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HomeViewHolder(inflater.inflate(R.layout.home_view_holder, parent, false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun updateData(data: List<ZomatoRestaurant>) {
        submitList(data)
    }
}