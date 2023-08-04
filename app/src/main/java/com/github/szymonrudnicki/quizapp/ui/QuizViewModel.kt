package com.github.szymonrudnicki.quizapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.github.szymonrudnicki.quizapp.API_KEY
import com.github.szymonrudnicki.quizapp.data.model.Question
import com.github.szymonrudnicki.quizapp.data.model.QuestionsResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@OptIn(BetaOpenAI::class)
class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.ShowSubjectInput)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private lateinit var _questions: List<Question>
    private var _currentQuestionIndex = 0

    fun onEvent(event: Event) {
        when (event) {
            is Event.SubjectEntered -> {
                _uiState.value = UIState.Loading
                getQuizQuestions(event.subject)
            }

            is Event.QuestionAnswered -> {
                with(_questions) {
                    this[_currentQuestionIndex].userAnswerId = event.answerId
                    _currentQuestionIndex++
                    if (_currentQuestionIndex < this.size) {
                        _uiState.value = UIState.ShowQuestion(this[_currentQuestionIndex])
                    } else {
                        val points = this.filter { it.correctAnswerId == it.userAnswerId }.size
                        _uiState.value = UIState.ShowSummaryScreen(points, this.size)
                    }
                }
            }

            is Event.QuizRestarted -> {
                _currentQuestionIndex = 0
                _uiState.value = UIState.ShowSubjectInput
            }
        }
    }

    private fun getQuizQuestions(subject: String) {
        viewModelScope.launch {
            val openAI = OpenAI(
                token = API_KEY,
                timeout = Timeout(socket = 90.seconds),
            )

            val prompt = """
                Act as a backend for a quiz application. 
                Generate 10 closed questions related to the topic, and four possible answers for each of them. 
                Only one answer is correct for each question. 
                Do not repeat the same question.
                Return JSON and nothing else.
                ===
                Example structure for topic - "Nobel Prize":
                {
                  "questions": [
                    {
                      "question": "Who was the first woman to win a Nobel Prize?",
                      "answers": [
                        {
                          "id": "A",
                          "answer": "Marie Curie"
                        },
                        {
                          "id": "B",
                          "answer": "Rosalind Franklin"
                        },
                        {
                          "id": "C",
                          "answer": "Jane Goodall"
                        },
                        {
                          "id": "D",
                          "answer": "Margaret Thatcher"
                        }
                      ],
                      "correctAnswerId": "A"
                    }
                  ]
                }
                ===
                Topic: $subject
            """.trimIndent()

            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.System,
                        content = prompt
                    ),
                ),
            )
            val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
            val json = completion.choices[0].message?.content
            Log.d("JSON", json ?: "error")
            val questions = Gson().fromJson(json, QuestionsResponse::class.java).questions
            _questions = questions
            _uiState.value = UIState.ShowQuestion(questions[_currentQuestionIndex])
        }
    }
}
