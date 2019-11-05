package rxbase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import network.SchedulerProvider
import rxbase.RxViewing.Companion.NO_LAYOUT

interface RxViewing<Command, Mutation, State, ViewModel> {
    val layoutResource: Int

    val presenter: RxPresenting<State, ViewModel>
    val interactor: RxInteracting<Command, Mutation, State>

    val input: Observable<ViewModel>
    val output: PublishSubject<Command>
    val disposer: CompositeDisposable
    val schedulerProvider: SchedulerProvider

    fun createBindings(input: Observable<ViewModel>): ArrayList<Disposable>
    fun render(viewModel: ViewModel)

    companion object {
        const val NO_LAYOUT: Int = 0
    }
}

abstract class RxActivity<Command, Mutation, State, ViewModel> :
    AppCompatActivity(), RxViewing<Command, Mutation, State, ViewModel> {

    override val output: PublishSubject<Command> = PublishSubject.create()
    override val disposer: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutResource != NO_LAYOUT) setContentView(layoutResource)

        val bindings = createBindings(input)
        disposer.addAll(bindings)
    }

    override fun createBindings(input: Observable<ViewModel>): ArrayList<Disposable> =
        arrayListOf(
            input
                .observeOn(schedulerProvider.ui())
                .subscribe { this.render(it) }
        )

    override fun onDestroy() {
        output.onComplete()
        disposer.dispose()
        super.onDestroy()
    }
}

private fun CompositeDisposable.addAll(bindings: List<Disposable>) {
    for (binding in bindings) add(binding)
}