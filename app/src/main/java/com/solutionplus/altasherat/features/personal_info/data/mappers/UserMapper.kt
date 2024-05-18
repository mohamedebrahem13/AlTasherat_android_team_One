package com.solutionplus.altasherat.features.personal_info.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personal_info.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.personal_info.domain.models.User
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

internal object UserMapper : Mapper<Unit, User, UserEntity>() {
    override fun dtoToDomain(model: Unit): User {
        throw NotImplementedError("not implemented")
    }

    override fun entityToDomain(model: UserEntity): User {
        return User(
            id = model.id,
            firstname = model.firstname,
            middlename = model.middlename,
            lastname = model.lastname,
            email = model.email,
            phone = PhoneMapper.entityToDomain(model.phone),
            image = ImageMapper.entityToDomain(model.image),
            birthDate = LocalDateTime.parse(
                model.birthDate + " 00:00",
                DateTimeFormatter.ofPattern("d-M-yyyy HH:mm").withZone(ZoneOffset.UTC)
            )
        )
    }
}