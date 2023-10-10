package com.marco.agendadecontatoscompose.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun FloatButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.onBackground
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Ir para tela de salvar.",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}