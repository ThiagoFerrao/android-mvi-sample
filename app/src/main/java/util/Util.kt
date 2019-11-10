package util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

fun View.show(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun TextView.showWithText(text: String?) {
    if (!text.isNullOrBlank()) {
        this.text = text
        show(true)
    } else show(false)
}

fun TextView.clearFocusAndHideKeyboard() {
    this.clearFocus()
    this.hideKeyboard()
}