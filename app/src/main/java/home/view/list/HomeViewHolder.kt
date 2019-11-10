package home.view.list

import android.view.View
import base.RxViewHolder
import com.jakewharton.rxbinding2.view.RxView
import home.model.HomeCommand
import home.model.RestaurantViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.home_view_holder.*
import thiagocruz.testingthings.R
import util.show
import util.showWithText
import java.util.concurrent.TimeUnit

class HomeViewHolder(
    override val containerView: View
) : RxViewHolder<HomeCommand, RestaurantViewModel, String>(containerView) {

    override val disposer: CompositeDisposable = CompositeDisposable()

    override fun bind(data: RestaurantViewModel) {
        super.bind(data)

        itemName.showWithText(data.name)
        itemType.showWithText(data.cuisines)

        val showLess = containerView.resources.getString(R.string.item_less_button)
        val showMore = containerView.resources.getString(R.string.item_more_button)
        itemButton.text = if (data.showInfo) showLess else showMore

        if (data.showInfo) {
            itemTiming.showWithText(data.timings)
            itemLocation.showWithText(data.address)

        } else {
            itemTiming.show(false)
            itemLocation.show(false)
        }
    }

    override fun createOutput(outputData: String): Observable<HomeCommand> =
        RxView.clicks(itemButton)
            .map<HomeCommand> { HomeCommand.ItemButtonTap(outputData) }
            .throttleFirst(1, TimeUnit.SECONDS)
}