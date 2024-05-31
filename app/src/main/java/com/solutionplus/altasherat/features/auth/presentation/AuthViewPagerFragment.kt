package com.solutionplus.altasherat.features.auth.presentation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.common.presentation.ui.view_pager.ViewPagerAdapter
import com.solutionplus.altasherat.databinding.FragmentSignupLoginBinding
import com.solutionplus.altasherat.features.auth.login.presentation.ui.LoginFragment
import com.solutionplus.altasherat.features.auth.presentation.listener.LoginSignupButtonListener
import com.solutionplus.altasherat.features.auth.signup.presentation.ui.SignUpFragment
import com.solutionplus.altasherat.features.home.presentation.HomeActivity

class AuthViewPagerFragment : BaseFragment<FragmentSignupLoginBinding>() {

    private val fragments by lazy {
        listOf<Fragment>(LoginFragment(), SignUpFragment())
    }

    private lateinit var viewPager: ViewPager2

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.btnLoginSignup.setOnClickListener {
            val currentItem =
                fragments[viewPager.currentItem] as LoginSignupButtonListener
            currentItem.triggerButton()
        }

        binding.skipTv.setOnClickListener {
            Intent(requireActivity(), HomeActivity::class.java).also { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
        viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(fragments = fragments, container = this)

        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
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

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.login_text)

                }

                1 -> {
                    tab.text = getString(R.string.new_account)
                }
            }
        }.attach()
    }
}