package com.example.forensiclens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.forensiclens.utils.LoaderHelper
import android.view.View
import android.widget.Toast

class Login : ComponentActivity() {
    // Firebase inits
    private var auth = FirebaseAuth.getInstance()
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val emailInput = findViewById<EditText>(R.id.login_email)
        val passwordInput = findViewById<EditText>(R.id.login_password)
        val loginBtn = findViewById<Button>(R.id.login_button)

        // Login button click
        loginBtn.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                LoaderHelper.showLoader(this, "Logging in...")
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        checkUser(user.uid, email)
                    } else {
                        LoaderHelper.hideLoader() // Safety
                    }
                } else {
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    LoaderHelper.hideLoader()
                }
            }
    }

    private fun checkUser(uid: String, email: String) {
        db.collection("users").document(uid).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val role = documentSnapshot.getString("role") ?: "user"
                    Toast.makeText(this, "Welcome $email", Toast.LENGTH_SHORT).show()

                    LoaderHelper.hideLoader()

                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                    LoaderHelper.hideLoader()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to check user", Toast.LENGTH_SHORT).show()
                LoaderHelper.hideLoader()
            }
    }

    fun register(view: View) {
        startActivity(Intent(this, Register::class.java))
    }

    fun forgotPassword(view: View) {
        startActivity(Intent(this, ForgotPassword::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        LoaderHelper.hideLoader()
    }
}
