package com.jam.recipeassistant.model.UserManagement

import kotlinx.serialization.*

@Serializable
data class NewUser(
    var UserName : String,
    var Email : String,
    var Password : String,
    var FirstName : String,
    var LastName : String,
    var AddressLine : String,
    var City : String,
    var State : String,
    var PostalCode : String,
    var Country : String,
)