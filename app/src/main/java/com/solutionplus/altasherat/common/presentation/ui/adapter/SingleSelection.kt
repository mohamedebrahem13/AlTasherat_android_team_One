package com.solutionplus.altasherat.common.presentation.ui.adapter

import java.io.Serializable

interface SingleSelection : Serializable {
    var id: Int
    var name: String
    var isSelected: Boolean
}