package org.example.app

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.math.abs

class CalculatorActivity : Activity() {

    private var displayTextView: TextView? = null

    private var currentInput: String = ""
    private var accumulator: Double? = null
    private var pendingOp: Char? = null
    private var justEvaluated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        displayTextView = findViewById(R.id.displayTextView)

        // Digits
        bindAppend("0", R.id.btn0)
        bindAppend("1", R.id.btn1)
        bindAppend("2", R.id.btn2)
        bindAppend("3", R.id.btn3)
        bindAppend("4", R.id.btn4)
        bindAppend("5", R.id.btn5)
        bindAppend("6", R.id.btn6)
        bindAppend("7", R.id.btn7)
        bindAppend("8", R.id.btn8)
        bindAppend("9", R.id.btn9)
        bindAppend(".", R.id.btnDot)

        // Operations
        bindOp('+', R.id.btnAdd)
        bindOp('-', R.id.btnSub)
        bindOp('*', R.id.btnMul)
        bindOp('/', R.id.btnDiv)

        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEquals() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClear() }

        render()
    }

    private fun bindAppend(token: String, buttonId: Int) {
        findViewById<Button>(buttonId).setOnClickListener { onAppend(token) }
    }

    private fun bindOp(op: Char, buttonId: Int) {
        findViewById<Button>(buttonId).setOnClickListener { onOperation(op) }
    }

    private fun onAppend(token: String) {
        if (justEvaluated) {
            // If user starts typing after equals, start a new number
            currentInput = ""
            justEvaluated = false
        }

        if (token == ".") {
            if (currentInput.contains(".")) return
            if (currentInput.isEmpty()) currentInput = "0"
        }

        currentInput += token
        render()
    }

    private fun onOperation(op: Char) {
        // If user taps an op without input, just change the pending op.
        val hasNumber = currentInput.isNotEmpty()
        if (!hasNumber) {
            pendingOp = op
            render()
            return
        }

        val inputValue = currentInput.toDoubleOrNull()
        if (inputValue == null) {
            currentInput = ""
            renderError("Invalid number")
            return
        }

        if (accumulator == null) {
            accumulator = inputValue
        } else if (pendingOp != null) {
            val result = applyOp(accumulator!!, inputValue, pendingOp!!)
            if (result == null) {
                renderError("Error")
                return
            }
            accumulator = result
        } else {
            // No pending op: replace accumulator with current value
            accumulator = inputValue
        }

        pendingOp = op
        currentInput = ""
        justEvaluated = false
        render()
    }

    private fun onEquals() {
        val inputValue = currentInput.toDoubleOrNull()
        val acc = accumulator
        val op = pendingOp

        if (acc == null && inputValue != null) {
            // Nothing to compute; just show the entered value.
            accumulator = inputValue
            currentInput = ""
            pendingOp = null
            justEvaluated = true
            render()
            return
        }

        if (acc == null || op == null || inputValue == null) {
            // Not enough info to evaluate
            render()
            return
        }

        val result = applyOp(acc, inputValue, op)
        if (result == null) {
            renderError("Error")
            return
        }

        accumulator = result
        currentInput = ""
        pendingOp = null
        justEvaluated = true
        render()
    }

    private fun onClear() {
        accumulator = null
        pendingOp = null
        currentInput = ""
        justEvaluated = false
        render()
    }

    private fun applyOp(a: Double, b: Double, op: Char): Double? {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> {
                if (abs(b) < 1e-12) null else a / b
            }
            else -> null
        }
    }

    private fun render() {
        val acc = accumulator
        val op = pendingOp
        val text = when {
            currentInput.isNotEmpty() -> currentInput
            acc != null && op != null -> format(acc) + " " + op
            acc != null -> format(acc)
            else -> "0"
        }
        displayTextView?.text = text
    }

    private fun renderError(message: String) {
        // Reset state so the user can continue after an error
        accumulator = null
        pendingOp = null
        currentInput = ""
        justEvaluated = false
        displayTextView?.text = message
    }

    private fun format(value: Double): String {
        val asLong = value.toLong()
        return if (value == asLong.toDouble()) asLong.toString() else value.toString()
    }
}
