package com.cjw.vettelgank.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cjw.vettelgank.R
import com.cjw.vettelgank.data.ui.GankDataItem
import com.cjw.vettelgank.data.ui.GankHeaderItem
import com.cjw.vettelgank.data.ui.GankItem
import com.cjw.vettelgank.ui.adapter.holder.GankDataHolder
import kotlinx.android.synthetic.main.recycler_item_gank_header.view.*

class GankDailyAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val gankItemList: MutableList<GankItem> = mutableListOf()

    fun replaceItems(gankItemList: MutableList<GankItem>) {
        this.gankItemList.clear()
        this.gankItemList.addAll(gankItemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            GankItem.ITEM_HEADER -> {
                val itemView =
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.recycler_item_gank_header,
                        parent, false
                    )
                return GankHeaderHolder(itemView)
            }
            GankItem.ITEM_DATA -> {
                val itemView =
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.recycler_item_gank_data,
                        parent, false
                    )
                return GankDataHolder(itemView)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int {
        return gankItemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GankHeaderHolder -> {
                holder.setup(gankItemList[position] as GankHeaderItem)
            }
            is GankDataHolder -> {
                holder.setup((gankItemList[position] as GankDataItem).gank)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return gankItemList[position].type
    }

    class GankHeaderHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setup(gankHeaderItem: GankHeaderItem) {
            itemView.tv_header.text = gankHeaderItem.name
        }

    }

}