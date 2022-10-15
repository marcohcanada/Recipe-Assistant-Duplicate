package com.jam.recipeassistant.model.Suggestions

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetails (
    var RecipeId:Int,
    var RecipeName: String,
    var RecipeImage: String,
    var RecipeImageType: String,
    var RecipeDescription: String,
    var CreateUserName: String,
    var MonetaryScale: Int,
    var Likes:Long,
    var Dislikes:Long,
    var Views:Long,
    var Severity:Int,
    var RecipeDetailsTags: List<String>,
    var RecipeIngredients: List<RecipeDetailsIngredients>,
    var RecipeSteps: List<RecipeDetailsSteps>
)