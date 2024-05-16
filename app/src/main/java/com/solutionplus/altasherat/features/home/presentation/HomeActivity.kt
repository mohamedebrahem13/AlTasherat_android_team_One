package com.solutionplus.altasherat.features.home.presentation

import android.os.Bundle
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityHomeBinding


class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun viewInit() {
        binding.bottomNav.itemIconTintList = null
    }


    override fun onActivityReady(savedInstanceState: Bundle?) {
    }

}