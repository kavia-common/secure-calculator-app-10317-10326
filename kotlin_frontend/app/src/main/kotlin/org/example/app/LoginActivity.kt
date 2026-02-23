package org.example.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val errorText = findViewById<TextView>(R.id.errorText)

        loginButton.setOnClickListener {
            errorText.visibility = View.GONE

            val username = usernameInput.text?.toString()?.trim().orEmpty()
            val password = passwordInput.text?.toString()?.trim().orEmpty()

            // Simple local "authentication": require non-empty fields.
            if (username.isEmpty() || password.isEmpty()) {
                errorText.text = getString(R.string.login_error_required)
                errorText.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val intent = Intent(this, CalculatorActivity::class.java)
            intent.putExtra(CalculatorActivity.EXTRA_USERNAME, username)
            startActivity(intent)
        }
    }
}
