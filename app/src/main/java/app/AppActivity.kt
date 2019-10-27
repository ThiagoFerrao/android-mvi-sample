package app

import android.app.Activity
import android.os.Bundle
import home.factory.HomeFactory

class AppActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(HomeFactory.intent(this))
        finish()
    }
}