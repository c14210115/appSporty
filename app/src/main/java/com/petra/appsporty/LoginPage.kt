package com.petra.appsporty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val _edtEmailLopin = findViewById<EditText>(R.id.edtEmailLogin)
        val _edtPassLogin= findViewById<EditText>(R.id.edtPassLogin)


        val btnLogin = findViewById<Button>(R.id.btnLogin)

        //bikin if jika email sama passnya benar sesuai roomDB/firebase
        btnLogin.setOnClickListener{
            val intentWithData = Intent(this@LoginPage,MainActivity::class.java)
            startActivity(intentWithData)

        }

    }
}