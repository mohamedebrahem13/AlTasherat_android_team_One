package com.solutionplus.altasherat.features.account.delete_account.domain.interactor

import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.constants.Constants.PASSWORD
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.account.delete_account.domain.repository.IDeleteAccountRepository
import com.solutionplus.altasherat.features.services.token.domain.interactor.DeleteCachedTokenUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.DeleteCachedUserUC

class DeleteAccountUC(
    private val repository: IDeleteAccountRepository,
    private val deleteCachedUserUC: DeleteCachedUserUC,
    private val deleteCachedTokenUC: DeleteCachedTokenUC
) : BaseUseCase<String, String>() {

    override suspend fun execute(params: String?): String {
        requireNotNull(params) {
            throw AlTasheratException.Local.RequestValidation(
                clazz = String::class,
                message = "Request is null"
            )
        }

        if (!isPasswordValid(params)) {
            throw AlTasheratException.Local.RequestValidation(
                clazz = String::class,
                errors = mapOf(PASSWORD to R.string.password_validation)
            )
        }

        val result = repository.deleteAccount(params)
        deleteCachedUserUC.execute()
        deleteCachedTokenUC.execute()
        return result
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 8 && password.length <= 50
    }
}