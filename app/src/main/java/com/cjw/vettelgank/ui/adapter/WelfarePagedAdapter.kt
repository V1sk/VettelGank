package com.cjw.vettelgank.ui.adapter

import android.text.TextUtils
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cjw.vettelgank.R
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.data.paging.NetworkState
import com.cjw.vettelgank.ui.adapter.holder.PagingStateHolder
import com.cjw.vettelgank.ui.adapter.holder.WelfareHolder

class WelfarePagedAdapter(private val retryCallback: () -> Unit) :
    PagedListAdapter<Gank, RecyclerView.ViewHolder>(GANK_COMPARATOR) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.layout_loading_more -> {
                PagingStateHolder.create(parent, retryCallback)
            }
            R.layout.recycler_item_welfare -> {
                WelfareHolder.create(parent)
            }
            else -> {
                throw IllegalArgumentException("unknown view type: $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.layout_loading_more -> {
                (holder as PagingStateHolder).status = networkState
            }
            R.layout.recycler_item_welfare -> {
                (holder as WelfareHolder).bind(getItem(position)!!)
            }
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.layout_loading_more
        } else {
            R.layout.recycler_item_welfare
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    companion object {
        val GANK_COMPARATOR = object : DiffUtil.ItemCallback<Gank>() {
            override fun areItemsTheSame(oldItem: Gank, newItem: Gank): Boolean {
                return TextUtils.equals(oldItem._id, newItem._id)
            }

            override fun areContentsTheSame(oldItem: Gank, newItem: Gank): Boolean {
                return TextUtils.equals(oldItem.url, newItem.url)
            }

        }
    }

}