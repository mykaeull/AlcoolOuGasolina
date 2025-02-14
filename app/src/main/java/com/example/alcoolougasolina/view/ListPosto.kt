package com.example.alcoolougasolina.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.*

@Composable
fun ListPosto(navController: NavController) {
    val postosList = remember { mutableStateListOf<Triple<String, Double, Double>>() }
    val database = FirebaseDatabase.getInstance()
    val postosRef = database.getReference("postos")

    // Carregar dados do Firebase
    LaunchedEffect(Unit) {
        postosRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postosList.clear()
                for (postoSnapshot in snapshot.children) {
                    val nome = postoSnapshot.child("nome").getValue(String::class.java) ?: ""
                    val valorAlcool = postoSnapshot.child("valorAlcool").getValue(Double::class.java) ?: 0.0
                    val valorGasolina = postoSnapshot.child("valorGasolina").getValue(Double::class.java) ?: 0.0

                    postosList.add(Triple(nome, valorAlcool, valorGasolina))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Erro ao carregar dados: ${error.message}")
            }
        })
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
            Text("Lista de Postos", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(postosList) { posto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Posto: ${posto.first}", style = MaterialTheme.typography.bodyLarge)
                            Text("Álcool: R$ ${posto.second}", style = MaterialTheme.typography.bodyMedium)
                            Text("Gasolina: R$ ${posto.third}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
