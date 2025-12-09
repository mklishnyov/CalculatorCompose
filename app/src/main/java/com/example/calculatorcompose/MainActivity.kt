package com.example.calculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculatorcompose.ui.theme.CalculatorComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp(modifier: Modifier = Modifier) {
    val firstValue = remember { mutableStateOf("") }
    val secondValue = remember { mutableStateOf("") }
    val result = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = firstValue.value,
            onValueChange = { firstValue.value = it },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            label = { Text("Enter first number") }
        )

        OutlinedTextField(
            value = secondValue.value,
            onValueChange = { secondValue.value = it },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            label = { Text("Enter second number") }
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val num1 = firstValue.value.toDoubleOrNull() ?: 0.0
            val num2 = secondValue.value.toDoubleOrNull() ?: 0.0

            Button(
                onClick = { result.value = (num1 + num2).toString() }
            ) {
                Text("+")
            }
            Button(
                onClick = { result.value = (num1 - num2).toString() }
            ) {
                Text("-")
            }
            Button(
                onClick = { result.value = (num1 * num2).toString() }
            ) {
                Text("*")
            }
            Button(
                onClick = {
                    if (num2 == 0.0) {
                        result.value = "Error: Division by zero"
                    } else {
                        result.value = (num1 / num2).toString()
                    }
                }
            ) {
                Text("/")
            }
        }

        Text(
            text = "Result: ${result.value}",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}