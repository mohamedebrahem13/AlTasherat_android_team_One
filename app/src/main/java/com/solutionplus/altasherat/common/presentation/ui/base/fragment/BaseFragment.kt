package com.solutionplus.altasherat.common.presentation.ui.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.solutionplus.altasherat.android.extentions.bindView
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.ui.base.IExceptionHandling
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.delegate.InternetConnectionDelegate
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.delegate.InternetConnectionDelegateImpl
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment<Binding : ViewBinding> : Fragment(), IExceptionHandling,
    InternetConnectionDelegate by InternetConnectionDelegateImpl() {

    private var _binding: Binding? = null
    protected val binding: Binding
        get() = _binding!!

    private lateinit var exceptionHandling: IExceptionHandling

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IExceptionHandling) {
            exceptionHandling = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindView()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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

    abstract fun subscribeToObservables()

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

    override fun handleException(
        exception: AlTasheratException,
        action: ViewAction?,
        handleValidationErrors: (Map<String, String>) -> Unit
    ) {
        if (::exceptionHandling.isInitialized) {
            exceptionHandling.handleException(
                exception = exception,
                action = action,
                handleValidationErrors = handleValidationErrors
            )
        }
    }

    abstract fun onRetryAction(action: ViewAction?, message: String)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}