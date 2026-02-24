package org.example.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text?.toString()?.trim().orEmpty()
            val password = passwordEditText.text?.toString()?.trim().orEmpty()

            // Local validation only (per container interfaces: "Mobile UI, local validation")
            val isValid = username.isNotEmpty() && password.isNotEmpty()

            if (!isValid) {
                // Keep UI minimal: use EditText errors (no extra dependencies)
                if (username.isEmpty()) usernameEditText.error = "Username is required"
                if (password.isEmpty()) passwordEditText.error = "Password is required"
                return@setOnClickListener
            }

            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }
    }
}
