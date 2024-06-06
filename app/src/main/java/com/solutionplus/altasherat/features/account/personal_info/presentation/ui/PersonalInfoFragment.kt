package com.solutionplus.altasherat.features.account.personal_info.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.constants.Constants.BIRTH_DATE
import com.solutionplus.altasherat.common.domain.constants.Constants.COUNTRY
import com.solutionplus.altasherat.common.domain.constants.Constants.EMAIL
import com.solutionplus.altasherat.common.domain.constants.Constants.FIRST_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.LAST_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.MIDDLE_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE_NUMBER
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentPersonalInfoBinding
import com.solutionplus.altasherat.features.account.personal_info.data.models.request.PhoneRequest
import com.solutionplus.altasherat.features.account.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.account.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoAction
import com.solutionplus.altasherat.features.account.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.account.personal_info.presentation.viewmodel.PersonalInfoViewModel
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.models.User
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar

@AndroidEntryPoint
class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding>() {

    private val viewModel: PersonalInfoViewModel by viewModels()

    private lateinit var countries: List<Country>
    private var selectedCountryIndex: Int = -1
    private var selectedCountryCodeIndex: Int = -1
    private lateinit var selectedDate: LocalDate
    private var selectedImageUri: Uri? = null

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data: Intent? = it.data
            data?.data?.let { uri ->
                selectedImageUri = uri
                binding.imageProfile.setImageURI(uri)
            }
        }
    }


    private val datePicker: MaterialDatePicker<Long> by lazy {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -13)
        val thirteenYearsAgoInMillis = calendar.timeInMillis

        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.before(thirteenYearsAgoInMillis))

        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(thirteenYearsAgoInMillis)
            .setCalendarConstraints(constraintsBuilder.build())
            .build()
    }

    private var selectionType = SelectionType.NONE

    override fun viewInit() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.primary)

        setFragmentResultListener(SelectionDialogFragment.REQUEST_KEY) { _, bundle ->
            val selectedIndex =
                bundle.getInt(SelectionDialogFragment.SELECTED_COUNTRY_INDEX_KEY)

            when (selectionType) {
                SelectionType.NONE -> {}
                SelectionType.COUNTRY -> {
                    selectedCountryIndex = selectedIndex
                    setCountryText(selectedIndex)
                }

                SelectionType.COUNTRY_CODE -> {
                    selectedCountryCodeIndex = selectedIndex
                    setCountryCodeText(selectedIndex)
                }
            }
        }
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        with(binding) {
            inputCountryCode.editText?.let { editText ->
                editText.setOnClickListener {
                    navigateToSelectionDialog(
                        SelectionType.COUNTRY_CODE,
                        selectedCountryCodeIndex
                    )
                }
            }

            inputCountry.setEndIconOnClickListener {
                navigateToSelectionDialog(
                    SelectionType.COUNTRY,
                    selectedCountryIndex
                )
            }

            inputBirthDate.editText?.setOnClickListener {
                datePicker.show(parentFragmentManager, datePicker.toString())
            }

            datePicker.addOnPositiveButtonClickListener {
                val zoneId = ZoneId.systemDefault()
                val instant = Instant.ofEpochMilli(it)
                val localDate = instant.atZone(zoneId).toLocalDate()
                setBirthDateText(localDate)
            }


            buttonEditImage.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                galleryLauncher.launch(intent)
            }

            buttonSave.setOnClickListener {
                val file = selectedImageUri?.let { uri -> uriToFile(uri, requireContext()) }

                val updateInfoRequest = UpdateInfoRequest(
                    firstname = inputFirstName.editText?.text.toString(),
                    middlename = inputMiddleName.editText?.text.toString(),
                    lastname = inputLastName.editText?.text.toString(),
                    phone = PhoneRequest(
                        number = inputPhoneNumber.editText?.text.toString(),
                        countryCode = countries[selectedCountryIndex].phoneCode
                    ),
                    email = inputEmail.editText?.text.toString(),
                    birthDate = if (::selectedDate.isInitialized) selectedDate.toString() else "",
                    countryId = countries[selectedCountryIndex].id,
                    image = file
                )

                viewModel.processIntent(PersonalInfoAction.UpdatePersonalInfo(updateInfoRequest))
            }

            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.processIntent(PersonalInfoAction.GetUpdatedUserPersonalInfo)
                binding.swipeRefreshLayout.isRefreshing = true
            }

            buttonMore.setOnClickListener {
                val action =
                    PersonalInfoFragmentDirections.actionPersonalInfoFragmentToAccountSettingFragment()
                findNavController().navigate(action)
            }

        }
    }

    private fun navigateToSelectionDialog(selectionType: SelectionType, selectedIndex: Int) {
        this.selectionType = selectionType

        val action =
            PersonalInfoFragmentDirections.actionPersonalInfoFragmentToSelectionDialogFragment(
                countries = countries.toTypedArray(),
                selectedIndex = selectedIndex
            )
        findNavController().navigate(action)
    }

    override fun subscribeToObservables() {
        viewModel.processIntent(PersonalInfoAction.GetCountries)
        collectFlowWithLifecycle(viewModel.viewState) { state ->
            onLoading(state.isLoading)

            state.exception?.let { exception ->
                handleException(exception, ::handleValidationErrors)
            }
        }

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            when (event) {
                is PersonalInfoEvent.CountriesIndex -> handleCountriesIndex(event.countries)
                is PersonalInfoEvent.UserPersonalInfo -> {
                    bindUser(event.user)
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                is PersonalInfoEvent.PersonalInfoUpdated -> {
                    Toast.makeText(
                        requireContext(),
                        "Personal info updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun handleCountriesIndex(countriesList: List<Country>) {
        countries = countriesList
        viewModel.processIntent(PersonalInfoAction.GetCachedUserPersonalInfo)
    }

    private fun bindUser(user: User) {
        with(binding) {
            inputFirstName.editText?.setText(user.firstname)
            inputMiddleName.editText?.setText(user.middlename)
            inputLastName.editText?.setText(user.lastname)
            inputPhoneNumber.editText?.setText(user.phone.number)
            inputEmail.editText?.setText(user.email)
            imageProfile.load(user.image.path) {
                crossfade(true)
                placeholder(R.drawable.ic_no_profile_image)
                error(R.drawable.ic_no_profile_image)
                transformations(CircleCropTransformation())
            }
        }

        selectedCountryIndex = countries.indexOfFirst { it.id == user.country.id }
        selectedCountryCodeIndex = countries.indexOfFirst { it.phoneCode == user.phone.countryCode }

        setBirthDateText(user.birthDate)
        setCountryText(selectedCountryIndex)
        setCountryCodeText(selectedCountryCodeIndex)
    }

    override fun onLoading(isLoading: Boolean) {
    }

    private fun setCountryText(index: Int) {
        binding.inputCountry.editText?.setText(countries[index].name)
    }

    private fun setCountryCodeText(index: Int) {
        val country = countries[index]
        binding.inputCountryCode.editText?.setText(
            requireContext().getString(
                R.string.selected_country_code, country.flag, country.phoneCode.substring(2)
            )
        )
    }

    private fun setBirthDateText(date: LocalDate) {
        if (date != LocalDate.MIN) {
            selectedDate = date
            binding.inputBirthDate.editText?.setText(date.toString())
        } else {
            binding.inputBirthDate.editText?.text = null
        }
    }

    private fun handleValidationErrors(errors: Map<String, String>) {
        val errorFields = mapOf(
            FIRST_NAME to binding.inputFirstName,
            MIDDLE_NAME to binding.inputMiddleName,
            LAST_NAME to binding.inputLastName,
            EMAIL to binding.inputEmail,
            PHONE_NUMBER to binding.inputPhoneNumber,
            PHONE to binding.inputPhoneNumber,
            BIRTH_DATE to binding.inputBirthDate,
            COUNTRY to binding.inputCountry
        )

        errorFields.forEach { (key, field) ->
            field.error = errors[key]
            field.editText?.doAfterTextChanged { field.error = null }
        }
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val tempFile = File(context.cacheDir, "temp_image")
        val outputStream = FileOutputStream(tempFile)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return tempFile
    }

}