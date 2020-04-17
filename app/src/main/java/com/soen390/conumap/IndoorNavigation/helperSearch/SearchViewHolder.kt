package com.soen390.conumap.IndoorNavigation.helperSearch

import androidx.recyclerview.widget.RecyclerView

class SearchViewHolder(private val binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(text: String){
        binding.resultText.text = text
    }
}
