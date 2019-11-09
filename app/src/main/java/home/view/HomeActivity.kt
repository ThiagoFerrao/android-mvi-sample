package home.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import home.di.HomeActivityType
import home.di.HomeAdapterType
import home.di.HomeInteractorType
import home.di.HomePresenterType
import home.model.HomeCommand
import home.model.HomeViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import network.SchedulerProvider
import org.koin.android.ext.android.inject
import org.koin.android.scope.currentScope
import org.koin.core.parameter.parametersOf
import thiagocruz.testingthings.R
import util.show

class HomeActivity : HomeActivityType() {
    override val layoutResource = R.layout.activity_main

    override val presenter: HomePresenterType by currentScope.inject()
    override val interactor: HomeInteractorType by currentScope.inject()

    override val input: Observable<HomeViewModel> by currentScope.inject { parametersOf(output) }
    override val schedulerProvider: SchedulerProvider by inject()

    private val layoutManager: RecyclerView.LayoutManager by currentScope.inject()
    private val adapter: HomeAdapterType by currentScope.inject { parametersOf(output) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun createBindings(input: Observable<HomeViewModel>): ArrayList<Disposable> {
        val bindings = super.createBindings(input)

        bindings.addAll(listOf(
            RxView.clicks(searchButton)
                .map { HomeCommand.ButtonTap(editText.text.toString()) }
                .subscribe { output.onNext(it) },

            RxTextView.editorActions(editText)
                .filter { it == EditorInfo.IME_ACTION_DONE }
                .map { HomeCommand.ButtonTap(editText.text.toString()) }
                .subscribe { output.onNext(it) }
        ))

        return bindings
    }

    override fun render(viewModel: HomeViewModel) {
        recyclerView.show(viewModel.restaurantList.isNotEmpty())
        adapter.updateData(viewModel.restaurantList)

        errorTextView.show(viewModel.errorMessage.isNullOrBlank().not())
        errorTextView.text = viewModel.errorMessage

        searchButton.isEnabled = viewModel.isButtonEnable

        val enableColor = ColorStateList.valueOf(getColor(R.color.colorAccent))
        val disableColor = ColorStateList.valueOf(getColor(R.color.colorDisable))
        searchButton.backgroundTintList = if (viewModel.isButtonEnable) enableColor else disableColor
    }
}