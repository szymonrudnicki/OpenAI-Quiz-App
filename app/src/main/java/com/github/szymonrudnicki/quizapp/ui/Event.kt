package com.github.szymonrudnicki.quizapp.ui

sealed class Event {
    data class SubjectEntered(val subject: String) : Event()
    data class QuestionAnswered(val answerId: String) : Event()
    object QuizRestarted : Event()
}