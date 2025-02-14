package com.example.alcoolougasolina

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alcoolougasolina.ui.theme.AlcoolOuGasolinaTheme
import com.example.alcoolougasolina.view.FormScreen
import com.example.alcoolougasolina.view.FormPosto
import com.example.alcoolougasolina.view.ListPosto
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        setContent {
            AlcoolOuGasolinaTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val postosList = remember { mutableStateListOf<Triple<String, String, String>>() }

    NavHost(navController = navController, startDestination = "formScreen") {
        composable("formScreen") { FormScreen(navController) }
        composable("formPosto") { FormPosto(navController, postosList) }
        composable("listPosto") { ListPosto(navController) }
    }
}