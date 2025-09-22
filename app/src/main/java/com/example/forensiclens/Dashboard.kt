package com.example.forensiclens

import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import android.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class Dashboard : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private val homeUrl = "https://inv8solutions.github.io/ForensicLens/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        val webView = findViewById<WebView>(R.id.main_webview)

        auth = FirebaseAuth.getInstance()

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

        webView.webViewClient = WebViewClient()

        webView.loadUrl(homeUrl)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else{
                    val builder = AlertDialog.Builder(this@Dashboard)
                    builder.setTitle("Exit")
                    builder.setMessage("Are you sure you want to exit?")
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