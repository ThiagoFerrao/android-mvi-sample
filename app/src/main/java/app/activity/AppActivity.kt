package app.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import home.view.HomeActivity

class AppActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}