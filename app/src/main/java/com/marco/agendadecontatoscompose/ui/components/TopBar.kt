package com.marco.agendadecontatoscompose.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.marco.agendadecontatoscompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {

    val isDark = isSystemInDarkTheme()

    TopAppBar(
        title = {
                Text(text = stringResource(id = R.string.app_name))
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}