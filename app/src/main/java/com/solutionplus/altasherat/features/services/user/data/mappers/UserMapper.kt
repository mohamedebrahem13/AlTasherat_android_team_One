package com.solutionplus.altasherat.features.services.user.data.mappers

import com.solutionplus.altasherat.android.extentions.parseDateString
import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.services.user.domain.models.User

internal object UserMapper : Mapper<Unit, User, UserEntity>() {
    override fun dtoToDomain(model: Unit): User {
        throw NotImplementedError("not implemented")
    }

    override fun entityToDomain(model: UserEntity): User {
        return User(
            id = model.id,
            username = model.username,
            firstname = model.firstname,
            middlename = model.middlename,
            lastname = model.lastname,
            email = model.email,
            phone = PhoneMapper.entityToDomain(model.phone),
            image = ImageMapper.entityToDomain(model.image),
            birthDate = model.birthDate.parseDateString(),
            isEmailVerified = model.isEmailVerified,
            isPhoneVerified = model.isPhoneVerified,
            isBlocked = model.isBlocked,
            country = CountryMapper.entityToDomain(model.country),
            allPermissions = model.allPermissions
        )
    }
}