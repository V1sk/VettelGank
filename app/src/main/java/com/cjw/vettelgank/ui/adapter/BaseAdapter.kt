package com.cjw.vettelgank.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cjw.vettelgank.ui.adapter.holder.BaseViewHolder
import com.cjw.vettelgank.ui.adapter.holder.LoadingHolder


abstract class BaseAdapter<T>(
    private val dataList: MutableList<T> = mutableListOf(),
    private val layoutResId: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_LOAD_MORE = 1
        const val VIEW_TYPE_DATA = 2
    }

    var loadingStatus: Int = LoadingHolder.STATUS_HIDDEN
        set(value) {
            if (value != field) {
                field = value
                notifyDataSetChanged()
            }
        }

    private var loadingMore = false
    private var layoutManager: RecyclerView.LayoutManager? = null

    private fun footerSize(): Int {
        if (loadingStatus == LoadingHolder.STATUS_HIDDEN)
            return 0
        return when (layoutManager) {
            null -> 0
            is StaggeredGridLayoutManager -> 1
            else -> 1
        }
    }

    fun loadMoreCompleted() {
        loadingMore = false
    }

    fun replaceItems(list: MutableList<T>) {
        this.dataList.clear()
        this.dataList.addAll(list)
        notifyDataSetChanged()
    }

    var loadMoreListener: LoadMoreListener? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            (layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (getItemViewType(position)) {
                        VIEW_TYPE_LOAD_MORE -> (layoutManager as GridLayoutManager).spanCount
                        else -> 1
                    }
                }
            }
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                shouldLoadMore(recyclerView, !loadingMore && newState == RecyclerView.SCROLL_STATE_IDLE)

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                shouldLoadMore(recyclerView, !loadingMore, dx, dy)
            }
        })
    }

    private fun shouldLoadMore(recyclerView: RecyclerView, shouldLoadMore: Boolean, dx: Int = 0, dy: Int = 0) {
        loadMoreListener ?: return
        val layoutManager = recyclerView.layoutManager ?: return

        when (layoutManager) {
            is LinearLayoutManager -> {
                val reachBottom =
                    layoutManager.findLastCompletelyVisibleItemPosition() >= (layoutManager.itemCount - 1)
                if (reachBottom && shouldLoadMore) {
                    loadMore()
                }
            }
            is GridLayoutManager -> {
                val reachBottom =
                    layoutManager.findLastCompletelyVisibleItemPosition() >= (layoutManager.itemCount - 1)
                if (reachBottom && shouldLoadMore) {
                    loadMore()
                }
            }
            is StaggeredGridLayoutManager -> {
                val positions: IntArray? = null
                val into = layoutManager.findLastCompletelyVisibleItemPositions(positions)
                val lastPosition = Math.max(into[0], into[1])

                if (shouldLoadMore && dy > 0 && layoutManager.getItemCount() - lastPosition <= 3) {
                    //load more
                    loadMore()

                }
            }
        }
    }

    private fun loadMore() {
        loadingMore = true
        loadingStatus = LoadingHolder.STATUS_LOADING
        loadMoreListener?.loadMore()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LOAD_MORE -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(LoadingHolder.LAYOUT_ID, parent, false)
                LoadingHolder(itemView).also {
                    it.failedRetryClickListener = this.loadMoreListener
                    it.status = this.loadingStatus
                }
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(layoutResId, parent, false)
                BaseViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BaseViewHolder -> {
                render(holder.itemView, dataList[position])
            }
            is LoadingHolder -> {
                holder.status = loadingStatus
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position < dataList.size) {
            return VIEW_TYPE_DATA
        }
        return VIEW_TYPE_LOAD_MORE
    }

    override fun getItemCount(): Int {
        return dataList.size + footerSize()
    }

    abstract fun render(itemView: View, data: T)

}