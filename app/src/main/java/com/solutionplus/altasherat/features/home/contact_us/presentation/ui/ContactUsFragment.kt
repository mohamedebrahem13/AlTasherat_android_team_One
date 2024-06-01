package com.solutionplus.altasherat.features.home.contact_us.presentation.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentContactUsBinding
import com.solutionplus.altasherat.features.home.contact_us.presentation.viewmodels.ContactUsContract
import com.solutionplus.altasherat.features.home.contact_us.presentation.viewmodels.ContactUsViewModel
import com.solutionplus.altasherat.features.services.country.domain.models.Country
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

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            handleSingleEvent(event)
        }
    }
    private fun setUpCountryCodeAdapter() {
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.country_menu_item, customCountiesList)
        binding.etCountryCode.setAdapter(arrayAdapter)
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
                    setUpCountryCodeAdapter()
                }
            }

        }
    }

    override fun viewInit() {
    }

}