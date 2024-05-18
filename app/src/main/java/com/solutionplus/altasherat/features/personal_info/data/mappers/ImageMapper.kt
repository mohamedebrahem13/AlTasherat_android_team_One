package com.solutionplus.altasherat.features.personal_info.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personal_info.data.models.entity.ImageEntity
import com.solutionplus.altasherat.features.personal_info.domain.models.Image

internal object ImageMapper : Mapper<Unit, Image, ImageEntity>() {
    override fun dtoToDomain(model: Unit): Image {
        throw NotImplementedError("not implemented")
    }

    override fun entityToDomain(model: ImageEntity): Image {
        return Image(id = model.id, path = model.path)
    }
}