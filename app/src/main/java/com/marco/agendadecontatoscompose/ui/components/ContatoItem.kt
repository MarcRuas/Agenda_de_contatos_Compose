package com.marco.agendadecontatoscompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.marco.agendadecontatoscompose.AppDatabase
import com.marco.agendadecontatoscompose.dao.ContatoDao
import com.marco.agendadecontatoscompose.model.Contato
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var contatoDao: ContatoDao

@Composable
fun ContatoItem(
    navController: NavController,
    contatoItem: Contato
) {

    val nome = contatoItem.nome
    val sobrenome = contatoItem.sobrenome
    val idade = contatoItem.idade
    val celular = contatoItem.celular
    val uid = contatoItem.uid

    var ativar by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    if (ativar) {
        CaixaDeAlerta(
            confirmButton = {
                scope.launch(Dispatchers.IO){
                    contatoDao = AppDatabase.getInstance(context).contatoDao()
                    contatoDao.deletar(uid)
                }
                scope.launch(Dispatchers.Main){
                    navController.navigate("listaContato"){
                        launchSingleTop = true
                    }
                }
                ativar = false
            },
            dismissButton = {
                ativar = false
            },

            )
    }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 20.dp, end = 10.dp, bottom = 10.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(all = 20.dp)
        ) {
            val (txtNome, txtIdade, txtCelular, btAtualizar, btDeletar) = createRefs()

            Text(
                text = "Contato: $nome $sobrenome",
                fontSize = 18.sp,
                modifier = Modifier.constrainAs(txtNome) {
                    top.linkTo(parent.top, margin = 5.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
            )

            Text(
                text = "Idade: $idade",
                fontSize = 18.sp,
                modifier = Modifier.constrainAs(txtIdade) {
                    top.linkTo(txtNome.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
            )

            Text(
                text = "Celular: $celular",
                fontSize = 18.sp,
                modifier = Modifier.constrainAs(txtCelular) {
                    top.linkTo(txtIdade.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
            )

            IconButton(
                onClick = {
                    navController.navigate("atualizarContato/${uid}")
                },
                modifier = Modifier.constrainAs(btAtualizar) {
                    top.linkTo(txtIdade.bottom)
                    start.linkTo(txtCelular.end, margin = 5.dp)
                }
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Atualizar o contato!")
            }

            IconButton(
                onClick = {
                    ativar = true
                },
                modifier = Modifier.constrainAs(btDeletar) {
                    top.linkTo(txtIdade.bottom)
                    start.linkTo(btAtualizar.end, margin = 5.dp)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Deletar este contato!"
                )
            }
        }
    }
}