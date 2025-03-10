package com.seroean.apps.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seroean.apps.data.response.BudayaData
import com.seroean.apps.databinding.ItemsPlaceholderBinding
import com.seroean.apps.ui.loadImage

class BudayaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listBudaya = ArrayList<BudayaData>()
    private var isLoading = true
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun showShimmerPlaceholder() {
        isLoading = true
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<BudayaData>) {
        listBudaya.clear()
        listBudaya.addAll(data)
        isLoading = false
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        this.onItemClickCallback = callback
    }

    override fun getItemCount(): Int {
        return if (isLoading) 4 else listBudaya.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemsPlaceholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ListViewHolder).binding

        if (isLoading) {
            binding.shimmerViewContainer.startShimmer()
            binding.shimmerViewContainer.visibility = View.VISIBLE
            binding.contentContainer.visibility = View.GONE
        } else {
            binding.shimmerViewContainer.stopShimmer()
            binding.shimmerViewContainer.visibility = View.GONE
            binding.contentContainer.visibility = View.VISIBLE
            holder.bind(listBudaya[position], onItemClickCallback)
        }
    }

    class ListViewHolder(val binding: ItemsPlaceholderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(budaya: BudayaData, callback: OnItemClickCallback?) {
            binding.apply {
                tvTitle.text = budaya.nama
                img.loadImage(budaya.foto)
            }

            itemView.setOnClickListener {
                callback?.onItemClicked(budaya)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: BudayaData)
    }
}
