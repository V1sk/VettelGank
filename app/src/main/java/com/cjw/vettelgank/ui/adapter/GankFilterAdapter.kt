package com.cjw.vettelgank.ui.adapter

import android.view.View
import com.cjw.vettelgank.R
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.ext.getDateString
import com.cjw.vettelgank.ui.common.WebActivity
import kotlinx.android.synthetic.main.recycler_item_gank_data.view.*

class GankFilterAdapter(
    private val gankList: MutableList<Gank>
) :
    BaseAdapter<Gank>(gankList, R.layout.recycler_item_gank_data) {

    fun replaceItems(gankList: MutableList<Gank>) {
        this.gankList.clear()
        this.gankList.addAll(gankList)
        notifyDataSetChanged()
    }

    private val onClickListener = View.OnClickListener {
        val gank = it.tag as Gank
        WebActivity.start(it.context, gank.url)
    }

    override fun render(itemView: View, data: Gank) {
        itemView.tv_desc.text = data.desc
        itemView.tv_who.text = data.who
        itemView.tv_date.text = data.publishedAt.getDateString()
        itemView.item_wrapper.tag = data
        itemView.item_wrapper.setOnClickListener(onClickListener)
    }

}