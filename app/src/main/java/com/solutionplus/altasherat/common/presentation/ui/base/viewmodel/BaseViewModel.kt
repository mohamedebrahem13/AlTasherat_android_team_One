package com.solutionplus.altasherat.common.presentation.ui.base.viewmodel

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<Event> : ViewModel() {
    abstract fun processEvent(event: Event)

}