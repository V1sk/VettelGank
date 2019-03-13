package com.cjw.vettelgank.ui.home.filter


import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cjw.vettelgank.ui.adapter.BaseGankPagedAdapter
import com.cjw.vettelgank.ui.adapter.WelfarePagedAdapter

// 福利
class WelfareFragment : BaseFilterFragment() {
    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(activity, 2)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    override fun getAdapter(): BaseGankPagedAdapter {
        return WelfarePagedAdapter {
            viewBinding.viewModel?.retry()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WelfareFragment()
    }
}
