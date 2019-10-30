package base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import base.RxViewing.Companion.NO_LAYOUT
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

abstract class RxActivity<Command, Mutation, State, ViewModel> :
    RxViewing<Command, Mutation, State, ViewModel>, AppCompatActivity() {

    override val input: Observable<ViewModel> by lazy { processBinding() }
    override val output: PublishSubject<Command> = PublishSubject.create()
    override val disposer: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutResource != NO_LAYOUT) setContentView(layoutResource)

        val bindings = createBindings(input)
        disposer.addAll(bindings)
    }

    override fun processBinding(): Observable<ViewModel> =
        presenter.adapt(interactor.process(output))

    override fun createBindings(input: Observable<ViewModel>): ArrayList<Disposable> =
        arrayListOf( input.subscribe { this.render(it) } )

    override fun onDestroy() {
        output.onComplete()
        disposer.dispose()
        super.onDestroy()
    }
}

private fun CompositeDisposable.addAll(bindings: List<Disposable>) {
    for (binding in bindings) add(binding)
}