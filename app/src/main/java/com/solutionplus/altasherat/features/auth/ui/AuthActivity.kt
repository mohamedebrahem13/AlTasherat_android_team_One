package com.solutionplus.altasherat.features.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityAuthBinding
import com.solutionplus.altasherat.features.auth.login.presentation.ui.LoginFragment
import com.solutionplus.altasherat.features.auth.signup.presentation.ui.SignUpFragment
import com.solutionplus.altasherat.features.auth.ui.listener.LoginSignupButtonListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    private val fragments by lazy {
        listOf<Fragment>(LoginFragment(), SignUpFragment())
    }

    private val adapter by lazy {
        ViewPagerAdapter(fragmentManager = supportFragmentManager, fragments = fragments, lifecycle = lifecycle)
    }

    override fun viewInit() {
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                binding.cardView.requestLayout()

                when (position) {
                    0 -> {
                        binding.btnLoginSignup.text = getString(R.string.login_text)
                    }

                    1 -> {
                        binding.btnLoginSignup.text = getString(R.string.signup_text)
                    }
                }
            }
        })

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
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

    override fun onActivityReady(savedInstanceState: Bundle?) {
        binding.btnLoginSignup.setOnClickListener {
            val currentItem =
                fragments[binding.viewPager.currentItem] as LoginSignupButtonListener
            currentItem.triggerButton()
        }
    }

}