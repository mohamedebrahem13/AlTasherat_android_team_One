package com.solutionplus.altasherat.features.reset_password.presentation

import android.os.Bundle
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentViewPagerResetPasswordBinding
import com.solutionplus.altasherat.common.presentation.ui.view_pager.ViewPagerAdapter
import com.solutionplus.altasherat.common.presentation.ui.listener.SharedButtonListener

class FragmentViewPagerResetPassword : BaseFragment<FragmentViewPagerResetPasswordBinding>() {

    private val fragments by lazy {
        listOf(
            FragmentFirstResetPassword(),
            FragmentSecondResetPassword(),
            FragmentThirdResetPassword()
        )
    }

    private val adapter by lazy {
        ViewPagerAdapter(fragmentManager = requireActivity().supportFragmentManager, fragments = fragments, lifecycle = lifecycle)
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.btnSend.setOnClickListener {
            val currentItem =
                fragments[binding.viewPager.currentItem] as SharedButtonListener
            currentItem.triggerButton()
        }
    }

    override fun onLoading(isLoading: Boolean) {

    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
        binding.viewPager.adapter = adapter
    }
}