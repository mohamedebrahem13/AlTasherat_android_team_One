package com.solutionplus.altasherat.features.splash.presention

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger

import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSplachBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplachBinding>() {



    override fun onFragmentReady(savedInstanceState: Bundle?) {

    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        logger.debug("splash")

    }
    companion object {
        private val logger = getClassLogger()
    }


}