package com.cjw.vettelgank.ui.home.filter


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.cjw.vettelgank.databinding.FragmentWelfareBinding
import com.cjw.vettelgank.ui.adapter.LoadMoreListener
import com.cjw.vettelgank.ui.adapter.WelfareAdapter
import com.cjw.vettelgank.ui.home.MainActivity


class WelfareFragment : Fragment() {

    private lateinit var viewBinding: FragmentWelfareBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentWelfareBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as MainActivity).obtainGankFilterViewModel()
        }
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding.lifecycleOwner = viewLifecycleOwner

        viewBinding.rvWelfare.layoutManager = GridLayoutManager(activity, 2)
        viewBinding.rvWelfare.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        val adapter = WelfareAdapter(mutableListOf())
        viewBinding.rvWelfare.adapter = adapter
        adapter.loadMoreListener = object : LoadMoreListener {
            override fun loadMore() {
                viewBinding.viewModel?.loadMore()
            }
        }

        val filter = viewBinding.viewModel?.currentFiltering

        viewBinding.viewModel?.data?.observe(viewLifecycleOwner, Observer {
            if (it != null && it[filter] != null)
                adapter.replaceItems(it[filter]!!)
        })

        viewBinding.viewModel?.loadMoreState?.observe(viewLifecycleOwner, Observer {
            adapter.loadMoreCompleted()
            if (it != null && it[filter] != null)
                adapter.loadingStatus = it[filter]!!
        })

        val data = viewBinding.viewModel?.data?.value
        if (data == null || data[filter].isNullOrEmpty()) {
            //delay for animation
            viewBinding.rvWelfare.postDelayed({
                viewBinding.viewModel?.refresh()
            }, 200)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WelfareFragment()
    }
}
