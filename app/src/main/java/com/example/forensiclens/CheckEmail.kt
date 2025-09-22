package com.example.forensiclens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class CheckEmail: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.check_email)


        val button = findViewById<Button>(R.id.return_login)
        button.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val resend = findViewById<TextView>(R.id.reset_resend_btn)
        resend.setOnClickListener {

        }

    }
}