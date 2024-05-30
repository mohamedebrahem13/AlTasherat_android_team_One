package com.solutionplus.altasherat.features.personal_info.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoAction
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoViewModel
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.models.User
import dagger.hilt.android.AndroidEntryPoint
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

            buttonSave.setOnClickListener {
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
                    countryId = countries[selectedCountryIndex].id
                )

                viewModel.processIntent(PersonalInfoAction.UpdatePersonalInfo(updateInfoRequest))
            }

            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }

            binding.swipeRefreshLayout.setOnRefreshListener {
                viewModel.processIntent(PersonalInfoAction.GetUpdatedUserPersonalInfo)
                binding.swipeRefreshLayout.isRefreshing = true
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
        collectFlowWithLifecycle(viewModel.viewState) {}

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
        }else {
            binding.inputBirthDate.editText?.text = null
        }
    }
}