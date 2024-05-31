package com.solutionplus.altasherat.common.presentation.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solutionplus.altasherat.android.extentions.bindView

abstract class BaseSheetFragment<Binding : ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: Binding? = null
    protected val binding: Binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewInit()
        onFragmentReady(savedInstanceState)
    }

    abstract fun viewInit()

    abstract fun onFragmentReady(savedInstanceState: Bundle?)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}