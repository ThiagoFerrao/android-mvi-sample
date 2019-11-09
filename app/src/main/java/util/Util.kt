package util

import android.view.View

fun View.show(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.INVISIBLE
}