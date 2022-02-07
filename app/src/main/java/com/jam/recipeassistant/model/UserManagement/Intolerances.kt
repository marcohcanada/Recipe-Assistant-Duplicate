package com.jam.recipeassistant.model.UserManagement

import kotlinx.serialization.*

@Serializable
data class Intolerances(
    var ingredient : String,
    var severity : Int
)