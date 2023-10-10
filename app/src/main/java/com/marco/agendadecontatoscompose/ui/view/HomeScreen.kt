package com.marco.agendadecontatoscompose.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.marco.agendadecontatoscompose.Verificacao
import com.marco.agendadecontatoscompose.ui.components.FloatButton
import com.marco.agendadecontatoscompose.ui.components.ContatoItem
import com.marco.agendadecontatoscompose.ui.components.TopBar

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    val context = LocalContext.current
    val ler = Verificacao()
    ler.ReadContato(context = context)

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 16.dp
            ){
                TopBar()
            }
        },

        floatingActionButton = {
            FloatButton() {
                navController.navigate("salvarContato") {
                    popUpTo("listaContato")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues){
            itemsIndexed(ler.listaDeContatos){ _, item ->
                ContatoItem(navController = navController, contatoItem = item)
            }
        }
    }
}

