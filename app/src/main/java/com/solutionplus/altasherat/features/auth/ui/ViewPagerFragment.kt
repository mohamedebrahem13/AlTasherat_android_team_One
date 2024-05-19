package com.solutionplus.altasherat.features.auth.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentViewpagerBinding
import com.solutionplus.altasherat.features.auth.login.presentation.ui.LoginFragment
import com.solutionplus.altasherat.features.auth.signup.presentation.ui.SignUpFragment
import com.solutionplus.altasherat.features.auth.ui.listener.LoginSignupButtonListener

class ViewPagerFragment : BaseFragment<FragmentViewpagerBinding>(), LoginSignupButtonListener {

    private lateinit var button: Button

    override fun updateButtonIcon(icon: Drawable?) {
        button.setCompoundDrawables(icon, null, null, null)
    }

    override fun updateButtonText(text: String) {
        button.text = text
    }

    override fun triggerButton(trigger: () -> Unit) {
        button.setOnClickListener { trigger() }
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {

        val viewPager = binding.viewPager
        val fragments = listOf<Fragment>(SignUpFragment(), LoginFragment())
        val adapter =
            ViewPagerAdapter(fragments, requireActivity().supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        val tabLayout = binding.tabLayout


        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.new_account)

                }

                1 -> {
                    tab.text = getString(R.string.login_text)
                }
            }

        }.attach()
    }

    override fun onLoading(isLoading: Boolean) {

    }

    override fun subscribeToObservables() {

    }

    override fun viewInit() {
        button = binding.btnLoginSignup
    }
}