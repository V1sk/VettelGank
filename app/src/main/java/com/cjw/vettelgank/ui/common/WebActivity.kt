package com.cjw.vettelgank.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cjw.vettelgank.R
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity(), View.OnClickListener {

    companion object {

        private const val EXTRA_URL = "url"

        @JvmStatic
        fun start(context: Context, url: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(EXTRA_URL, url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        if (!intent.hasExtra(EXTRA_URL)) {
            Toast.makeText(this, R.string.data_error, Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        val url = intent.getStringExtra(EXTRA_URL)
        setUpWebView(web_view)
        web_view.loadUrl(url)

        btn_refresh.setOnClickListener(this)
        btn_back.setOnClickListener(this)
        btn_close.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                back()
            }
            R.id.btn_close -> {
                finish()
            }
            R.id.btn_refresh -> {
                web_view.reload()
            }
        }
    }

    override fun onBackPressed() {
        back()
    }

    private fun back() {
        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView(webView: WebView) {
        // Settings
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        // 启用地理定位
        webSettings.setGeolocationEnabled(true)
        webSettings.allowContentAccess = true
        webSettings.setAppCacheEnabled(true)
        webSettings.allowFileAccess = true
        webSettings.allowFileAccessFromFileURLs = true
        webSettings.databaseEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.loadsImagesAutomatically = true

        webView.webViewClient = WebClient()
        webView.webChromeClient = ChromeClient()
    }

    private inner class WebClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            btn_refresh.visibility = View.GONE
            pb_loading.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            btn_refresh.visibility = View.VISIBLE
            pb_loading.visibility = View.GONE
        }

    }

    private inner class ChromeClient : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            tv_title.text = title
        }

        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            if (showDialog(view, message, result)) return true
            return super.onJsAlert(view, url, message, result)
        }

        override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            if (showDialog(view, message, result)) return true
            return super.onJsConfirm(view, url, message, result)
        }

        override fun onJsPrompt(
            view: WebView?,
            url: String?,
            message: String?,
            defaultValue: String?,
            result: JsPromptResult?
        ): Boolean {
            if (showDialog(view, message, result)) return true
            return super.onJsPrompt(view, url, message, defaultValue, result)
        }

        private fun showDialog(
            view: WebView?,
            message: String?,
            result: JsResult?
        ): Boolean {
            if (view?.context == null)
                return true
            AlertDialog.Builder(view.context)
                .setMessage(message)
                .setPositiveButton(
                    R.string.dialog_ok
                ) { _, _ -> result?.confirm() }
                .setNegativeButton(R.string.dialog_cancel) { _, _ -> result?.cancel() }
                .create()
                .show()
            return false
        }
    }


}
