package com.example.forensiclens

import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import com.example.forensiclens.utils.LoaderHelper

class Dashboard : ComponentActivity() {

    private lateinit var webView: WebView
    private val homeUrl = "https://inv8solutions.github.io/ForensicLens/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        setupWebView()
        setupBackHandler()
    }

    private fun setupWebView() {
        webView = findViewById(R.id.main_webview)


        with(webView.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            cacheMode = WebSettings.LOAD_DEFAULT
            useWideViewPort = true
            loadWithOverviewMode = true
        }

        // Handle page events
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                LoaderHelper.showLoader(this@Dashboard, "Loading Dashboard...")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                LoaderHelper.hideLoader()
            }

            @Deprecated("Deprecated in Android Q")
            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                LoaderHelper.hideLoader()
                showErrorDialog(description ?: "Unknown error")
            }
        }


        webView.loadUrl(homeUrl)
    }

    private fun setupBackHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when {
                    webView.canGoBack() -> webView.goBack()
                    else -> showExitDialog()
                }
            }
        })
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this)
            .setTitle("Exit")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage("Failed to load page: $message")
            .setPositiveButton("Retry") { _, _ -> webView.loadUrl(homeUrl) }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroy() {

        webView.apply {
            loadUrl("about:blank")
            clearHistory()
            removeAllViews()
            destroy()
        }
        LoaderHelper.hideLoader()
        super.onDestroy()
    }
}
