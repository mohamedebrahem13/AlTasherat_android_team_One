package com.solutionplus.altasherat.features.services.user.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.services.user.data.models.entity.ImageEntity
import com.solutionplus.altasherat.features.services.user.domain.models.Image

internal object ImageMapper : Mapper<Unit, Image, ImageEntity>() {
    override fun dtoToDomain(model: Unit): Image {
        throw NotImplementedError("not implemented")
    }

    override fun entityToDomain(model: ImageEntity): Image {
        return Image(
            id = model.id,
            type = model.type,
            path = model.path,
            title = model.title,
            description = model.description,
            priority = model.priority,
            isMain = model.isMain,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt
        )
    }
}