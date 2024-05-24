package com.solutionplus.altasherat.features.personal_info.data.mappers

import com.solutionplus.altasherat.android.extentions.parseDateString
import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personal_info.data.models.dto.CountryDto
import com.solutionplus.altasherat.features.personal_info.data.models.dto.ImageDto
import com.solutionplus.altasherat.features.personal_info.data.models.dto.PhoneDto
import com.solutionplus.altasherat.features.personal_info.data.models.dto.UserDto
import com.solutionplus.altasherat.features.personal_info.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.personal_info.domain.models.User

internal object UserMapper : Mapper<UserDto, User, UserEntity>() {

    override fun dtoToDomain(model: UserDto): User {
        return User(
            id = model.id ?: -1,
            firstname = model.firstname.orEmpty(),
            middlename = model.middlename.orEmpty(),
            lastname = model.lastname.orEmpty(),
            email = model.email.orEmpty(),
            phone = PhoneMapper.dtoToDomain(model.phone ?: PhoneDto()),
            image = ImageMapper.dtoToDomain(model.image ?: ImageDto()),
            birthDate = model.birthDate.orEmpty().parseDateString(),
            country = CountryMapper.dtoToDomain(model.country ?: CountryDto())
        )
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
            birthDate = model.birthDate.parseDateString(),
            country = CountryMapper.entityToDomain(model.country)
        )
    }
}