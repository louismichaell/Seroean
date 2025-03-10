package com.seroean.apps.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seroean.apps.data.response.PemanduData
import com.seroean.apps.databinding.ItemsPemanduBinding
import com.seroean.apps.ui.loadImage

class PemanduAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listPemandu = ArrayList<PemanduData>()
    private var isLoading = true
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun showShimmerPlaceholder() {
        isLoading = true
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<PemanduData>) {
        isLoading = false
        listPemandu.clear()
        listPemandu.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        this.onItemClickCallback = callback
    }

    override fun getItemCount(): Int {
        return if (isLoading) 4 else listPemandu.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemsPemanduBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            holder.bind(listPemandu[position], onItemClickCallback)
        }
    }

    class ListViewHolder(val binding: ItemsPemanduBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pemandu: PemanduData, callback: OnItemClickCallback?) {
            binding.apply {
                tvnama.text = pemandu.name
                tvnotelp.text = pemandu.notelp
                imgProfile.loadImage(pemandu.profilePicture)
            }
            itemView.setOnClickListener {
                callback?.onItemClicked(pemandu)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: PemanduData)
    }
}
