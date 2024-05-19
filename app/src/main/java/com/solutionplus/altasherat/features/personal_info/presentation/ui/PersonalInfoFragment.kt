package com.solutionplus.altasherat.features.personal_info.presentation.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentPersonalInfoBinding
import com.solutionplus.altasherat.features.personal_info.domain.models.Country
import com.solutionplus.altasherat.features.personal_info.domain.models.User
import com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection.SingleSelection
import com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection.SingleSelectionCallback
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoAction
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding>(),
    SingleSelectionCallback {

    private val viewModel: PersonalInfoViewModel by viewModels()

    private lateinit var countries: ArrayList<Country>
    private lateinit var countriesAdapter: ArrayAdapter<String>

    private val selectionBottomSheet by lazy {
        SelectionDialogFragment.newInstance(this, countries)
    }

    private var selectedCountryIndex = -1

    override fun viewInit() {
        countriesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line)

        (binding.inputCountry.editText as AutoCompleteTextView).setAdapter(countriesAdapter)
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        with(binding) {
            inputCountryCode.editText?.let { editText ->
                editText.setOnClickListener { showCountrySelectionSheet() }
            }

            (inputCountry.editText as AutoCompleteTextView).setOnItemClickListener { _, _, position, _ ->
                selectedCountryIndex = position
            }
        }
    }

    override fun subscribeToObservables() {
        viewModel.processIntent(PersonalInfoAction.GetCountries)

        collectFlowWithLifecycle(viewModel.viewState) {}

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            when (event) {
                is PersonalInfoEvent.CountriesIndex -> handleCountriesIndex(event.countries)
                is PersonalInfoEvent.UserPersonalInfo -> bindUser(event.user)
            }
        }
    }

    private fun handleCountriesIndex(countriesList: ArrayList<Country>) {
        countries = countriesList
        val countriesNamesArray = countries.map { it.name }
        countriesAdapter.addAll(countriesNamesArray)
        viewModel.processIntent(PersonalInfoAction.GetUserPersonalInfo)
    }

    private fun bindUser(user: User) {
        with(binding) {
            inputFirstName.editText?.setText(user.firstname)
            inputMiddleName.editText?.setText(user.middlename)
            inputLastName.editText?.setText(user.lastname)
            inputPhoneNumber.editText?.setText(user.phone.number)
            inputEmail.editText?.setText(user.email)
            inputBirthDate.editText?.setText(user.birthDate.toString())
        }

        val country = countries.first { it.phoneCode == user.phone.countryCode }
        country.isSelected = true
        setCountryCodeText(country)
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun onSingleItemSelected(selectedItem: SingleSelection) {
        val country = selectedItem as Country
        setCountryCodeText(country)

        countries.firstOrNull { it.isSelected }?.isSelected = false
        countries.find { it == country }?.isSelected = true

        selectionBottomSheet.dismiss()
    }

    private fun setCountryCodeText(country: Country) {
        binding.inputCountryCode.editText?.setText(
            requireContext().getString(
                R.string.selected_country_code, country.flag, country.phoneCode.substring(2)
            )
        )
    }

    private fun showCountrySelectionSheet() {
        if (!selectionBottomSheet.isAdded) {
            selectionBottomSheet.show(
                parentFragmentManager, SelectionDialogFragment::class.java.simpleName
            )
        }
    }
}