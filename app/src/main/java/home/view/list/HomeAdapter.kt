package home.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import home.di.HomeAdapterType
import home.di.HomeDiffUtilType
import home.di.HomeViewHolderType
import home.model.HomeCommand
import io.reactivex.Observer
import thiagocruz.testingthings.R

class HomeAdapter(
    override val diffUtil: HomeDiffUtilType,
    override val viewOutput: Observer<HomeCommand>
) : HomeAdapterType(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolderType {
        val inflater = LayoutInflater.from(parent.context)
        return HomeViewHolder(inflater.inflate(R.layout.home_view_holder, parent, false))
    }

    override fun onBindViewHolder(holder: HomeViewHolderType, position: Int) {
        super.onBindViewHolder(holder, position)

        val restaurantId = getItem(position).id
        (holder as? HomeViewHolder)?.bindOutputTo(viewOutput, restaurantId)
    }
}