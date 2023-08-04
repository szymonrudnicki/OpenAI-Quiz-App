package com.github.szymonrudnicki.quizapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SummaryScreen(points: Int, maxPoints: Int, onQuizRestart: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$points / $maxPoints", style = TextStyle(fontSize = 24.sp))
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onQuizRestart) {
            Text(text = "Restart")
        }
    }
}