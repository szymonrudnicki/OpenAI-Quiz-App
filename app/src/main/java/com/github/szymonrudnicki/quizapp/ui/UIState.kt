package com.github.szymonrudnicki.quizapp.ui

import com.github.szymonrudnicki.quizapp.data.model.Question

sealed class UIState {
    object Loading : UIState()
    object ShowSubjectInput : UIState()
    data class ShowQuestion(val question: Question) : UIState()
    data class ShowSummaryScreen(val points: Int, val maxPoints: Int) : UIState()
}