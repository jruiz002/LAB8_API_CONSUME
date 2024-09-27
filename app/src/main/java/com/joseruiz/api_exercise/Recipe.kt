package com.joseruiz.api_exercise

data class RecipesResponse(
    val meals: List<MealRecipe>?
)

data class MealRecipe(
    val idMeal: String,
    val strMeal: String,
    val strDrinkAlternate: String?,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: String,
    val strTags: String?,
    val strYoutube: String?,
    val ingredients: List<Ingredient>
)

data class Ingredient(
    val name: String,
    val measure: String
)
