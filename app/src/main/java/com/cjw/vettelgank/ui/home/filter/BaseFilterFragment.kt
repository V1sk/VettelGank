package com.cjw.vettelgank.ui.home.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.cjw.vettelgank.data.paging.NetworkState
import com.cjw.vettelgank.databinding.FragmentGankFilterBinding
import com.cjw.vettelgank.ui.adapter.BaseGankPagedAdapter
import com.cjw.vettelgank.ui.home.MainActivity

// 按标签过滤的基础类
abstract class BaseFilterFragment : Fragment() {

    protected lateinit var viewBinding: FragmentGankFilterBinding

    protected abstract fun getLayoutManager(): RecyclerView.LayoutManager
    protected abstract fun getItemDecoration(): RecyclerView.ItemDecoration?
    protected abstract fun getAdapter(): BaseGankPagedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentGankFilterBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as MainActivity).obtainGankFilterViewModel()
        }
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding.lifecycleOwner = viewLifecycleOwner

        // init recycler view
        viewBinding.rvGankFilter.layoutManager = getLayoutManager()
        getItemDecoration()?.also {
            viewBinding.rvGankFilter.addItemDecoration(it)
        }
        val adapter = getAdapter()
        viewBinding.rvGankFilter.adapter = adapter

        // add observer
        viewBinding.viewModel?.gankList?.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewBinding.viewModel?.refreshState?.observe(viewLifecycleOwner, Observer {
            viewBinding.rvGankFilter.post {
                viewBinding.swipeRefreshLayout.isRefreshing = it == NetworkState.LOADING
            }
        })

        viewBinding.viewModel?.networkState?.observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
        })
    }

}