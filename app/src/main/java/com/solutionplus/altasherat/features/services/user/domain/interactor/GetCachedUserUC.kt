package com.solutionplus.altasherat.features.services.user.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.user.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.models.Image
import com.solutionplus.altasherat.features.services.user.domain.models.Phone
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository
import java.time.LocalDate

class GetCachedUserUC(
    private val repository: IUserRepository
) : BaseUseCase<User, Unit>() {

    override suspend fun execute(params: Unit?): User {
        val fakeUser = User(
            id = 1,
            username = "fakeuser",
            firstname = "John",
            middlename = "Doe",
            lastname = "Smith",
            email = "john.doe@example.com",
            phone = Phone(
                id = 1,
                countryCode = "+1",
                number = "123456789",
                extension = "",
                type = "Mobile",
                holderName = "John Doe"
            ),
            image = Image(
                id = 1,
                type = "profile",
                path = "https://example.com/profile.jpg",
                title = "Profile Image",
                description = "Profile picture of John Doe",
                priority = 1,
                isMain = true,
                createdAt = "",
                updatedAt = ""
            ),
            birthDate = LocalDate.of(1990, 1, 1),
            isEmailVerified = true,
            isPhoneVerified = true,
            isBlocked = 0,
            country = Country(
                id = 1,
                name = "United States",
                currency = "USD",
                code = "US",
                phoneCode = "+1",
                flag = "https://example.com/us-flag.png",
                isSelected = true
            ),
            allPermissions = listOf("read", "write", "delete")
        )

        return fakeUser
}
    }