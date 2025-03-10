package com.seroean.apps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seroean.apps.data.response.ImageDataResponse
import com.seroean.apps.databinding.ItemsSlideBinding

class ImageSliderAdapter(private val imageList: List<ImageDataResponse>) :
    RecyclerView.Adapter<ImageSliderAdapter.SliderViewHolder>() {

    class SliderViewHolder(private val binding: ItemsSlideBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageData: ImageDataResponse) {
            Glide.with(binding.ivSlider.context)
                .load(imageData.imageUrl)
                .centerCrop()
                .into(binding.ivSlider)

            binding.ivSlider.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val binding = ItemsSlideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size
}
