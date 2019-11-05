package home.view

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import home.model.HomeViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import network.SchedulerProvider
import network.ZomatoRestaurant
import org.koin.android.ext.android.inject
import org.koin.android.scope.currentScope
import org.koin.core.parameter.parametersOf
import rxbase.*
import thiagocruz.testingthings.R
import util.show

class HomeActivity : RxActivity<HomeCommand, HomeMutation, HomeState, HomeViewModel>() {
    override val layoutResource = R.layout.activity_main

    override val presenter: RxPresenting<HomeState, HomeViewModel> by currentScope.inject()
    override val interactor: RxInteracting<HomeCommand, HomeMutation, HomeState> by currentScope.inject()

    override val input: Observable<HomeViewModel> by lazy { presenter.adapt(interactor.process(output)) }
    override val schedulerProvider: SchedulerProvider by inject()

    private val layoutManager: RecyclerView.LayoutManager by currentScope.inject()
    private val adapter: RxListAdapter<HomeCommand, ZomatoRestaurant, RxViewHolder<HomeCommand, ZomatoRestaurant>>
            by currentScope.inject {
                parametersOf(
                    output
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun createBindings(input: Observable<HomeViewModel>): ArrayList<Disposable> {
        val bindings = super.createBindings(input)

        bindings.add(
            RxView.clicks(searchButton)
                .map { HomeCommand.ButtonTap(editText.text.toString()) }
                .subscribe { output.onNext(it) }
        )

        return bindings
    }

    override fun render(viewModel: HomeViewModel) {
        recyclerView.show(viewModel.restaurantList.isNotEmpty())
        adapter.updateData(viewModel.restaurantList)
        errorTextView.show(viewModel.errorMessage.isNullOrBlank().not())
        errorTextView.text = viewModel.errorMessage
        searchButton.isEnabled = viewModel.isButtonEnable
    }
}