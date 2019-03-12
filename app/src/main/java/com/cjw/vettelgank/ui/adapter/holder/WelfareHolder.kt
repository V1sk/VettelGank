package com.cjw.vettelgank.ui.adapter.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cjw.vettelgank.R
import com.cjw.vettelgank.data.Gank
import kotlinx.android.synthetic.main.recycler_item_welfare.view.*

class WelfareHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//    private val onclickListener = View.OnClickListener {
//        val gank = it.tag as Gank
//        val position = gankList.indexOf(gank)
//        GalleryActivity.start(it.context, position, gankList as ArrayList<Gank>)
//    }

    fun bind(gank: Gank) {
        Glide.with(itemView.context)
            .applyDefaultRequestOptions(RequestOptions().apply {
                centerCrop()
            })
            .load(gank.url)
            .into(itemView.iv_welfare)
//        itemView.tag = data
//        itemView.setOnClickListener(onclickListener)
    }

    companion object {
        fun create(parent: ViewGroup): WelfareHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_welfare, parent, false)
            return WelfareHolder(view)
        }
    }

}