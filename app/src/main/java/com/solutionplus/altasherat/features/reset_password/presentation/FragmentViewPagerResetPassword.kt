package com.solutionplus.altasherat.features.reset_password.presentation

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.solutionplus.altasherat.android.extentions.onBackButtonPressed
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentViewPagerResetPasswordBinding
import com.solutionplus.altasherat.common.presentation.ui.view_pager.ViewPagerAdapter

class FragmentViewPagerResetPassword : BaseFragment<FragmentViewPagerResetPasswordBinding>() {

    private val fragments by lazy {
        listOf(
            FragmentFirstResetPassword(),
            FragmentSecondResetPassword(),
            FragmentThirdResetPassword()
        )
    }

    private val adapter by lazy {
        ViewPagerAdapter(
            fragmentManager = requireActivity().supportFragmentManager,
            fragments = fragments,
            lifecycle = lifecycle
        )
    }

    private lateinit var viewPager: ViewPager2

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.btnSend.setOnClickListener {
            if (viewPager.currentItem < 2)
                viewPager.currentItem += 1
        }
        onBackButtonPressed()
    }

    override fun onLoading(isLoading: Boolean) {

    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
        viewPager = binding.viewPager
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
    }
}

