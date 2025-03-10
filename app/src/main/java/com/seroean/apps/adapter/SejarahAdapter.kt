package com.seroean.apps.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seroean.apps.data.response.SejarahData
import com.seroean.apps.databinding.ItemsPlaceholderBinding
import com.seroean.apps.ui.loadImage

class SejarahAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listSejarah = ArrayList<SejarahData>()
    private var isLoading = true
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun showShimmerPlaceholder() {
        isLoading = true
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<SejarahData>) {
        isLoading = false
        listSejarah.clear()
        listSejarah.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        this.onItemClickCallback = callback
    }

    override fun getItemCount(): Int {
        return if (isLoading) 4 else listSejarah.size
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
            holder.bind(listSejarah[position], onItemClickCallback)
        }
    }

    class ListViewHolder(val binding: ItemsPlaceholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sejarah: SejarahData, callback: OnItemClickCallback?) {
            binding.apply {
                tvTitle.text = sejarah.nama
                img.loadImage(sejarah.foto)
            }
            itemView.setOnClickListener {
                callback?.onItemClicked(sejarah)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: SejarahData)
    }
}
