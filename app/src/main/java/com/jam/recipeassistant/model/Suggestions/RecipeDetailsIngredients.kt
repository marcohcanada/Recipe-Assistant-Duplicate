package com.jam.recipeassistant.model.Suggestions

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetailsIngredients (
    var RecipeIngredientAmount: Double,
    var RecipeIngredientUnit: String,
    var IngredientName: String
    )