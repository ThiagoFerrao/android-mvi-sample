package rxbase

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.extensions.LayoutContainer

interface RxViewHolding<Command, ItemType> {
    val containerView: View
    val disposer: CompositeDisposable

    fun bind(data: ItemType)
    fun createOutput(): Observable<Command>
    fun bindOutputTo(observer: Observer<Command>)
}

abstract class RxViewHolder<Command, ItemType>(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), RxViewHolding<Command, ItemType>, LayoutContainer {

    override val disposer: CompositeDisposable = CompositeDisposable()

    override fun bind(data: ItemType) {
        disposer.clear()
    }

    override fun bindOutputTo(observer: Observer<Command>) {
        val disposable = createOutput().subscribe { observer.onNext(it) }
        disposer.add(disposable)
    }
}