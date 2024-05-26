package com.solutionplus.altasherat.features.splash.presention.ui.activity

import android.os.Bundle
import androidx.fragment.app.commit

import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityOnboardingBinding
import com.solutionplus.altasherat.features.splash.presention.ui.fragments.OnBoardingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>() {

    override fun viewInit() {

    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.onBoardingFragmentContainer, OnBoardingFragment())
            }
        }
    }

}