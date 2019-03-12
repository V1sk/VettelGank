package com.cjw.vettelgank.ui.adapter.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cjw.vettelgank.R
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.ext.getDateString
import com.cjw.vettelgank.ui.common.WebActivity
import kotlinx.android.synthetic.main.recycler_item_gank_data.view.*

class GankDataHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val onClickListener = View.OnClickListener {
        val gank = it.tag as Gank
        WebActivity.start(it.context, gank.url)
    }

    fun setup(gank: Gank) {
        itemView.tv_desc.text = gank.desc
        itemView.tv_who.text = gank.who
        itemView.tv_date.text = gank.publishedAt.getDateString()
        itemView.item_wrapper.tag = gank
        itemView.item_wrapper.setOnClickListener(onClickListener)
    }

    companion object {
        fun create(parent: ViewGroup): GankDataHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_gank_data, parent, false)
            return GankDataHolder(view)
        }
    }
}