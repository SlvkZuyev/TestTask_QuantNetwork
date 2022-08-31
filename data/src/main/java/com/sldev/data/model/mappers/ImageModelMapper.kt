package com.sldev.data.model.mappers

import com.sldev.data.model.ImageApiModel
import com.sldev.domain.model.ImageModel
import com.sldev.domain.model.Urls

fun ImageApiModel.toImageModel(): ImageModel {
    return ImageModel(
        description = description,
        author = user.username,
        urls = Urls(
            small = urls.small,
            large = urls.full
        )
    )
}