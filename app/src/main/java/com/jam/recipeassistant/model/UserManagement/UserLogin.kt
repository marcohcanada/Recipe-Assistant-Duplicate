package com.jam.recipeassistant.model.UserManagement

/**
 * Created by 991470628 : MARCO HIDALGO ROMERO
 * on 2022-01-30
 */
import kotlinx.serialization.*

@Serializable
data class UserLogin(
    var email : String,
    var password : String,
    var result: Int
)
