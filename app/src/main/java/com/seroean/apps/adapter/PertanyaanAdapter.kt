package com.seroean.apps.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seroean.apps.data.response.PertanyaanData
import com.seroean.apps.databinding.ItemsPertanyaanBinding

class PertanyaanAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listPertanyaan = ArrayList<PertanyaanData>()
    private var isLoading = true
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun showShimmerPlaceholder() {
        isLoading = true
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<PertanyaanData>) {
        isLoading = false
        listPertanyaan.clear()
        listPertanyaan.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        this.onItemClickCallback = callback
    }

    override fun getItemCount(): Int {
        return if (isLoading) 4 else listPertanyaan.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemsPertanyaanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            holder.bind(listPertanyaan[position], onItemClickCallback)
        }
    }

    class ListViewHolder(val binding: ItemsPertanyaanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pertanyaan: PertanyaanData, callback: OnItemClickCallback?) {
            binding.apply {
                tvTipe.text = pertanyaan.tipe
                tvQuestion.text = pertanyaan.pertanyaan
            }
            itemView.setOnClickListener {
                callback?.onItemClicked(pertanyaan)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: PertanyaanData)
    }
}
