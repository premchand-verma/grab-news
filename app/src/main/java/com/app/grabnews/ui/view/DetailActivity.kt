package com.app.grabnews.ui.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.app.grabnews.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import weather.android.com.util.Constants

class DetailActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setupToolbar()

        setupWebview(intent.getStringExtra(Constants.NEWS_URL));
    }

    private fun setupToolbar() {
        toolbar.tv_title.text = getString(R.string.detail)
        setSupportActionBar(toolbar.toolbar_header)
        toolbar.act_iv_back.visibility = View.VISIBLE
        toolbar.act_iv_back.setOnClickListener({ view -> finish() })
    }

    private fun setupWebview(url: String?) {
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
        webView.webViewClient = MyWebViewClient()
        val webSettings = webView.settings
        webSettings.domStorageEnabled = true
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            request: WebResourceRequest
        ): Boolean {
            return false
        }

        override fun onPageStarted(
            view: WebView,
            url: String,
            favicon: Bitmap?
        ) {
            //To handle event on Page Started
        }

        override fun onPageFinished(
            view: WebView,
            url: String
        ) {
            //To handle event on Page Finished
            progress_bar.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return false
    }
}