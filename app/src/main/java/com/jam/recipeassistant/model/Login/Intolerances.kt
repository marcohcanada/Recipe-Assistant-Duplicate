package com.jam.recipeassistant.model.Login

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class Intolerances(
    var ingredient : String,
    var severity : Int
)