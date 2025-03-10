package com.seroean.apps.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seroean.apps.R
import com.seroean.apps.data.response.NotifikasiData
import com.seroean.apps.databinding.ItemsNotifikasiBinding

class NotifikasiAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listNotifikasi = ArrayList<NotifikasiData>()
    private var isLoading = true

    @SuppressLint("NotifyDataSetChanged")
    fun showShimmerPlaceholder() {
        isLoading = true
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<NotifikasiData>) {
        isLoading = false
        listNotifikasi.clear()
        listNotifikasi.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (isLoading) 4 else listNotifikasi.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemsNotifikasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            holder.bind(listNotifikasi[position])
        }
    }

    class ListViewHolder(val binding: ItemsNotifikasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(notifikasi: NotifikasiData) {
            binding.apply {
                tvTitleNotifikasi.text = notifikasi.title
                tvDate.text = notifikasi.datetime + " " + "WIB"
            }
        }
    }
}
