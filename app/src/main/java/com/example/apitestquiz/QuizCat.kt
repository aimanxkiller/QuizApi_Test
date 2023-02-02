package com.example.apitestquiz

import com.google.gson.annotations.SerializedName

data class QuizCat(
    @SerializedName("Arts & Literature") val ArtsLiterature: List<String>,
    @SerializedName("Film & TV")val FilmTV: List<String>,
    @SerializedName("Food & Drink") val FoodDrink: List<String>,
    @SerializedName("General Knowledge") val GeneralKnowledge: List<String>,
    val Geography: List<String>,
    val History: List<String>,
    val Music: List<String>,
    val Science: List<String>,
    @SerializedName("Society & Culture") val SocietyCulture: List<String>,
    @SerializedName("Sport & Leisure") val SportLeisure: List<String>
)

