package home.view.list

import android.view.View
import home.di.HomeViewHolderType
import home.model.HomeCommand
import io.reactivex.Observable
import kotlinx.android.synthetic.main.home_view_holder.*
import network.ZomatoRestaurant

class HomeViewHolder(
    override val containerView: View
) : HomeViewHolderType(containerView) {

    override fun bind(data: ZomatoRestaurant) {
        super.bind(data)

        itemName.text = data.name
    }

    override fun createOutput(): Observable<HomeCommand> = Observable.empty()
}