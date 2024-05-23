package com.solutionplus.altasherat.features.personal_info.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personal_info.data.models.dto.ImageDto
import com.solutionplus.altasherat.features.personal_info.data.models.entity.ImageEntity
import com.solutionplus.altasherat.features.personal_info.domain.models.Image

internal object ImageMapper : Mapper<ImageDto, Image, ImageEntity>() {
    override fun dtoToDomain(model: ImageDto): Image {
        return Image(id = model.id ?: -1, path = model.path.orEmpty())
    }

    override fun entityToDomain(model: ImageEntity): Image {
        return Image(id = model.id, path = model.path)
    }
}