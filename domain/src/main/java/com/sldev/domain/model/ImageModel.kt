package com.sldev.domain.model

data class ImageModel(
   val description: String?,
   val author: String,
   val urls: Urls
)

data class Urls(
    val small: String,
    val large: String
)

