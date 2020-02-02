package com.mohsenoid.gifbrowser.presentation.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mohsenoid.gifbrowser.R
import com.mohsenoid.gifbrowser.presentation.model.GifModel
import kotlinx.android.synthetic.main.result_item.view.gifThumb
import java.util.ArrayList

class ResultAdapter(
    private val listener: ClickListener
) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    private var data: MutableList<GifModel> = ArrayList()

    fun setResult(results: List<GifModel>) {
        this.data = results.toMutableList()
    }

    fun addMoreResult(results: List<GifModel>) {
        this.data.addAll(results)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gifModel = data[position]

        holder.setGifModel(gifModel)
        holder.view.setOnClickListener { listener.onResultClick(gifModel) }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val gifThumb: ImageView = view.gifThumb

        fun setGifModel(gifModel: GifModel) {
            Glide.with(view.context)
                .load(gifModel.thumbUrl)
                .placeholder(R.drawable.ic_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(gifThumb)
        }
    }

    interface ClickListener {
        fun onResultClick(gifModel: GifModel)
    }
}
