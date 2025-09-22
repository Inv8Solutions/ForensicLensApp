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

class Login: ComponentActivity() {
    //auth and firestore inits
    private var auth= FirebaseAuth.getInstance()
    private var db= FirebaseFirestore.getInstance()

// end


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        //select inputs logics
         val email=findViewById<EditText>(R.id.login_email)
         val password=findViewById<EditText>(R.id.login_password)
         val login=findViewById<Button>(R.id.login_button)
        //end

        //onclick listener for login
        login.setOnClickListener {
            LoaderHelper.showLoader(this, "Logging in...")
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()

            if(email.isNotEmpty() && password.isNotEmpty()){
                loginUser(email, password)
            } else{
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    //end

    }

    private fun loginUser(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful){
                    val user=auth.currentUser
                    if (user != null) {
                        checkUser(user.uid, email)
                    }
                } else{
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkUser(uid:String, email:String){
        db.collection("users").document(uid).get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val role = documentSnapshot.getString("role") ?: "user"
                Toast.makeText(this, "Welcome $email", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to check user", Toast.LENGTH_SHORT).show()
        }
    }
    fun register(view: View) {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }

}