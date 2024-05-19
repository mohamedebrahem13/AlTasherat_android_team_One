package com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection

import java.io.Serializable

interface SingleSelection : Serializable {
    var id: Int
    var name: String
    var flag: String
    var isSelected: Boolean
}