package com.solutionplus.altasherat.features.personal_info.presentation.ui

import android.os.Bundle
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentPersonalInfoBinding
import com.solutionplus.altasherat.features.personal_info.domain.models.Country
import com.solutionplus.altasherat.features.personal_info.domain.models.User
import com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection.SingleSelection
import com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection.SingleSelectionCallback
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfoFragment() : BaseFragment<FragmentPersonalInfoBinding>(),
    SingleSelectionCallback {

    private val viewModel: PersonalInfoViewModel by viewModels()
    private lateinit var countries: ArrayList<Country>
    private val modalBottomSheet by lazy {
        ItemListDialogFragment.newInstance(this, countries)
    }
    private var selectedItemIndex = -1

    override fun viewInit() {
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        with(binding) {
            inputCountryCode.editText?.let { editText ->
                editText.setOnClickListener {
                    modalBottomSheet.show(parentFragmentManager, "ItemListDialogFragment")
                }
            }
        }
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.viewState) { state ->
        }

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            when (event) {
                is PersonalInfoEvent.CountriesIndex -> {
                    countries = event.countries
                }

                is PersonalInfoEvent.UserPersonalInfo -> bindUser(event.user)
            }
        }
    }

    private fun bindUser(state: User) {
        with(binding) {
            inputFirstName.editText?.setText(state.firstname)
            inputMiddleName.editText?.setText(state.middlename)
            inputLastName.editText?.setText(state.lastname)
            inputPhoneNumber.editText?.setText(state.phone.number)
            (inputCountryCode.editText as AutoCompleteTextView).setText(
                state.phone.countryCode,
                false
            )
            inputEmail.editText?.setText(state.email)
            inputBirthDate.editText?.setText(state.birthDate.toString())
        }
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun onSingleItemSelected(selectedItem: SingleSelection) {
        with(binding) {
            val country = selectedItem as Country
            inputCountryCode.editText?.setText(
                requireContext().getString(
                    R.string.selected_country_code,
                    country.flag,
                    country.phoneCode.substring(2)
                )
            )
        }

        if (selectedItemIndex != -1) {
            countries[selectedItemIndex].isSelected = false
        }

        selectedItemIndex = countries.indexOf(selectedItem as Country)
        countries[selectedItemIndex].isSelected = true
        modalBottomSheet.dismiss()
    }
}