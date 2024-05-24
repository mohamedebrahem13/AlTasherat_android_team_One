package com.solutionplus.altasherat.features.auth.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.features.services.country.domain.models.Country

class CountryCodeAdapter(
    context: Context,
    private val countries: List<Country>
) : ArrayAdapter<Country>(context, R.layout.country_menu_item, countries) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.country_menu_item, parent, false)

        val formattedCountryCode = context.getString(
            R.string.selected_country_code,
            countries[position].flag,
            countries[position].phoneCode.substring(2)
        )
        val countryCodeText = view.findViewById<TextView>(R.id.countryCodeTv)
        countryCodeText.text = formattedCountryCode
        return view
    }

    override fun getItem(position: Int): Country? {
        return countries.getOrNull(position)
    }

}












