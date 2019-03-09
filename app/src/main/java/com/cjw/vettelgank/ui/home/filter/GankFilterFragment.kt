package com.cjw.vettelgank.ui.home.filter


import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.ui.adapter.BaseAdapter
import com.cjw.vettelgank.ui.adapter.GankFilterAdapter

// 按标签过滤
class GankFilterFragment : BaseFilterFragment() {
    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activity)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
    }

    override fun getAdapter(): BaseAdapter<Gank> {
        return GankFilterAdapter(mutableListOf())
    }

    companion object {
        @JvmStatic
        fun newInstance() = GankFilterFragment()
    }
}
