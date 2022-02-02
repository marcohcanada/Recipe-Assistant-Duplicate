package com.jam.recipeassistant.model.Suggestions

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetailsSteps (
    var StepNumber: Int,
    var StepText: String,
        )