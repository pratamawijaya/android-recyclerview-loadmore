package com.pratama.rvloadmore

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pratama.rvloadmore.databinding.ItemRvBinding

class MainAdapter(private val listItem: List<TextItem>) :
    RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val itemBinding = ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount() = listItem.size

    inner class MainHolder(private val itemRvBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {

        fun bind(item: TextItem) {
            itemRvBinding.tvNumber.text = "${item.number}"
            itemRvBinding.tvText.text = "${item.text}"
        }
    }
}