package com.jam.recipeassistant.model.Shared

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable

data class SimpleStringList (
    var Items:List<String>
)