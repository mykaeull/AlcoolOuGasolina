package com.example.alcoolougasolina.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alcoolougasolina.R

@Composable
fun FormScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    var alcoolPrice by remember { mutableStateOf("") }
    var gasolinaPrice by remember { mutableStateOf("") }
    var isSeventyFiveChecked by remember {
        mutableStateOf(sharedPreferences.getBoolean("switchState", true))
    }
    var resultText by remember { mutableStateOf("") }

    var alcoholBestOptionMessage = stringResource(id = R.string.alcohol_best_option_message)
    var gasolineBestOptionMessage = stringResource(id = R.string.gasoline_best_option_message)
    var calculateErrorMessage = stringResource(id = R.string.calculate_error_message)

    fun calcularMelhorCombustivel() {
        val alcool = alcoolPrice.toFloatOrNull()
        val gasolina = gasolinaPrice.toFloatOrNull()
        val fator = if (isSeventyFiveChecked) 0.75 else 0.70

        if (alcool != null && gasolina != null && gasolina > 0) {
            resultText = if (alcool / gasolina <= fator) {
                alcoholBestOptionMessage
            } else {
                gasolineBestOptionMessage
            }
        } else {
            resultText = calculateErrorMessage
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
                label = { Text(stringResource(id = R.string.alcohol_price)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de Preço da Gasolina
            OutlinedTextField(
                value = gasolinaPrice,
                onValueChange = { gasolinaPrice = it },
                label = { Text(stringResource(id = R.string.gasoline_price)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

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
                Text(stringResource(id = R.string.calculate))
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Resultado
            Text(text = resultText, style = MaterialTheme.typography.bodyLarge)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { navController.navigate("formPosto") },
            shape = RoundedCornerShape(6.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Adicionar Posto")
        }
    }
}
