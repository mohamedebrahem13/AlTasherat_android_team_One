package com.solutionplus.altasherat.features.home.profile.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.databinding.ItemListBinding

class ItemAdapter(context: Context, private val items: List<Item>, private val itemClickListener: ItemClickListener) :
    ArrayAdapter<Item>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemListBinding
        val view: View

        if (convertView == null) {
            binding = ItemListBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            view = convertView
            binding = view.tag as ItemListBinding
        }

        val item = getItem(position)!!
        binding.itemButton.setImageResource(item.buttonImageRes)
        binding.itemText.text = item.text

        // Set click listener
        view.setOnClickListener {
            itemClickListener.onItemClick(item)
        }

        return view
    }

    // Define click listener interface
    interface ItemClickListener {
        fun onItemClick(item: Item)
    }
}