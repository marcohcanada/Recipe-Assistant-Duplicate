package com.jam.recipeassistant.model.Suggestions

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class UpdatedRecipeDetails (
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
    var Rating:Double,
    var AllowReview:Int,
    var SeverityItems: List<String>,
    var RecipeReviews: List<String>,
    var RecipeDetailsTags: List<String>,
    var RecipeIngredients: List<RecipeDetailsIngredients>,
    var RecipeSteps: List<RecipeDetailsSteps>
)