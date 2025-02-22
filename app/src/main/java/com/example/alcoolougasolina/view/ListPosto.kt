package com.example.alcoolougasolina.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alcoolougasolina.R
import com.google.firebase.database.*

@Composable
fun ListPosto(navController: NavController) {
    val postosList = remember { mutableStateListOf<Pair<String, Triple<String, Double, Double>>>() }
    val database = FirebaseDatabase.getInstance()
    val postosRef = database.getReference("postos")
    val context = LocalContext.current

    // Coordenadas fixas para Fortaleza - CE
    val latitude = -3.7327
    val longitude = -38.5270

    // Carregar dados do Firebase
    LaunchedEffect(Unit) {
        postosRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postosList.clear()
                for (postoSnapshot in snapshot.children) {
                    val id = postoSnapshot.key ?: ""
                    val nome = postoSnapshot.child("nome").getValue(String::class.java) ?: ""
                    val valorAlcool = postoSnapshot.child("valorAlcool").getValue(Double::class.java) ?: 0.0
                    val valorGasolina = postoSnapshot.child("valorGasolina").getValue(Double::class.java) ?: 0.0

                    postosList.add(id to Triple(nome, valorAlcool, valorGasolina))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Erro ao carregar dados: ${error.message}")
            }
        })
    }

    fun excluirPosto(id: String) {
        postosRef.child(id).removeValue()
    }

    fun abrirGoogleMaps() {
        val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude(Posto+de+Combustível)")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        context.startActivity(intent)
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
            Text(stringResource(id = R.string.station_list), style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(postosList) { (id, posto) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column {
                                    Text("${stringResource(id = R.string.station)}: ${posto.first}", style = MaterialTheme.typography.bodyLarge)
                                    Text("${stringResource(id = R.string.alcohol)} ${posto.second}", style = MaterialTheme.typography.bodyMedium)
                                    Text("${stringResource(id = R.string.gasoline)} ${posto.third}", style = MaterialTheme.typography.bodyMedium)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(
                                        onClick = { abrirGoogleMaps() },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                                    ) {
                                        Text(stringResource(id = R.string.location))
                                    }
                                }
                                IconButton(
                                    onClick = { excluirPosto(id) },
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Excluir Posto",
                                        tint = Color.Red,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
