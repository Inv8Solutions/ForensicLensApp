package com.example.forensiclens

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Register: ComponentActivity(){
    //auth and firestore inits
    private var auth= FirebaseAuth.getInstance()
    private var db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        auth=FirebaseAuth.getInstance()
        db=FirebaseFirestore.getInstance()

        val email_registered=findViewById<EditText>(R.id.registration_email)
        val full_name=findViewById<EditText>(R.id.registration_full_name)
        val password=findViewById<EditText>(R.id.registration_password)
        val confirm_password=findViewById<EditText>(R.id.registration_confirm_pass)
        val register=findViewById<Button>(R.id.sign_up_btn)

        register.setOnClickListener {
            val email=email_registered.text.toString().trim()
            val name=full_name.text.toString().trim()
            val pass=password.text.toString().trim()
            val confirm=confirm_password.text.toString().trim()

            if(email.isEmpty() && name.isEmpty() && pass.isEmpty() && confirm.isEmpty()){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pass!=confirm){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                        val user = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "role" to "user"
                        )
                        db.collection("users").document(userId).set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Registration failed: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()

                    }

                }

        }
    }
}