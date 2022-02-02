package com.jam.recipeassistant.model.Suggestions

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetails (
    var RecipeId:Int,
    var RecipeName: String,
    var RecipeImage: String,
    var RecipeDescription: String,
    var CreateUserName: String,
    var MonetaryScale: Int,
    var RecipeIngredients: List<RecipeDetailsIngredients>,
    var RecipeSteps: List<RecipeDetailsSteps>
)