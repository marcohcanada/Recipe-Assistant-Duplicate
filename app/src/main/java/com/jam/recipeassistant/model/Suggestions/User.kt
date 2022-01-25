package com.jam.recipeassistant.model.Suggestions

import kotlinx.serialization.Serializable

@Serializable
data class User (
    var UserId : Int,
    var FirstName : String,
    var LastName : String,
    var Email : String,
    var Password : String,
)