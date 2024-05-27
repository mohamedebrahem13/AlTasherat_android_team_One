package com.solutionplus.altasherat.features.personal_info.domain.models

import android.os.Parcelable
import com.solutionplus.altasherat.common.presentation.ui.adapter.SingleSelection
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    override var id: Int,
    override var name: String,
    val phoneCode: String,
    val flag: String,
    override var isSelected: Boolean
) : SingleSelection, Parcelable