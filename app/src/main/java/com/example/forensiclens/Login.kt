package com.example.forensiclens

import android.content.Intent
import android.os.Bundle

import android.view.View
import androidx.activity.ComponentActivity

class Login: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
    }

    fun register(view: View) {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }

}