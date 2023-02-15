package com.example.apitestquiz.model

import com.google.gson.annotations.SerializedName

private val category:MutableList<String> = mutableListOf()
private val category2:MutableList<String> = mutableListOf()
private var count1 = 0
private var count2 = 0

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
){
    fun getDetails(): MutableList<String> {
        if(count1==0){
            category.add(SportLeisure.toString())
            category.add(SocietyCulture.toString())
            category.add(Science.toString())
            category.add(Music.toString())
            category.add(History.toString())
            category.add(Geography.toString())
            category.add(GeneralKnowledge.toString())
            category.add(ArtsLiterature.toString())
            category.add(FoodDrink.toString())
            category.add(FilmTV.toString())
            count1++

            category.forEachIndexed { index, _ ->
                category[index]= category[index].replace("[","").replace("]","")
                if(category[index].contains(",")){
                    val a = category[index].split(",")
                    var short = a.maxBy { it.length }
                    short = short.replace("_"," ")
                    category[index] = short
                }
            }
        }
        return category
    }

    fun getTitle():MutableList<String>{
        if (count2==0){
            category2.add(SportLeisure.toString())
            category2.add(SocietyCulture.toString())
            category2.add(Science.toString())
            category2.add(Music.toString())
            category2.add(History.toString())
            category2.add(Geography.toString())
            category2.add(GeneralKnowledge.toString())
            category2.add(ArtsLiterature.toString())
            category2.add(FoodDrink.toString())
            category2.add(FilmTV.toString())
            count2++

            category2.forEachIndexed { index, _ ->
                category2[index]= category2[index].replace("[","").replace("]","")
            }
        }
        return category2
    }

}
