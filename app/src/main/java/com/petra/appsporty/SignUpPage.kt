package com.petra.appsporty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class SignUpPage : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)
        firestore = FirebaseFirestore.getInstance()

        //foto detault anton dulu ya wkwkw
        val _edtNameSign = findViewById<EditText>(R.id.edtNameSign)
        val _edtPhoneSign = findViewById<EditText>(R.id.edtPhoneSign)
        val _edtEmailSign = findViewById<EditText>(R.id.edtEmailSign)
        val _edtPassSign = findViewById<EditText>(R.id.edtPassSign)
        val _edtConfirmPassSign = findViewById<EditText>(R.id.edtConfirmPassSign)
        val _signup = findViewById<TextView>(R.id.tvNotRegistered)

        //jika sudah ada akun dan text ditekan
        _signup.setOnClickListener{
            val intent = Intent(this@SignUpPage, LoginPage::class.java)
            startActivity(intent)
            finish()
        }
        val btnSign = findViewById<Button>(R.id.btnSignup)
        btnSign.setOnClickListener {

            val name = _edtNameSign.text.toString().trim()
            val phone = _edtPhoneSign.text.toString().trim()
            val email = _edtEmailSign.text.toString().trim()
            val password = _edtPassSign.text.toString().trim()
            val age: String? = null
            val gender: String? = null
            val category: String? = null
            val notes: String? = null
            // Cek apakah password sama dengan konfirmasi password
            if (password == _edtConfirmPassSign.text.toString().trim()) {
                // Menyimpan data ke Firestore
                val user = User(
                    name,
                    age.toString(),
                    gender.toString(),
                    phone,
                    email,
                    category.toString(),
                    notes.toString(),
                    password
                )

                // Mengakses collection "users" di Firestore
                firestore.collection("users")
                    .document(name)
                    .set(user)
                    .addOnSuccessListener {
                        Toast.makeText(this@SignUpPage, "Sign up successful", Toast.LENGTH_SHORT).show()
                        // Buka Login Page
                        val intent = Intent(this@SignUpPage, LoginPage::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this@SignUpPage, "Error: $e", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this@SignUpPage, "Password doesn't match", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


