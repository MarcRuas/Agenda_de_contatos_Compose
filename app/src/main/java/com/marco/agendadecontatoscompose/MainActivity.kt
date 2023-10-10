package com.marco.agendadecontatoscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marco.agendadecontatoscompose.ui.theme.AgendaDeContatosComposeTheme
import com.marco.agendadecontatoscompose.ui.view.HomeScreen
import com.marco.agendadecontatoscompose.ui.view.SaveScreen
import com.marco.agendadecontatoscompose.ui.view.UpdateScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgendaDeContatosComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "listaContato") {
                    composable("listaContato") {
                        HomeScreen(navController = navController)
                    }

                    composable("salvarContato") {
                        SaveScreen(navController = navController)
                    }

                    composable(
                        "atualizarContato/{uid}",
                        arguments = listOf(navArgument("uid") {})
                    ) {
                        UpdateScreen(
                            navController = navController,
                            it.arguments?.getString("uid").toString()
                        )
                    }
                }
            }
        }
    }
}

