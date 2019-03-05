package com.cjw.vettelgank.ui.home.about


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cjw.vettelgank.R
import com.cjw.vettelgank.ext.spanClick
import com.cjw.vettelgank.ui.common.WebActivity
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_about_app.spanClick {
            if (context == null)
                return@spanClick
            WebActivity.start(context!!, it)
        }
        tv_open_source.spanClick {
            if (context == null)
                return@spanClick
            WebActivity.start(context!!, it)
        }
    }

    companion object {

        const val TAG = "AboutFragment"

        @JvmStatic
        fun newInstance() = AboutFragment()
    }
}
