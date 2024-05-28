package com.solutionplus.altasherat.features.services.user.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.services.user.data.models.dto.ImageDto
import com.solutionplus.altasherat.features.services.user.data.models.entity.ImageEntity
import com.solutionplus.altasherat.features.services.user.domain.models.Image

internal object ImageMapper : Mapper<ImageDto, Image, ImageEntity>() {

    override fun dtoToDomain(model: ImageDto): Image {
        return Image(
            id = model.id ?: -1,
            type = model.type.orEmpty(),
            path = model.path.orEmpty(),
            title = model.title.orEmpty(),
            description = model.description.orEmpty(),
            priority = model.priority ?: -1,
            isMain = model.isMain ?: false,
            createdAt = model.createdAt.orEmpty(),
            updatedAt = model.updatedAt.orEmpty()
        )
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

    override fun domainToEntity(model: Image): ImageEntity {
        return ImageEntity(
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