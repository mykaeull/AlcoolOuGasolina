package com.example.alcoolougasolina

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.alcoolougasolina.ui.theme.AlcoolOuGasolinaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlcoolOuGasolinaTheme {
                FormScreen()
            }
        }
    }
}

@Composable
fun FormScreen() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    var alcoolPrice by remember { mutableStateOf("") }
    var gasolinaPrice by remember { mutableStateOf("") }
    var postoNome by remember { mutableStateOf("") }
    var isSeventyFiveChecked by remember {
        mutableStateOf(sharedPreferences.getBoolean("switchState", true))
    }
    var resultText by remember { mutableStateOf("") }

    fun calcularMelhorCombustivel() {
        val alcool = alcoolPrice.toFloatOrNull()
        val gasolina = gasolinaPrice.toFloatOrNull()
        val fator = if (isSeventyFiveChecked) 0.75 else 0.70

        if (alcool != null && gasolina != null && gasolina > 0) {
            resultText = if (alcool / gasolina <= fator) {
                "Álcool é a melhor opção"
            } else {
                "Gasolina é a melhor opção"
            }
        } else {
            resultText = "Por favor, insira valores válidos."
        }
    }

    fun saveSwitchState(state: Boolean) {
        sharedPreferences.edit().putBoolean("switchState", state).apply()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campo de Preço do Álcool
            OutlinedTextField(
                value = alcoolPrice,
                onValueChange = { alcoolPrice = it },
                label = { Text("Preço do Álcool (R$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de Preço da Gasolina
            OutlinedTextField(
                value = gasolinaPrice,
                onValueChange = { gasolinaPrice = it },
                label = { Text("Preço da Gasolina (R$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de Nome do Posto (opcional)
            OutlinedTextField(
                value = postoNome,
                onValueChange = { postoNome = it },
                label = { Text("Nome do Posto (opcional)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Switch 75%
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("75%")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isSeventyFiveChecked,
                    onCheckedChange = {
                        isSeventyFiveChecked = it
                        saveSwitchState(it)
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Botão de Calcular
            Button(
                onClick = { calcularMelhorCombustivel() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Resultado
            Text(text = resultText, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
