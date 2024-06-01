package com.solutionplus.altasherat.features.home.contact_us.presentation.ui

import android.os.Bundle
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentContactUsBinding
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactUsBinding>() {
    private var countryId: Int? = null
    private var countryCode: String? = null
    private var countries: List<Country>? = null

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
    }

    override fun viewInit() {
    }

}