package com.example.forensiclens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.email_reset_input)
        val resetButton = findViewById<Button>(R.id.reset_link_btn)

        resetButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val intent = Intent(this, CheckEmail::class.java)
            intent.putExtra("email", email)
            startActivity(intent)

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Password reset link sent to your email",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, CheckEmail::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Failed to send password reset link",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
        }
    }
}