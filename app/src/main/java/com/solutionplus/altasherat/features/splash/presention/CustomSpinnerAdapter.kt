package com.solutionplus.altasherat.features.splash.presention

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.solutionplus.altasherat.databinding.SpinnerItemBinding
import com.solutionplus.altasherat.features.splash.domain.models.Country

class CustomSpinnerAdapter(private val context: Context, private val countries: List<Country>) : BaseAdapter() {

    override fun getCount(): Int = countries.size

    override fun getItem(position: Int): Country = countries[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: SpinnerItemBinding
        val view: View

        if (convertView == null) {
            binding = SpinnerItemBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as SpinnerItemBinding
            view = convertView
        }

        val country = countries[position]
        binding.itemFlag.text = country.flag
        binding.itemText.text = country.name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }
}