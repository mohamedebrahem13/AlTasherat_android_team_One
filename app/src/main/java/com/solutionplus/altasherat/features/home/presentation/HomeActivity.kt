package com.solutionplus.altasherat.features.home.presentation

import android.os.Bundle
import com.etebarian.meowbottomnavigation.MeowBottomNavigation.Model
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityHomeBinding


class HomeActivity : BaseActivity<ActivityHomeBinding>() {


    override fun viewInit() {
        with(binding) {
            bottomNavigation.add(Model(1, R.drawable.ic_stamp))
            bottomNavigation.add(Model(2, R.drawable.ic_request))
            bottomNavigation.add(Model(3, R.drawable.ic_profile))
        }
    }


    override fun onActivityReady(savedInstanceState: Bundle?) {
    }

}