package com.solutionplus.altasherat.features.auth.reset_password

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.common.presentation.ui.view_pager.ViewPagerAdapter
import com.solutionplus.altasherat.databinding.FragmentViewPagerResetPasswordBinding

class FragmentViewPagerResetPassword : BaseFragment<FragmentViewPagerResetPasswordBinding>() {

    private val fragments by lazy {
        listOf(
            FragmentFirstResetPassword(),
            FragmentSecondResetPassword(),
            FragmentThirdResetPassword()
        )
    }

    private lateinit var viewPager: ViewPager2

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.btnSend.setOnClickListener {
            if (viewPager.currentItem < 2)
                viewPager.currentItem += 1
        }
    }

    override fun onLoading(isLoading: Boolean) {

    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
        viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(fragments = fragments, container = this)
        viewPager.isUserInputEnabled = false
    }
}

