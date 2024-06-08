package com.solutionplus.altasherat.features.home.presentation

import android.content.Intent
import android.os.Bundle
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseSheetFragment
import com.solutionplus.altasherat.databinding.FragmentUnAuthorizedDialogBinding
import com.solutionplus.altasherat.features.auth.presentation.AuthActivity

class UnAuthorizedDialogFragment : BaseSheetFragment<FragmentUnAuthorizedDialogBinding>() {

    override fun viewInit() {}

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.buttonNext.setOnClickListener {
            Intent(requireActivity(), AuthActivity::class.java).also { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}