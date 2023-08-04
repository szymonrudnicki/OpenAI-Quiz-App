package com.github.szymonrudnicki.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.github.szymonrudnicki.quizapp.ui.Event
import com.github.szymonrudnicki.quizapp.ui.QuizViewModel
import com.github.szymonrudnicki.quizapp.ui.UIState
import com.github.szymonrudnicki.quizapp.ui.screen.InputSubjectScreen
import com.github.szymonrudnicki.quizapp.ui.screen.LoadingScreen
import com.github.szymonrudnicki.quizapp.ui.screen.QuestionScreen
import com.github.szymonrudnicki.quizapp.ui.screen.SummaryScreen
import com.github.szymonrudnicki.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: QuizViewModel = viewModel()
            val uiState = viewModel.uiState.collectAsState().value

            QuizAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(16.dp)) {
                        when (uiState) {
                            is UIState.ShowSubjectInput -> {
                                InputSubjectScreen(
                                    onSubjectEntered = { subject -> viewModel.onEvent(Event.SubjectEntered(subject)) },
                                )
                            }
                            is UIState.ShowQuestion -> {
                                QuestionScreen(
                                    uiState.question,
                                    onQuestionAnswered = { answerId -> viewModel.onEvent(Event.QuestionAnswered(answerId)) }
                                )
                            }
                            is UIState.ShowSummaryScreen -> {
                                SummaryScreen(
                                    points = uiState.points,
                                    maxPoints = uiState.maxPoints,
                                    onQuizRestart = { viewModel.onEvent(Event.QuizRestarted) }
                                )
                            }
                            is UIState.Loading -> {
                                LoadingScreen()
                            }
                        }
                    }

                }
            }
        }
    }
}