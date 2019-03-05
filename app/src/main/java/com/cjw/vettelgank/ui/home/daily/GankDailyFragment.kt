package com.cjw.vettelgank.ui.home.daily


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cjw.vettelgank.R
import com.cjw.vettelgank.data.GankDailyData
import com.cjw.vettelgank.data.ui.GankItem
import com.cjw.vettelgank.ui.adapter.GankDailyAdapter
import kotlinx.android.synthetic.main.fragment_gank.*

class GankDailyFragment : Fragment(), GankDailyContract.View {

    override lateinit var presenter: GankDailyContract.Presenter

    private val gankItemList: MutableList<GankItem> = mutableListOf()
    private lateinit var gankDailyAdapter: GankDailyAdapter

//    lateinit var imageLoadedCallback: MainActivity.ImageLoaderCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_gank_daily.layoutManager = LinearLayoutManager(activity)
        rv_gank_daily.setHasFixedSize(true)
        rv_gank_daily.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        gankDailyAdapter = GankDailyAdapter(gankItemList)
        rv_gank_daily.adapter = gankDailyAdapter
        swipe_refresh_layout.setOnRefreshListener {
            refreshData()
        }
        rv_gank_daily.post {
            swipe_refresh_layout.isRefreshing = true
            refreshData()
        }
    }

    private fun refreshData() {
        presenter.start()
    }

    override fun showGankDaily(gankDailyData: GankDailyData) {
        setLoadingIndicator(false)
        this.gankItemList.clear()
        this.gankItemList.addAll(gankDailyData.toGankUIItem())
        gankDailyAdapter.notifyDataSetChanged()
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (rv_gank_daily == null)
            return
        if (active) {
            rv_gank_daily.post {
                swipe_refresh_layout.isRefreshing = true
                refreshData()
            }
        } else {
            rv_gank_daily.post {
                swipe_refresh_layout.isRefreshing = false
            }
        }
    }

    override fun showLoadingGankError() {
        setLoadingIndicator(false)
        Toast.makeText(context, "showLoadingGankError", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "GankDailyFragment"
        @JvmStatic
        fun newInstance() = GankDailyFragment()
    }
}
