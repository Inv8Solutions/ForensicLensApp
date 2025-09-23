package com.example.forensiclens

import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import com.example.forensiclens.utils.LoaderHelper


class Dashboard : ComponentActivity() {

    private val homeUrl = "https://inv8solutions.github.io/ForensicLens/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dashboard)

        val webView = findViewById<WebView>(R.id.main_webview)

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

        webView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                LoaderHelper.showLoader(this@Dashboard,"Loading Dashboard...")
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                LoaderHelper.hideLoader()
            }
            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                LoaderHelper.hideLoader()
            }
        }

        webView.loadUrl(homeUrl)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else{
                    val builder = AlertDialog.Builder(this@Dashboard)
                    builder.setTitle("Exit")
                    builder.setMessage("Are you sure you want to Log out?")
                    builder.setPositiveButton("Yes") { _, _ ->
                        finish()
                    }
                    builder.setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }
            }

        })
    }

}