package com.solutionplus.altasherat.features.auth.signup.domain.interactor

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.features.auth.signup.data.model.request.PhoneSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation.UserInputsValidationUC
import com.solutionplus.altasherat.features.auth.signup.domain.repository.ISignUpRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import io.mockk.mockk
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SignUpUCTest {


    private lateinit var signUpRepository: ISignUpRepository
    private lateinit var saveUserUC: SaveUserUC
    private lateinit var userInputsValidation: UserInputsValidationUC
    private lateinit var signupUC: SignUpUC


    @Before
    fun setUp() {
        signUpRepository = mockk(relaxed = true)
        saveUserUC = mockk(relaxed = true)

        userInputsValidation = UserInputsValidationUC()
        signupUC = SignUpUC(signUpRepository, userInputsValidation, saveUserUC)

    }

    @Test
    fun userSignUp_SignUpProgress_returnLoadingState() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "4452233699")
        val userRequest = UserSignUpRequest(
            "Ebram",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "1111255569",
            "1111255569",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).first()
        assertTrue((expected as Resource.Progress).loading)
    }

    @Test
    fun userValidation_emptyFirstName_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "4452233699")
        val userRequest = UserSignUpRequest(
            "",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "1111255569",
            "1111255569",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()
        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_firstnameMinLength_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "4452233699")
        val userRequest = UserSignUpRequest(
            "e",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "1111255569",
            "1111255569",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_firstnameMaxLength_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "4452233699")
        val userRequest = UserSignUpRequest(
            "eeeeeeeeeeeeeeee",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "1111255569",
            "1111255569",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_firstnameSpecialChar_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "4452233699")
        val userRequest = UserSignUpRequest(
            "Ebram@Ibrahem!!",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "1111255569",
            "1111255569",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_emailPattern_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "4452233699")
        val userRequest = UserSignUpRequest(
            "Ebram",
            "Ibrahem",
            "Aziz",
            "email989.com",
            "1111255569",
            "1111255569",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_emptyPhoneNumber_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "")
        val userRequest = UserSignUpRequest(
            "Ebram",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "1111255569",
            "1111255569",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_nonNumericPhoneNumber_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "456aas223")
        val userRequest = UserSignUpRequest(
            "Ebram",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "1111255569",
            "1111255569",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_minLengthPhoneNumber_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "44958")
        val userRequest = UserSignUpRequest(
            "Ebram",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "1111255569",
            "1111255569",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_maxLengthPhoneNumber_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "44958698754123659")
        val userRequest = UserSignUpRequest(
            "Ebram",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "1111255569",
            "1111255569",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_emptyPassword_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "4452233699")
        val userRequest = UserSignUpRequest(
            "Ebram",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "",
            "",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_minLengthPassword_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "4452233699")
        val userRequest = UserSignUpRequest(
            "Ebram",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "526",
            "526",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_maxLengthPassword_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "4452233699")
        val userRequest = UserSignUpRequest(
            "Ebram",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "561451608495464641231231456478978975465156656555655",
            "561451608495464641231231456478978975465156656555655",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_missingUpperCasePassword_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "4452233699")
        val userRequest = UserSignUpRequest(
            "Ebram",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "5699874113",
            "5699874113",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_missingLowerCasePassword_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "4452233699")
        val userRequest = UserSignUpRequest(
            "Ebram",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "5699874113",
            "5699874113",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun userValidation_missingDigitsPassword_returnException() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "4452233699")
        val userRequest = UserSignUpRequest(
            "Ebram",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "password",
            "password",
            1,
            phoneRequest
        )
        val expected = signupUC(userRequest).drop(1).first()

        assertTrue((expected as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }


}



