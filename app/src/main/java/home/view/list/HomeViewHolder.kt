package home.view.list

import android.view.View
import home.model.HomeCommand
import io.reactivex.Observable
import kotlinx.android.synthetic.main.home_view_holder.*
import network.ZomatoRestaurant
import rxbase.RxViewHolder

class HomeViewHolder(
    override val containerView: View
) : RxViewHolder<HomeCommand, ZomatoRestaurant>(containerView) {

    override fun bind(data: ZomatoRestaurant) {
        super.bind(data)

        itemName.text = data.name
    }

    override fun createOutput(): Observable<HomeCommand> = Observable.empty()
}
