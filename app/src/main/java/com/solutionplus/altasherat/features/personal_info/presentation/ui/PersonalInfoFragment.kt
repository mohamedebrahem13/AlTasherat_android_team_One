package com.solutionplus.altasherat.features.personal_info.presentation.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentPersonalInfoBinding
import com.solutionplus.altasherat.features.personal_info.domain.models.Country
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoState
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding>() {

    private val viewModel: PersonalInfoViewModel by viewModels()
    private lateinit var countriesAdapter: ArrayAdapter<Country>

    override fun viewInit() {
        countriesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)
        (binding.inputCountryCode.editText as AutoCompleteTextView).setAdapter(countriesAdapter)
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        collectFlowWithLifecycle(viewModel.viewState) { state ->
            bindState(state)
        }

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            when (event) {
                is PersonalInfoContract.PersonalInfoEvent.CountriesIndex -> {
                    countriesAdapter.clear()
                    countriesAdapter.addAll(event.countries)
                }
            }
        }
    }

    private fun bindState(state: PersonalInfoState) {
        with(binding) {
            inputFirstName.editText?.setText(state.userPersonalInfo.firstname)
            inputMiddleName.editText?.setText(state.userPersonalInfo.middlename)
            inputLastName.editText?.setText(state.userPersonalInfo.lastname)
            inputPhoneNumber.editText?.setText(state.userPersonalInfo.phone.number)
            inputCountryCode.editText?.setText(state.userPersonalInfo.phone.countryCode)
            inputEmail.editText?.setText(state.userPersonalInfo.email)
            inputBirthDate.editText?.setText(state.userPersonalInfo.birthDate.toString())
        }
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
    }
}