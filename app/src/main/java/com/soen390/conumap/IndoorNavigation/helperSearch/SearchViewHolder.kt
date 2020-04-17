package com.soen390.conumap.IndoorNavigation.helperSearch

import androidx.recyclerview.widget.RecyclerView
import com.soen390.conumap.databinding.SearchItemBinding

class SearchViewHolder(private val binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(text: String){
        binding.resultText.text = text
    }
}
