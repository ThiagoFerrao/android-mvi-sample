package home.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import home.model.HomeCommand
import io.reactivex.Observer
import network.ZomatoRestaurant
import rxbase.RxListAdapter
import rxbase.RxViewHolder
import thiagocruz.testingthings.R

class HomeAdapter(
    override val diffUtil: DiffUtil.ItemCallback<ZomatoRestaurant>,
    override val viewOutput: Observer<HomeCommand>
) : RxListAdapter<HomeCommand, ZomatoRestaurant, RxViewHolder<HomeCommand, ZomatoRestaurant>>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HomeViewHolder(inflater.inflate(R.layout.home_view_holder, parent, false))
    }
}