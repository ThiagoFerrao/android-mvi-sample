package base

import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable

interface ViewHolderBinding<Command, OutputData> {
    val disposer: CompositeDisposable

    fun createOutput(outputData: OutputData): Observable<Command>
    fun bindOutputTo(observer: Observer<Command>, outputData: OutputData)
}

abstract class RxViewHolder<Command, ItemType, OutputData>(
    override val containerView: View
) : BaseViewHolder<Command, ItemType>(containerView), ViewHolderBinding<Command, OutputData> {

    override val disposer: CompositeDisposable = CompositeDisposable()

    override fun bind(data: ItemType) {
        disposer.clear()
    }

    override fun bindOutputTo(observer: Observer<Command>, outputData: OutputData) {
        val disposable = createOutput(outputData).subscribe { observer.onNext(it) }
        disposer.add(disposable)
    }
}

fun <Command, ItemType> RxViewHolder<Command, ItemType, Unit>.bindOutputTo(observer: Observer<Command>) =
    bindOutputTo(observer, Unit)