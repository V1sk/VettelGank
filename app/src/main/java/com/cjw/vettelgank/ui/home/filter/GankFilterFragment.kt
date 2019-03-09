package com.cjw.vettelgank.ui.home.filter


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cjw.vettelgank.databinding.FragmentGankFilterBinding
import com.cjw.vettelgank.ui.adapter.GankFilterAdapter
import com.cjw.vettelgank.ui.adapter.LoadMoreListener
import com.cjw.vettelgank.ui.home.MainActivity


class GankFilterFragment : Fragment() {

    private lateinit var viewBinding: FragmentGankFilterBinding

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

        viewBinding.rvGankFilter.layoutManager = LinearLayoutManager(activity)
        viewBinding.rvGankFilter.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        val adapter = GankFilterAdapter(mutableListOf())
        viewBinding.rvGankFilter.adapter = adapter
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
            viewBinding.rvGankFilter.postDelayed({
                viewBinding.viewModel?.refresh()
            }, 200)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = GankFilterFragment()
    }
}
