package com.thesis.partas.passenger

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.thesis.partas.passenger.model.User
import com.thesis.partas.passenger.ui.login.LoginViewModel




class LoginActivity : AppCompatActivity() {
    private lateinit var txtEmail: TextView
    private lateinit var txtPassword: TextView
    private lateinit var loginButton: Button
    private lateinit var tvSignUp: TextView
    private lateinit var email: String
    private lateinit var password: String





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)
        loginButton = findViewById(R.id.btnRegister)
        txtEmail = findViewById(R.id.etEmail)
        txtPassword = findViewById(R.id.etPassword)
        tvSignUp = findViewById(R.id.tvSignUp)


        loginButton.setOnClickListener {
            email = txtEmail.text.toString()
            password = txtPassword.text.toString()
            loginViewModel.performLogin(email, password)?.observe(this) {
                sentToMain(it)


            }
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        }


    }

    private fun sentToMain(user: User?){
        if(user?.email!=null) {
            Log.d("user details",user?.email.toString())
            val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("email", user?.email)
            editor.putString("name",user?.name)
            editor.apply()
            val email = sharedPreferences.getString("email", "")

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)


        }else{
            Toast.makeText(this@LoginActivity, "Invalid User", Toast.LENGTH_SHORT).show()
        }


    }
}




