package com.cjw.vettelgank.ui.adapter.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup, layoutResId: Int): BaseViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(layoutResId, parent, false)
            return BaseViewHolder(view)
        }
    }

}