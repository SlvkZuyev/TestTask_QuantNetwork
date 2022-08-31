package com.sldev.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageApiModel (

    @Json(name = "id")
    val id: String,

    @Json(name = "description")
    val description: String?,

    @Json(name = "user")
    val user: User,

    @Json(name = "urls")
    val urls: Urls,
)

@JsonClass(generateAdapter = true)
data class Urls (
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

@JsonClass(generateAdapter = true)
data class User (
    val id: String,
    val username: String,
    val name: String,
)