package com.jam.recipeassistant.model.Suggestions

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class RecipeCard (
        var RecipeId:Int,
        var RecipeName: String,
        var RecipeImage: String,
        var RecipeDescription: String,
        var CreateUserName: String,
        var MonetaryScale: Int,
        var Likes:Long,
        var Dislikes:Long,
        var Views:Long,
        var Severity:Int,
)