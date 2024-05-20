package com.solutionplus.altasherat.features.splash.presention

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLanguageBinding
import com.solutionplus.altasherat.features.splash.domain.models.Country

class LanguageFragment : BaseFragment<FragmentLanguageBinding>() {


    override fun onFragmentReady(savedInstanceState: Bundle?) {
        val countries = listOf(
            Country(
                id = 1,
                name = "Saudi Arabia",
                nationality = "Saudi",
                currency = "SAR",
                code = "SA",
                phoneCode = "+966",
                visible = true,
                flag = "\uD83C\uDDEA\uD83C\uDDEC"
            ),
            Country(
                id = 2,
                name = "United Arab Emirates",
                nationality = "Emirati",
                currency = "AED",
                code = "AE",
                phoneCode = "+971",
                visible = true,
                flag = "\uD83C\uDDEA\uD83C\uDDEC"
            ),
            Country(
                id = 3,
                name = "United States",
                nationality = "American",
                currency = "USD",
                code = "US",
                phoneCode = "+1",
                visible = true,
                flag = "\uD83C\uDDEA\uD83C\uDDEC"
            ),
            // Add more countries as needed
        )
        val spinnerAdapter = CustomSpinnerAdapter(requireContext(), countries)
        binding.spinner.adapter = spinnerAdapter
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
    }


}