package com.solutionplus.altasherat.features.personal_info.presentation.ui

import android.os.Bundle
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentPersonalInfoBinding
import com.solutionplus.altasherat.features.personal_info.data.models.request.PhoneRequest
import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.personal_info.domain.models.Country
import com.solutionplus.altasherat.features.personal_info.domain.models.User
import com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection.SingleSelection
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoAction
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@AndroidEntryPoint
class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding>() {

    private val viewModel: PersonalInfoViewModel by viewModels()

    private lateinit var countries: ArrayList<Country>
    private lateinit var selectedCountry: Country
    private lateinit var selectedCountryCode: Country
    private lateinit var selectedDate: LocalDate

    private val datePicker: MaterialDatePicker<Long> by lazy {
        val constraintsBuilder =
            CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())

        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraintsBuilder.build())
            .build()
    }

    private var selectionType = SelectionType.NONE

    override fun viewInit() {

        setFragmentResultListener(SelectionDialogFragment.REQUEST_KEY) { _, bundle ->
            val selectedCountryIndex =
                bundle.getInt(SelectionDialogFragment.SELECTED_COUNTRY_INDEX_KEY)

            val selectedCountry = countries[selectedCountryIndex]

            when (selectionType) {
                SelectionType.NONE -> {}
                SelectionType.COUNTRY -> onCountrySelected(selectedCountry)
                SelectionType.COUNTRY_CODE -> onCountryCodeSelected(selectedCountry)
            }
        }
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        with(binding) {
            inputCountryCode.editText?.let { editText ->
                editText.setOnClickListener {
                    navigateToSelectionDialog(
                        SelectionType.COUNTRY_CODE,
                        countries.indexOf(selectedCountryCode)
                    )
                }
            }

            inputCountry.setEndIconOnClickListener {
                navigateToSelectionDialog(
                    SelectionType.COUNTRY,
                    countries.indexOf(selectedCountry)
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

            buttonSave.setOnClickListener {
                val updateInfoRequest = UpdateInfoRequest(
                    firstname = inputFirstName.editText?.text.toString(),
                    middlename = inputMiddleName.editText?.text.toString(),
                    lastname = inputLastName.editText?.text.toString(),
                    phone = PhoneRequest(
                        number = inputPhoneNumber.editText?.text.toString(),
                        countryCode = selectedCountryCode.phoneCode
                    ),
                    email = inputEmail.editText?.text.toString(),
                    birthDate = selectedDate.toString(),
                    image = "",
                    countryId = selectedCountry.id
                )

                viewModel.processIntent(PersonalInfoAction.UpdatePersonalInfo(updateInfoRequest))
            }
        }
    }

    private fun navigateToSelectionDialog(selectionType: SelectionType, selectedIndex: Int) {
        this.selectionType = selectionType
        Navigation.findNavController(requireActivity(), R.id.myNavHostFragment).navigate(
            PersonalInfoFragmentDirections.actionPersonalInfoFragmentToSelectionDialogFragment(
                countries = countries.toTypedArray(),
                selectedIndex = selectedIndex
            )
        )
    }

    override fun subscribeToObservables() {
        viewModel.processIntent(PersonalInfoAction.GetCountries)
        collectFlowWithLifecycle(viewModel.viewState) {}

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            when (event) {
                is PersonalInfoEvent.CountriesIndex -> handleCountriesIndex(event.countries)
                is PersonalInfoEvent.UserPersonalInfo -> bindUser(event.user)
                is PersonalInfoEvent.PersonalInfoUpdated -> TODO()
            }
        }
    }

    private fun handleCountriesIndex(countriesList: ArrayList<Country>) {
        countries = countriesList
        viewModel.processIntent(PersonalInfoAction.GetUserPersonalInfo)
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


        selectedCountry = user.country
        selectedCountryCode = countries.first { it.phoneCode == user.country.phoneCode }

        setBirthDateText(user.birthDate)
        setCountryText(selectedCountry.name)
        setCountryCodeText(selectedCountryCode)
    }

    override fun onLoading(isLoading: Boolean) {
    }

    private fun onCountryCodeSelected(selectedItem: SingleSelection) {
        selectedCountryCode = selectedItem as Country
        setCountryCodeText(selectedCountryCode)
    }

    private fun onCountrySelected(selectedItem: SingleSelection) {
        selectedCountry = selectedItem as Country
        setCountryText(selectedCountry.name)
    }

    private fun setCountryCodeText(country: Country) {
        binding.inputCountryCode.editText?.setText(
            requireContext().getString(
                R.string.selected_country_code, country.flag, country.phoneCode.substring(2)
            )
        )
    }

    private fun setCountryText(countryName: String) {
        binding.inputCountry.editText?.setText(countryName)
    }

    private fun setBirthDateText(date: LocalDate) {
        selectedDate = date
        binding.inputBirthDate.editText?.setText(date.toString())
    }

    companion object {

    }
}