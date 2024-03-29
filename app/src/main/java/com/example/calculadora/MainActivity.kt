package com.example.calculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var tvNum1: TextView
    private lateinit var tvNum2: TextView
    private var currentInput = StringBuilder()
    private var currentOperator = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvNum1 = findViewById(R.id.tv_num1)
        tvNum2 = findViewById(R.id.tv_num2)
    }

    fun clicNumero(view: View) {
        val button = view as Button
        val number = button.text.toString()
        currentInput.append(number)
        updateDisplay()
    }

    fun clicOperacion(view: View) {
        val button = view as Button
        currentOperator = button.text.toString()
        currentInput.append(currentOperator)
        updateDisplay()
    }

    fun calcularResultado(view: View) {
        val expression = currentInput.toString()
        if (expression.isNotEmpty()) {
            try {
                val result = evaluarExpresion(expression)
                tvNum1.text = result.toString()
                tvNum2.text = ""
                currentInput.clear()
                currentInput.append(result)
            } catch (e: ArithmeticException) {
                tvNum1.text = "Error"
                tvNum2.text = ""
                currentInput.clear()
            }
        }
    }

    private fun evaluarExpresion(expression: String): Double {
        val parts = expression.split(currentOperator)
        val num1 = parts[0].toDouble()
        val num2 = parts[1].toDouble()

        return when (currentOperator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> num1 / num2
            else -> throw IllegalArgumentException("Operador no válido")
        }
    }

    fun borrar(view: View) {
        currentInput.clear()
        tvNum1.text = "0.0"
        tvNum2.text = ""
        currentOperator = ""
    }

    private fun updateDisplay() {
        val inputText = currentInput.toString()
        if (currentOperator.isEmpty()) {
            tvNum1.text = inputText
            tvNum2.text = ""
        } else {
            val operatorIndex = inputText.indexOf(currentOperator)
            tvNum1.text = inputText.substring(0, operatorIndex)
            tvNum2.text = inputText.substring(operatorIndex + 1)
        }
    }
}
