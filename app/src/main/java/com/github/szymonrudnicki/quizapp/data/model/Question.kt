package com.github.szymonrudnicki.quizapp.data.model

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("question") var question: String,
    @SerializedName("answers") var answers: ArrayList<Answer>,
    @SerializedName("correctAnswerId") var correctAnswerId: String,
    var userAnswerId: String? = null
)
