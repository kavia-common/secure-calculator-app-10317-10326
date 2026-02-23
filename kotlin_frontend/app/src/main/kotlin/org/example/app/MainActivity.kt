package org.example.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 * Legacy entry point from the init template.
 * The launcher is now LoginActivity, but we keep this Activity to avoid breaking
 * any existing references while the project evolves.
 */
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Immediately forward to login flow.
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
