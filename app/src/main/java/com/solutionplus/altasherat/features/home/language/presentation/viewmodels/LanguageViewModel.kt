package com.solutionplus.altasherat.features.home.language.presentation.viewmodels

import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.home.language.presentation.viewmodels.LanguageContract
import javax.inject.Inject

class LanguageViewModel@Inject constructor() : AlTasheratViewModel <LanguageContract.LanguageAction, LanguageContract.LanguageEvent, LanguageContract.LanguageViewState>(
    LanguageContract.LanguageViewState.initial()) {
    override fun clearState() {
        setState(LanguageContract.LanguageViewState.initial())
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is LanguageContract.LanguageAction.StartCountriesWorkerEn -> startCountriesWorker(action.language)
            is LanguageContract.LanguageAction.StartCountriesWorkerAr -> startCountriesWorker(action.language)
        }
    }
    private fun startCountriesWorker(language: String) {}

    }