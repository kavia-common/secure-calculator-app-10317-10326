package org.example.app

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.math.BigDecimal
import java.math.MathContext

class CalculatorActivity : Activity() {

    companion object {
        const val EXTRA_USERNAME: String = "extra_username"
        private val MATH_CONTEXT: MathContext = MathContext.DECIMAL64
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val numberAInput = findViewById<EditText>(R.id.numberAInput)
        val numberBInput = findViewById<EditText>(R.id.numberBInput)

        val addButton = findViewById<Button>(R.id.addButton)
        val subtractButton = findViewById<Button>(R.id.subtractButton)
        val multiplyButton = findViewById<Button>(R.id.multiplyButton)
        val divideButton = findViewById<Button>(R.id.divideButton)

        val resultText = findViewById<TextView>(R.id.resultText)
        val errorText = findViewById<TextView>(R.id.calcErrorText)

        val username = intent.getStringExtra(EXTRA_USERNAME).orEmpty()
        if (username.isNotBlank()) {
            // Optional nicety: personalize title if desired.
            findViewById<TextView>(R.id.calcTitle).text = getString(R.string.calculator_title_user, username)
        }

        fun showError(message: String) {
            errorText.text = message
            errorText.visibility = View.VISIBLE
        }

        fun clearError() {
            errorText.visibility = View.GONE
        }

        fun parseInputs(): Pair<BigDecimal, BigDecimal>? {
            val aRaw = numberAInput.text?.toString()?.trim().orEmpty()
            val bRaw = numberBInput.text?.toString()?.trim().orEmpty()

            if (aRaw.isEmpty() || bRaw.isEmpty()) {
                showError(getString(R.string.calc_error_required))
                return null
            }

            val a = aRaw.toBigDecimalOrNull()
            val b = bRaw.toBigDecimalOrNull()

            if (a == null || b == null) {
                showError(getString(R.string.calc_error_invalid_number))
                return null
            }

            return Pair(a, b)
        }

        fun setResult(value: BigDecimal) {
            // Strip trailing zeros for cleaner output.
            val display = value.stripTrailingZeros().toPlainString()
            resultText.text = display
        }

        addButton.setOnClickListener {
            clearError()
            val inputs = parseInputs() ?: return@setOnClickListener
            setResult(inputs.first.add(inputs.second, MATH_CONTEXT))
        }

        subtractButton.setOnClickListener {
            clearError()
            val inputs = parseInputs() ?: return@setOnClickListener
            setResult(inputs.first.subtract(inputs.second, MATH_CONTEXT))
        }

        multiplyButton.setOnClickListener {
            clearError()
            val inputs = parseInputs() ?: return@setOnClickListener
            setResult(inputs.first.multiply(inputs.second, MATH_CONTEXT))
        }

        divideButton.setOnClickListener {
            clearError()
            val inputs = parseInputs() ?: return@setOnClickListener

            if (inputs.second.compareTo(BigDecimal.ZERO) == 0) {
                showError(getString(R.string.calc_error_divide_by_zero))
                return@setOnClickListener
            }

            setResult(inputs.first.divide(inputs.second, MATH_CONTEXT))
        }
    }
}
