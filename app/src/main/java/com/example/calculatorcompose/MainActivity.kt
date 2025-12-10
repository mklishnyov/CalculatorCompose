package com.example.calculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.example.calculatorcompose.ui.theme.CalculatorComposeTheme
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorComposeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CalculatorApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorApp(modifier: Modifier = Modifier) {
    val firstValue = remember { mutableStateOf("") }
    val secondValue = remember { mutableStateOf("") }
    val result = remember { mutableStateOf("") }

    fun parseInputs(): Pair<Double?, Double?> {
        val n1 = firstValue.value.toDoubleOrNull()
        val n2 = secondValue.value.toDoubleOrNull()
        return Pair(n1, n2)
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = firstValue.value,
            onValueChange = { firstValue.value = it },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            label = { Text("Enter first number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = secondValue.value,
            onValueChange = { secondValue.value = it },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            label = { Text("Enter second number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Result card
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Box(modifier = Modifier.fillMaxWidth().padding(12.dp), contentAlignment = Alignment.CenterStart) {
                Text(text = if (result.value.isEmpty()) "Result: -" else "Result: ${result.value}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // First row: + - × ÷
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val (n1, n2) = parseInputs()

            Button(modifier = Modifier.weight(1f), onClick = {
                if (n1 == null || n2 == null) {
                    result.value = "Error: Invalid input"
                } else {
                    result.value = (n1 + n2).toString()
                }
            }) { Text("+") }

            Button(modifier = Modifier.weight(1f), onClick = {
                if (n1 == null || n2 == null) {
                    result.value = "Error: Invalid input"
                } else {
                    result.value = (n1 - n2).toString()
                }
            }) { Text("-") }

            Button(modifier = Modifier.weight(1f), onClick = {
                if (n1 == null || n2 == null) {
                    result.value = "Error: Invalid input"
                } else {
                    result.value = (n1 * n2).toString()
                }
            }) { Text("×") }

            Button(modifier = Modifier.weight(1f), onClick = {
                if (n1 == null || n2 == null) {
                    result.value = "Error: Invalid input"
                } else if (n2 == 0.0) {
                    result.value = "Error: Division by zero"
                } else {
                    result.value = (n1 / n2).toString()
                }
            }) { Text("÷") }
        }

        // Second row: ^ % C
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val (n1b, n2b) = parseInputs()

            Button(modifier = Modifier.weight(1f), onClick = {
                if (n1b == null || n2b == null) {
                    result.value = "Error: Invalid input"
                } else {
                    // power: n1 ^ n2
                    result.value = try {
                        n1b.pow(n2b).toString()
                    } catch (_: Exception) {
                        "Error"
                    }
                }
            }) { Text("^") }

            Button(modifier = Modifier.weight(1f), onClick = {
                if (n1b == null || n2b == null) {
                    result.value = "Error: Invalid input"
                } else if (n2b == 0.0) {
                    result.value = "Error: Modulo by zero"
                } else {
                    result.value = (n1b % n2b).toString()
                }
            }) { Text("%") }

            Button(modifier = Modifier.weight(1f), onClick = {
                firstValue.value = ""
                secondValue.value = ""
                result.value = ""
            }) { Text("C") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorComposeTheme {
        CalculatorApp()
    }
}