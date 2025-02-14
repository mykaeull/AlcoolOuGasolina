package com.example.alcoolougasolina.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase

@Composable
fun FormPosto(navController: NavController, postosList: MutableList<Triple<String, String, String>>) {
    var postoNome by remember { mutableStateOf("") }
    var alcoolPrice by remember { mutableStateOf("") }
    var gasolinaPrice by remember { mutableStateOf("") }

    val isButtonEnabled = postoNome.isNotBlank() && alcoolPrice.isNotBlank() && gasolinaPrice.isNotBlank()

    fun salvarPosto(nome: String, valorAlcool: Double, valorGasolina: Double) {
        val database = FirebaseDatabase.getInstance()
        val postosRef = database.getReference("postos")

        val novoPostoId = postosRef.push().key // Gera um ID único
        val posto = mapOf(
            "nome" to nome,
            "valorAlcool" to valorAlcool,
            "valorGasolina" to valorGasolina
        )

        novoPostoId?.let {
            postosRef.child(it).setValue(posto)
                .addOnSuccessListener { println("Posto salvo com sucesso!") }
                .addOnFailureListener { error -> println("Erro ao salvar: ${error.message}") }
        }
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
            // Campo Nome do Posto
            OutlinedTextField(
                value = postoNome,
                onValueChange = { postoNome = it },
                label = { Text("Nome do Posto") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo Valor do Álcool
            OutlinedTextField(
                value = alcoolPrice,
                onValueChange = { alcoolPrice = it },
                label = { Text("Valor do Álcool (R$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo Valor da Gasolina
            OutlinedTextField(
                value = gasolinaPrice,
                onValueChange = { gasolinaPrice = it },
                label = { Text("Valor da Gasolina (R$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Botão Adicionar Posto
            Button(
                onClick = {
                    val alcoolDouble = alcoolPrice.toDoubleOrNull() ?: 0.0
                    val gasolinaDouble = gasolinaPrice.toDoubleOrNull() ?: 0.0

                    postosList.add(Triple(postoNome, alcoolPrice, gasolinaPrice))
                    salvarPosto(postoNome, alcoolDouble, gasolinaDouble)
                    navController.navigate("listPosto")
                },
                enabled = isButtonEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar Posto")
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Link para Ver Lista de Postos
            Text(
                text = "Ver Lista de Postos",
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { navController.navigate("listPosto") }
            )
        }
    }
}
