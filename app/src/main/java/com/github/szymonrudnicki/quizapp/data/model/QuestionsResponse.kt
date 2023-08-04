package com.github.szymonrudnicki.quizapp.data.model

import com.google.gson.annotations.SerializedName

data class QuestionsResponse(
    @SerializedName("questions") var questions: List<Question>
)