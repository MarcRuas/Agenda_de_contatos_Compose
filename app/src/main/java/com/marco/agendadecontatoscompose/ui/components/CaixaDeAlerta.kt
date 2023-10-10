package com.marco.agendadecontatoscompose.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun CaixaDeAlerta(confirmButton: () -> Unit, dismissButton: () -> Unit) {
    val openDialog = remember {
        mutableStateOf(true)
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = { Text(text = "Deseja Excluir?") },
            text = { Text(text = "Tem certeza que deseja excluir esse contato?") },
            confirmButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    confirmButton()
                }) {

                    Text(text = "Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    dismissButton()
                }) {

                    Text(text = "Cancelar")
                }
            }
        )
    }
}

