package com.github.szymonrudnicki.quizapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.szymonrudnicki.quizapp.data.model.Question

@Composable
fun QuestionScreen(question: Question, onQuestionAnswered: (String) -> Unit) {
    Column {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = question.question,
                style = TextStyle(fontSize = 24.sp),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        question.answers.forEach { answer ->
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                onClick = { onQuestionAnswered(answer.id) },
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = answer.answer,
                        style = TextStyle(fontSize = 18.sp),
                    )
                }
            }
        }
    }
}