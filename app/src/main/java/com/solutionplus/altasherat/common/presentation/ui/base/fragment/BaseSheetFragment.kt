package com.solutionplus.altasherat.common.presentation.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solutionplus.altasherat.android.extentions.bindView
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.delegate.InternetConnectionDelegate
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.delegate.InternetConnectionDelegateImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseSheetFragment<Binding : ViewBinding> : BottomSheetDialogFragment(),
    InternetConnectionDelegate by InternetConnectionDelegateImpl() {

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
        subscribeToObservables()
    }

    abstract fun onFragmentReady(savedInstanceState: Bundle?)

    abstract fun onLoading(isLoading: Boolean)

    open fun subscribeToObservables(){}

    abstract fun viewInit()

    fun isInternetAvailable(): Boolean {
        return isInternetAvailable(requireContext())
    }

    protected fun <T> collectFlowWithLifecycle(flow: Flow<T>, action: (T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest {
                    action(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}