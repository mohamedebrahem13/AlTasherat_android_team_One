package com.solutionplus.altasherat.features.home.visa_platform.presentation.ui

import android.os.Bundle
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentVisaPlatformBinding
import com.solutionplus.altasherat.features.home.visa_platform.presentation.ui.adapter.VisaPlatformAdapter
import com.solutionplus.altasherat.features.home.visa_platform.presentation.ui.adapter.VisaPlatformCallback
import com.solutionplus.altasherat.features.home.visa_platform.presentation.ui.adapter.VisaPlatformItem
import com.solutionplus.altasherat.features.home.visa_platform.presentation.ui.adapter.VisaTypeEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VisaPlatformFragment : BaseFragment<FragmentVisaPlatformBinding>(), VisaPlatformCallback {

    private val visaPlatformItems: List<VisaPlatformItem> by lazy {
        listOf(
            VisaPlatformItem(
                type = VisaTypeEnum.TOURISM_VISA,
                titleFirstPart = R.string.first_title_first_part,
                titleSecondPart = R.string.first_title_second_part,
                buttonText = R.string.first_button_text,
                actionText = R.string.first_action_text,
                image = R.drawable.image_tourism_visa
            ),

            VisaPlatformItem(
                type = VisaTypeEnum.EMPLOYMENT_VISA,
                titleFirstPart = R.string.second_title_first_part,
                titleSecondPart = R.string.second_title_second_part,
                buttonText = R.string.second_button_text,
                actionText = R.string.second_action_text,
                image = R.drawable.image_employment_visa
            ),

            VisaPlatformItem(
                type = VisaTypeEnum.VISA_SELLING,
                titleFirstPart = R.string.third_title_first_part,
                titleSecondPart = R.string.third_title_second_part,
                buttonText = R.string.third_button_text,
                actionText = R.string.third_action_text,
                image = R.drawable.image_visa_selling
            )
        )
    }

    private val visaPlatformAdapter by lazy {
        VisaPlatformAdapter(visaPlatformItems, this)
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

    override fun onVisaItemClicked(clickedItemType: VisaTypeEnum) {
        when (clickedItemType) {
            VisaTypeEnum.TOURISM_VISA -> {
            }

            VisaTypeEnum.EMPLOYMENT_VISA -> {
            }

            VisaTypeEnum.VISA_SELLING -> {
            }
        }
    }
}