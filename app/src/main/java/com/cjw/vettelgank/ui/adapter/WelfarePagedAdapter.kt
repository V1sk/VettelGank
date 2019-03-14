package com.cjw.vettelgank.ui.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cjw.vettelgank.R
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.ui.gallery.GalleryActivity
import kotlinx.android.synthetic.main.recycler_item_welfare.view.*

class WelfarePagedAdapter(retryCallback: () -> Unit) :
    BaseGankPagedAdapter(R.layout.recycler_item_welfare, retryCallback) {

    override fun render(itemView: View, data: Gank?) {
        Glide.with(itemView.context)
            .applyDefaultRequestOptions(RequestOptions().apply {
                centerCrop()
            })
            .load(data?.url)
            .into(itemView.iv_welfare)
        itemView.tag = data
        itemView.setOnClickListener(onclickListener)
    }

    private val onclickListener = View.OnClickListener {
        if (currentList.isNullOrEmpty())
            return@OnClickListener
        val gank = it.tag as Gank
        val position = currentList?.indexOf(gank)
        val list = currentList?.toList()
        if (position != null && list != null) {
            GalleryActivity.start(it.context, position, list.map { gank ->
                gank.url
            } as ArrayList<String>)
        }
    }


}