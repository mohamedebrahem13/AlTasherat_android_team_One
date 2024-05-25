package com.solutionplus.altasherat.features.home.visa_platform.presentation.ui

import android.os.Bundle
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentVisaPlatformBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VisaPlatformFragment : BaseFragment<FragmentVisaPlatformBinding>() {

    private val visaItems: List<VisaItem> by lazy {
        listOf(
            VisaItem(
                titleFirstPart = R.string.first_title_first_part,
                titleSecondPart = R.string.first_title_second_part,
                buttonText = R.string.first_button_text,
                actionText = R.string.first_action_text,
                image = R.drawable.image_tourism_visa
            ),

            VisaItem(
                titleFirstPart = R.string.second_title_first_part,
                titleSecondPart = R.string.second_title_second_part,
                buttonText = R.string.second_button_text,
                actionText = R.string.second_action_text,
                image = R.drawable.image_employment_visa
            ),

            VisaItem(
                titleFirstPart = R.string.third_title_first_part,
                titleSecondPart = R.string.third_title_second_part,
                buttonText = R.string.third_button_text,
                actionText = R.string.third_action_text,
                image = R.drawable.image_visa_selling
            )
        )
    }

    private val visaPlatformAdapter by lazy {
        VisaPlatformAdapter(visaItems)
    }


    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.recyclerViewVisaPlatform.adapter = visaPlatformAdapter
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
    }
}