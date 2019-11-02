package home.view

import base.RxActivity
import base.RxInteracting
import base.RxPresenting
import com.jakewharton.rxbinding2.view.RxView
import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import home.model.HomeViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import network.MainSchedulerFactory
import org.koin.android.ext.android.inject
import org.koin.android.scope.currentScope
import thiagocruz.testingthings.R

class HomeActivity : RxActivity<HomeCommand, HomeMutation, HomeState, HomeViewModel>() {

    override val layoutResource = R.layout.activity_main

    override val presenter: RxPresenting<HomeState, HomeViewModel> by currentScope.inject()
    override val interactor: RxInteracting<HomeCommand, HomeMutation, HomeState> by currentScope.inject()

    override val schedulerFactory: MainSchedulerFactory by inject()

    override fun createBindings(input: Observable<HomeViewModel>): ArrayList<Disposable> {
        val bindings = super.createBindings(input)

        bindings.add(
            RxView.clicks(mainButton)
                .map { HomeCommand.ButtonTap(editText.text.toString()) }
                .subscribe { output.onNext(it) }
        )

        return bindings
    }

    override fun render(viewModel: HomeViewModel) {
        quoteTextView.text = viewModel.restaurantName
        mainButton.text = viewModel.buttonText
        mainButton.isEnabled = viewModel.isButtonEnable
    }
}