package com.solutionplus.altasherat.features.menu.contact_us.presentation.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentContactUsBinding
import com.solutionplus.altasherat.features.menu.contact_us.presentation.viewmodels.ContactUsContract
import com.solutionplus.altasherat.features.menu.contact_us.presentation.viewmodels.ContactUsViewModel
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactUsBinding>() {
    private var countryCode: String? = null
    private var countries: List<Country>? = null
    private val viewModel: ContactUsViewModel by viewModels()
    private val customCountiesList: ArrayList<String> = ArrayList()


    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.etCountryCode.setOnItemClickListener { _, _, position, _ ->
            val selectedCountryItem = countries?.get(position)
            selectedCountryItem.let { country ->
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

        // Log the size of the customCountiesList
        logger.debug("customCountiesList size: ${customCountiesList.size}")

        // Check if customCountiesList is not empty and get the default value
        val defaultCountryCode = customCountiesList.getOrNull(0)
        countryCode=defaultCountryCode
        logger.debug("defaultCountryCode: $defaultCountryCode")

        // Find the user's country index if the user is not null
        val userCountryIndex = user?.let {
            countries?.indexOfFirst { country -> country.phoneCode == it.country.phoneCode } ?: -1
        } ?: -1

        // Log the user country index
        logger.debug("userCountryIndex: $userCountryIndex")

        // Determine the country code to set
        val selectedCountryCode = if (userCountryIndex >= 0) {
            customCountiesList[userCountryIndex]
        } else {
            defaultCountryCode
        }

        // Log the selected country code
        logger.debug("selectedCountryCode: $selectedCountryCode")

        // Set the text only if selectedCountryCode is not null
        selectedCountryCode?.let {
            binding.etCountryCode.setText(it, false)
        } ?: logger.debug("selectedCountryCode is null, no text set")
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
                    setCustomCountry()
                    setUpCountryCodeAdapter(null)

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