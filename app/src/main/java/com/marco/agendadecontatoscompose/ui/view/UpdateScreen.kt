package com.marco.agendadecontatoscompose.ui.view

import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.marco.agendadecontatoscompose.AppDatabase
import com.marco.agendadecontatoscompose.Verificacao
import com.marco.agendadecontatoscompose.dao.ContatoDao
import com.marco.agendadecontatoscompose.ui.components.But
import com.marco.agendadecontatoscompose.ui.components.OutField
import com.marco.agendadecontatoscompose.ui.components.TopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private lateinit var contatoDao: ContatoDao

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(
    navController: NavController,
    contatoId: String
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val veri = Verificacao()

    var message: Boolean

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

    LaunchedEffect(contatoId) {
        val contato = withContext(Dispatchers.IO) {
            contatoDao = AppDatabase.getInstance(context).contatoDao()
            contatoDao.getContatoPeloId(contatoId)
        }

        if (contato != null) {
            nome = contato.nome
            sobrenome = contato.sobrenome
            idade = contato.idade
            celular = contato.celular
        }
    }

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 16.dp
            ) {
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutField(
                value = sobrenome,
                onValueChange = { sobrenome = it },
                label = "Sobrenome",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutField(
                value = idade,
                onValueChange = { idade = it },
                label = "Idade",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutField(
                value = celular,
                onValueChange = { celular = it },
                label = "Celular",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            But(text = "Atualizar") {
                 scope.launch(
                    Dispatchers.IO
                ) {
                     message = veri.atualizarContato(
                        uid = contatoId,
                        nome = nome,
                        sobreNome = sobrenome,
                        idade = idade,
                        celular = celular,
                        context = context
                    )

                     withContext(Dispatchers.Main){
                         if (message){
                             navController.popBackStack()
                         }
                         else{
                             Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT)
                                 .show()
                         }
                     }
                }
            }
        }
    }
}