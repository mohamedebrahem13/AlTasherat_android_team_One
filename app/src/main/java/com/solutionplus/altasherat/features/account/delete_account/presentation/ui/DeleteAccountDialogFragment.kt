package com.solutionplus.altasherat.features.account.delete_account.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solutionplus.altasherat.databinding.FragmentDeleteAccountDialogBinding
import com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel.DeleteAccountViewModel

class DeleteAccountDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDeleteAccountDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DeleteAccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteAccountDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonConfirm.setOnClickListener {

        }
    }

    private fun subscribeToObservables() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}