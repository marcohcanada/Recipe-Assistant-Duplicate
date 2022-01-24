package com.jam.recipeassistant.model.Suggestions

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
class RecipeCard (

        var RecipeId:Int,
        var RecipeName: String,
        var RecipeImage: String,
        var RecipeDescription: String,
        var CreateUserName: String,
        var MonetaryScale: Int,
)