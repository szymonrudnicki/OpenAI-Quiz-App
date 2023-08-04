package com.github.szymonrudnicki.quizapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Generating quiz...")
        Spacer(modifier = Modifier.height(32.dp))
        CircularProgressIndicator()
    }
}