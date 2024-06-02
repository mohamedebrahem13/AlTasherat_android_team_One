package com.solutionplus.altasherat.features.home.contact_us.presentation.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentContactUsBinding
import com.solutionplus.altasherat.features.home.contact_us.presentation.viewmodels.ContactUsContract
import com.solutionplus.altasherat.features.home.contact_us.presentation.viewmodels.ContactUsViewModel
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactUsBinding>() {
    private var countryId: Int? = null
    private var countryCode: String? = null
    private var countries: List<Country>? = null
    private val viewModel: ContactUsViewModel by viewModels()
    private val customCountiesList: ArrayList<String> = ArrayList()


    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.etCountryCode.setOnItemClickListener { _, _, position, _ ->
            val selectedCountryItem = countries?.get(position)
            selectedCountryItem.let { country ->
                countryId = country?.id
                countryCode = country?.phoneCode
            }

        }
    }

    override fun onLoading(isLoading: Boolean) {}


    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            handleSingleEvent(event)
        }
        collectFlowWithLifecycle(viewModel.viewState) { state ->
            handleViewState(state)
        }
    }
    private fun setUpCountryCodeAdapter(user: User?) {
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.country_menu_item, customCountiesList)
        binding.etCountryCode.setAdapter(arrayAdapter)

        // Find the user's country index if the user is not null, else set it to -1
        val userCountryIndex = user?.let {
            countries?.indexOfFirst { country -> country.phoneCode == user.country.phoneCode } ?: -1
        } ?: -1

        // If a valid user country index is found, set the text to that country code
        if (userCountryIndex >= 0) {
            binding.etCountryCode.setText(customCountiesList[userCountryIndex], false)
        } else {
            // If no valid user country index is found, set the text to the default value (first item in the list)
            binding.etCountryCode.setText(customCountiesList.getOrNull(0), false)
        }
    }

    private fun setCustomCountry() {
        countries?.forEach {
            val formattedCountryCode = requireContext().getString(
                R.string.selected_country_code,
                it.flag,
                it.phoneCode.substring(2)
            )
            customCountiesList.add(formattedCountryCode)
        }
    }
    private fun handleSingleEvent(event: ContactUsContract.ContactUsEvent) {
        when (event) {
            is ContactUsContract.ContactUsEvent.GetCountries -> {
                if (event.countries?.isNotEmpty() == true) {
                    countries = event.countries
                    countryId = countries!![0].id
                    countryCode = countries!![0].phoneCode
                    setCustomCountry()
                }
            }

            ContactUsContract.ContactUsEvent.BackClick ->{
                findNavController().popBackStack()
            }

        }
    }
    private fun handleViewState(state: ContactUsContract.ContactUsState) {
        when {
            state.exception != null -> {
                // Handle error state
                val errorMessage = state.exception.message ?: "Unknown error"
                showToast("Error: $errorMessage")
            }
            state.user != null -> {
                logger.debug("user_country${state.user}")
                setupContactView(state.user)
                setUpCountryCodeAdapter(state.user)

            } else -> {
            setupContactView(null)
            setUpCountryCodeAdapter(null) //

        }

        }
    }
    private fun setupContactView(user:User?) {
        if (user != null) {
            binding.etEmail.setText(user.email)
            binding.etName.setText(user.firstname)
            binding.etPhoneNumber.setText(user.phone.number)
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun viewInit() {
        binding.buttonBack.setOnClickListener {
            viewModel.onActionTrigger(ContactUsContract.ContactUsAction.BackClick)

        }
    }
    companion object {
        private val logger = getClassLogger()
    }

}