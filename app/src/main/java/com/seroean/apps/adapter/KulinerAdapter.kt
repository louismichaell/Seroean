package com.seroean.apps.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seroean.apps.data.response.KulinerData
import com.seroean.apps.databinding.ItemsPlaceholderBinding
import com.seroean.apps.ui.loadImage

class KulinerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listKuliner = ArrayList<KulinerData>()
    private var isLoading = true
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun showShimmerPlaceholder() {
        isLoading = true
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<KulinerData>) {
        isLoading = false
        listKuliner.clear()
        listKuliner.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        this.onItemClickCallback = callback
    }

    override fun getItemCount(): Int {
        return if (isLoading) 4 else listKuliner.size
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
            holder.bind(listKuliner[position], onItemClickCallback)
        }
    }

    class ListViewHolder(val binding: ItemsPlaceholderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(kuliner: KulinerData, callback: OnItemClickCallback?) {
            binding.apply {
                tvTitle.text = kuliner.nama
                img.loadImage(kuliner.foto)
            }

            itemView.setOnClickListener {
                callback?.onItemClicked(kuliner)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: KulinerData)
    }
}
