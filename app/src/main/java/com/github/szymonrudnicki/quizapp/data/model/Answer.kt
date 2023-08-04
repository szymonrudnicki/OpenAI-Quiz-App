package com.github.szymonrudnicki.quizapp.data.model

import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("id") var id: String,
    @SerializedName("answer") var answer: String
)