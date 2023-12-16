package com.petra.appsporty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginPage : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        mAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        val _edtUsername = findViewById<EditText>(R.id.edtUsernameLogin)
        val _edtPassLogin = findViewById<EditText>(R.id.edtPassLogin)
        val _tvNotRegistered = findViewById<TextView>(R.id.tvNotRegistered)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        _tvNotRegistered.setOnClickListener {
            val intent = Intent(this, SignUpPage::class.java)
            startActivity(intent)
        }
        //bikin if jika email sama passnya benar sesuai roomDB/firebase
        btnLogin.setOnClickListener {
            val name = _edtUsername.text.toString().trim()
            val password = _edtPassLogin.text.toString().trim()

            // Ambil dokumen pengguna berdasarkan email
            firestore.collection("users")
                .whereEqualTo("userName", name)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        // Dokumen ditemukan, periksa password
                        val userDoc = documents.documents[0]
                        val storedPassword = userDoc.getString("userPw")

                        // Bandingkan password
                        if (storedPassword == password) {
                            Toast.makeText(
                                this@LoginPage,
                                "Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Jika password cocok, login berhasil
                            //set ke data username global
                            val main = MainActivity()
                            main.setMyUsername(name)
                            //intent ke mainactivity biasa
                            val intent = Intent(this@LoginPage, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Jika password tidak cocok
                            Toast.makeText(
                                this@LoginPage,
                                "Invalid email or password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Dokumen tidak ditemukan
                        Toast.makeText(this@LoginPage, "User not registered", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                .addOnFailureListener { e ->
                    // Penanganan kegagalan query Firestore
                    Toast.makeText(this@LoginPage, "Failed to query Firestore", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }
}