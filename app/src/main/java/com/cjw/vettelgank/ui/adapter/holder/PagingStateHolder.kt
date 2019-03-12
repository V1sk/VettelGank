package com.cjw.vettelgank.ui.adapter.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cjw.vettelgank.R
import com.cjw.vettelgank.data.paging.NetworkState
import kotlinx.android.synthetic.main.layout_loading_more.view.*

class PagingStateHolder(itemView: View, retryCallback: () -> Unit) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            status = NetworkState.LOADING
            retryCallback()
        }
    }

    var status: NetworkState? = NetworkState.HIDDEN
        set(value) {
            field = value
            setup(value)
        }

    private fun setup(status: NetworkState?) {
        when (status) {
            NetworkState.HIDDEN -> {
                itemView.visibility = View.GONE
            }
            NetworkState.LOADING -> {
                itemView.visibility = View.VISIBLE
                itemView.loading_layout.visibility = View.VISIBLE
                itemView.load_failed_layout.visibility = View.GONE
                itemView.tv_load_end.visibility = View.GONE
                itemClickable(false)
            }
            NetworkState.LOADED -> {
                itemView.visibility = View.VISIBLE
                itemView.loading_layout.visibility = View.GONE
                itemView.load_failed_layout.visibility = View.GONE
                itemView.tv_load_end.visibility = View.VISIBLE
                itemClickable(false)
            }
            NetworkState.FAILED -> {
                itemView.visibility = View.VISIBLE
                itemView.loading_layout.visibility = View.GONE
                itemView.load_failed_layout.visibility = View.VISIBLE
                itemView.tv_load_end.visibility = View.GONE
                itemClickable(true)
            }
        }
    }

    private fun itemClickable(clickable: Boolean) {
        if (clickable) {
            itemView.isClickable = true
            itemView.isFocusable = true
        } else {
            itemView.isClickable = false
            itemView.isFocusable = false
        }
    }

    companion object {

        fun create(parent: ViewGroup, retryCallback: () -> Unit): PagingStateHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_loading_more, parent, false)
            return PagingStateHolder(view, retryCallback)
        }
    }

}