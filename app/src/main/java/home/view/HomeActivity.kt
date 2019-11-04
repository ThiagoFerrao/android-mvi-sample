package home.view

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import base.RxActivity
import base.RxInteracting
import base.RxPresenting
import com.jakewharton.rxbinding2.view.RxView
import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import home.model.HomeViewModel
import home.view.list.HomeAdapter
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import network.SchedulerProvider
import org.koin.android.ext.android.inject
import org.koin.android.scope.currentScope
import thiagocruz.testingthings.R
import util.show

class HomeActivity : RxActivity<HomeCommand, HomeMutation, HomeState, HomeViewModel>() {

    override val layoutResource = R.layout.activity_main

    override val presenter: RxPresenting<HomeState, HomeViewModel> by currentScope.inject()
    override val interactor: RxInteracting<HomeCommand, HomeMutation, HomeState> by currentScope.inject()

    override val schedulerProvider: SchedulerProvider by inject()

    private val adapter = HomeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(this)
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