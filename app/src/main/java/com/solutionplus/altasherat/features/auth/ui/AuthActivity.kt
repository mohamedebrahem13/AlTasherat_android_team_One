package com.solutionplus.altasherat.features.auth.ui

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {
    override fun viewInit() {}
    override fun onActivityReady(savedInstanceState: Bundle?) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_graph) as NavHostFragment
        // Get the NavController
        val navController = navHostFragment.navController
        // Set up the ActionBar with the Navigation UI
        setupActionBarWithNavController(navController)

        navController.navigate(R.id.action_authViewPagerFragment_to_fragmentViewPagerResetPassword)
    }
}