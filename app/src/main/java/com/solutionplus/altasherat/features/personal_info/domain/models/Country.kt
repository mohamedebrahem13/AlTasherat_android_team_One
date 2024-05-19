package com.solutionplus.altasherat.features.personal_info.domain.models

import android.os.Parcelable
import com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection.SingleSelection
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    override var id: Int,
    override var name: String,
    val phoneCode: String,
    override var flag: String,
    override var isSelected: Boolean
) : SingleSelection, Parcelable