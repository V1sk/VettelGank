package com.cjw.vettelgank.ui.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cjw.vettelgank.R
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.ui.gallery.GalleryActivity
import kotlinx.android.synthetic.main.recycler_item_welfare.view.*

class WelfareAdapter(
    val gankList: MutableList<Gank>
) :
    BaseAdapter<Gank>(gankList, R.layout.recycler_item_welfare) {

    fun replaceItems(gankList: MutableList<Gank>) {
        this.gankList.clear()
        this.gankList.addAll(gankList)
        notifyDataSetChanged()
    }

    private val onclickListener = View.OnClickListener {
        val gank = it.tag as Gank
        val position = gankList.indexOf(gank)
        GalleryActivity.start(it.context, position, gankList as ArrayList<Gank>)
    }

    override fun render(itemView: View, data: Gank) {
        Glide.with(itemView.context)
            .applyDefaultRequestOptions(RequestOptions().apply {
                centerCrop()
            })
            .load(data.url)
            .into(itemView.iv_welfare)
        itemView.tag = data
        itemView.setOnClickListener(onclickListener)
    }

}