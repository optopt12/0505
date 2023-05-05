package com.example.chatbot.food

import com.google.gson.annotations.SerializedName


data class Posts(
    @SerializedName("_links")
    val _links: Links,
    @SerializedName("hints")
    val hints: List<Hint>,
    @SerializedName("List")
    val parsed: List<Any>,
    @SerializedName("text")
    val text: String


)

data class Links(
    val next: Next
)

data class Hint(
    val food: Food,
    val measures: List<Measure>
)

data class Next(
    val href: String,
    val title: String
)

data class Food(
    val category: String,
    val categoryLabel: String,
    val foodId: String,
    val image: String,
    val knownAs: String,
    val label: String,
    val nutrients: Nutrients
)

data class Measure(
    val label: String,
    val qualified: List<Qualified>,
    val uri: String,
    val weight: Double
)

data class Nutrients(
    val CHOCDF: Double,
    val ENERC_KCAL: Double,
    val FAT: Double,
    val FIBTG: Double,
    val PROCNT: Double,
    val VITB6A:Double
)

data class Qualified(
    val qualifiers: List<Qualifier>,
    val weight: Double
)

data class Qualifier(
    val label: String,
    val uri: String
)



data class Test(

    val ingredients: List<Ingredient>
)

data class Ingredient(
    val foodId: String,
    val measureURI: String,
    val qualifiers: List<String>,
    val quantity: Int
)


