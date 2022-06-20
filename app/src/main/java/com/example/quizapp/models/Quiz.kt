package com.example.quizapp.models

import android.graphics.drawable.Drawable

data class Quiz(
    val question: String,
    val drawableImageName: Drawable?,  // this is the name of the image attached to the corresponding question(hard coded)
    val option_A: String,
    val option_B: String,
    val option_C: String,
    val option_D: String,
    val corrAnswer: Int
)