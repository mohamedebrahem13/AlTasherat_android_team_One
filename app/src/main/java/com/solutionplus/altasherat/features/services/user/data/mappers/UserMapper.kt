package com.solutionplus.altasherat.features.services.user.data.mappers

import com.solutionplus.altasherat.android.extentions.parseDateString
import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.services.user.data.models.dto.CountryDto
import com.solutionplus.altasherat.features.services.user.data.models.dto.ImageDto
import com.solutionplus.altasherat.features.services.user.data.models.dto.PhoneDto
import com.solutionplus.altasherat.features.services.user.data.models.dto.UserDto
import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.services.user.domain.models.User

internal object UserMapper : Mapper<UserDto, User, UserEntity>() {

    override fun dtoToDomain(model: UserDto): User {
        return User(
            id = model.id ?: -1,
            username = model.username.orEmpty(),
            firstname = model.firstname.orEmpty(),
            middlename = model.middleName.orEmpty(),
            lastname = model.lastname.orEmpty(),
            email = model.email.orEmpty(),
            phone = PhoneMapper.dtoToDomain(model.phone ?: PhoneDto()),
            image = ImageMapper.dtoToDomain(model.image ?: ImageDto()),
            birthDate = model.birthDate.orEmpty().parseDateString(),
            isEmailVerified = model.emailVerified ?: false,
            isPhoneVerified = model.isPhoneVerified ?: false,
            isBlocked = model.isBlocked ?: -1,
            country = CountryMapper.dtoToDomain(model.country ?: CountryDto()),
            allPermissions = model.allPermissions.orEmpty()
        )
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

    override fun domainToEntity(model: User): UserEntity {
        return UserEntity(
            id = model.id,
            username = model.username,
            firstname = model.firstname,
            middlename = model.middlename,
            lastname = model.lastname,
            email = model.email,
            phone = PhoneMapper.domainToEntity(model.phone),
            image = ImageMapper.domainToEntity(model.image),
            birthDate = model.birthDate.toString(),
            isEmailVerified = model.isEmailVerified,
            isPhoneVerified = model.isPhoneVerified,
            isBlocked = model.isBlocked,
            country = CountryMapper.domainToEntity(model.country),
            allPermissions = model.allPermissions
        )
    }
}