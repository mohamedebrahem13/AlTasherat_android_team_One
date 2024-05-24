package com.solutionplus.altasherat.features.auth.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.databinding.ActivityAuthBinding
import com.solutionplus.altasherat.features.auth.login.presentation.ui.LoginFragment
import com.solutionplus.altasherat.features.auth.signup.presentation.ui.SignUpFragment
import com.solutionplus.altasherat.features.auth.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var button: Button
    private lateinit var viewPager: ViewPager2
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        button = binding.btnLoginSignup
        viewPager = binding.viewPager
        val fragments = listOf<Fragment>(SignUpFragment(), LoginFragment())
        val adapter =
            ViewPagerAdapter(fragments, supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        val tabLayout = binding.tabLayout

        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val cardView = binding.cardView
                cardView.apply {
                    // Measure the CardView to get its intrinsic dimensions
                    measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

                    // Get the measured height
                    val measuredHeight = measuredHeight

                    // Set the CardView height to the measured height
                    layoutParams?.height = measuredHeight
                    layoutParams = layoutParams
                }
            }
        })

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

        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        val listener = viewModel.listener
        if (listener != null) {
            viewModel.buttonText.observe(this) {
                button.text = it
            }
        }
        button.setOnClickListener { listener?.triggerButton() }

        setContentView(binding.root)
    }
}