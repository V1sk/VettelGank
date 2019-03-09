package com.cjw.vettelgank.ui.home.filter


import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.ui.adapter.BaseAdapter
import com.cjw.vettelgank.ui.adapter.WelfareAdapter

// 福利
class WelfareFragment : BaseFilterFragment() {
    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(activity, 2)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    override fun getAdapter(): BaseAdapter<Gank> {
        return WelfareAdapter(mutableListOf())
    }

    companion object {
        @JvmStatic
        fun newInstance() = WelfareFragment()
    }
}
