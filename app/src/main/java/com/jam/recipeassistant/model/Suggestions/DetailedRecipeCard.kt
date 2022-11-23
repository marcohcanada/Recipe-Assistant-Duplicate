package com.jam.recipeassistant.model.Suggestions

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class DetailedRecipeCard (
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
    var Severity:Int,
    var SearchDetailString:String,
)