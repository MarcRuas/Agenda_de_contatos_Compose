package com.marco.agendadecontatoscompose.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.marco.agendadecontatoscompose.Verificacao
import com.marco.agendadecontatoscompose.ui.components.But
import com.marco.agendadecontatoscompose.ui.components.OutField
import com.marco.agendadecontatoscompose.ui.components.TopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveScreen(
    navController: NavController
) {
    var nome by rememberSaveable {
        mutableStateOf("")
    }
    var sobrenome by rememberSaveable {
        mutableStateOf("")
    }
    var idade by rememberSaveable {
        mutableStateOf("")
    }
    var celular by rememberSaveable {
        mutableStateOf("")
    }

    val veri = Verificacao()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var sucess: Boolean

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 16.dp
            ){
                TopBar()
            }
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutField(
                value = nome,
                onValueChange = { nome = it },
                label = "Nome",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                )
            )

            OutField(
                value = sobrenome,
                onValueChange = { sobrenome = it },
                label = "Sobrenome",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                )
            )

            OutField(
                value = idade,
                onValueChange = { idade = it },
                label = "Idade",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                )

            OutField(
                value = celular,
                onValueChange = { celular = it },
                label = "Celular",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            But(text = "Salvar") {
                scope.launch(Dispatchers.IO) {
                    sucess = veri.criarContato(
                        nome = nome,
                        sobreNome = sobrenome,
                        idade = idade,
                        celular = celular,
                        context = context
                    )
                    withContext(Dispatchers.Main) {
                        if (sucess) {
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

            }
        }
    }
}