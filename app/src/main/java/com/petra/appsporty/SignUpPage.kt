package com.petra.appsporty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        //foto detault anton dulu ya wkwkw

        val _edtNameSign = findViewById<EditText>(R.id.edtNameSign)
        val _edtPhoneSign = findViewById<EditText>(R.id.edtPhoneSign)
        val _edtEmailSign = findViewById<EditText>(R.id.edtEmailSign)
        val _edtPassSign = findViewById<EditText>(R.id.edtPassSign)
        val _edtConfirmPassSign = findViewById<EditText>(R.id.edtConfirmPassSign)


        val btnSign = findViewById<Button>(R.id.btnSignup)
        btnSign.setOnClickListener{
            // cek passwrodnya sama atau tidak
            if(_edtPassSign.text == _edtConfirmPassSign.text){

                //simpan di roomDB/firebase
                val intentWithData = Intent(this@SignUpPage,MainActivity::class.java)
                startActivity(intentWithData)
            } else{
                Toast.makeText(
                    this@SignUpPage,
                    "PASSWORD NOT MATCH",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}