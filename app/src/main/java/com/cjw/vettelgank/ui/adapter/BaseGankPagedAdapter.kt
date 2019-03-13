package com.cjw.vettelgank.ui.adapter

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cjw.vettelgank.R
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.data.paging.NetworkState
import com.cjw.vettelgank.ui.adapter.holder.BaseViewHolder
import com.cjw.vettelgank.ui.adapter.holder.PagingStateHolder

abstract class BaseGankPagedAdapter(
    private val layoutResId: Int,
    private val retryCallback: () -> Unit
) :
    PagedListAdapter<Gank, RecyclerView.ViewHolder>(GANK_COMPARATOR) {

    abstract fun render(itemView: View, data: Gank?)

    private var networkState: NetworkState? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            (layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (getItemViewType(position)) {
                        R.layout.layout_loading_more -> (layoutManager as GridLayoutManager).spanCount
                        else -> 1
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BaseAdapter.VIEW_TYPE_LOAD_MORE -> {
                PagingStateHolder.create(parent, retryCallback)
            }
            else -> {
                BaseViewHolder.create(parent, layoutResId)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BaseViewHolder -> {
                render(holder.itemView, getItem(position))
            }
            is PagingStateHolder -> {
                holder.status = networkState
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            VIEW_TYPE_LOAD_MORE
        } else {
            VIEW_TYPE_DATA
        }
    }

    companion object {

        const val VIEW_TYPE_LOAD_MORE = 1
        const val VIEW_TYPE_DATA = 2

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