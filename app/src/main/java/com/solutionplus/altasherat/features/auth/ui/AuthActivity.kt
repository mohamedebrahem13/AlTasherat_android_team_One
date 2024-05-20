package com.solutionplus.altasherat.features.auth.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.databinding.ActivityAuthBinding
import com.solutionplus.altasherat.features.auth.login.presentation.ui.LoginFragment
import com.solutionplus.altasherat.features.auth.signup.presentation.ui.SignUpFragment
import com.solutionplus.altasherat.features.auth.ui.listener.LoginSignupButtonListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity(), LoginSignupButtonListener {

    private lateinit var binding: ActivityAuthBinding

    private lateinit var button: Button

    override fun updateButtonText(text: String) {
        button.text = text
    }

    override fun triggerButton(trigger: () -> Unit) {
        button.setOnClickListener { trigger() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        button = binding.btnLoginSignup

        val viewPager = binding.viewPager
        val fragments = listOf<Fragment>(SignUpFragment(), LoginFragment())
        val adapter =
            ViewPagerAdapter(fragments, supportFragmentManager, lifecycle)
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

        setContentView(binding.root)

    }
}