package com.solutionplus.altasherat.features.home.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun viewInit() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController


        with(binding) {
            bottomNavHome.itemIconTintList = null
            bottomNavHome.setupWithNavController(navController)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragment_visa_platform, R.id.fragment_visa_request,
                R.id.fragment_profile -> {
                    binding.bottomNavHome.visibility = View.VISIBLE
                }

                else -> {
                    binding.bottomNavHome.visibility = View.GONE
                }
            }
        }
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
    }
}