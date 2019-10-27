package home.view

import base.RxActivity
import base.RxFactory
import com.jakewharton.rxbinding2.view.RxView
import home.factory.HomeFactory
import home.model.HomeCommand
import home.model.HomeMutation
import home.model.HomeState
import home.model.HomeViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import thiagocruz.testingthings.R

class HomeActivity : RxActivity<HomeCommand, HomeMutation, HomeState, HomeViewModel>() {

    override val layoutResource = R.layout.activity_main

    override val factory: RxFactory<HomeCommand, HomeMutation, HomeState, HomeViewModel> = HomeFactory

    override fun createBindings(input: Observable<HomeViewModel>): ArrayList<Disposable> {
        val bindings = super.createBindings(input)

        bindings.add(
            RxView.clicks(main_button)
                .map { HomeCommand.BUTTON_TAP }
                .subscribe { output.onNext(it) }
        )

        return bindings
    }

    override fun render(viewModel: HomeViewModel) {
        main_button.text = viewModel.buttonTitle
    }
}