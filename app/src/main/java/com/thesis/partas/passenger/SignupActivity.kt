package com.thesis.partas.passenger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.partas.dbConn.RetrofitHelper
import com.thesis.partas.passenger.api.ApiInterface
import com.thesis.partas.passenger.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var password: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        phone= findViewById(R.id.editTextMobile)
        name = findViewById(R.id.etName)
        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {

                if( name.text.toString().trim().isEmpty()
                    || phone.text.toString().trim().isEmpty()
                    || email.text.toString().trim().isEmpty()
                    || password.text.toString().trim().isEmpty())
                {
                    Toast.makeText(this, "Please Fillup all informatin",Toast.LENGTH_LONG).show()
                }else if (password.text.length<8){
                          password.setText("")
                          password.requestFocus()
                        Toast.makeText(this, "Password Should Be greater than 8 Character",Toast.LENGTH_LONG).show()
                }else {
                    val user  = User(name.text.toString(),email.text.toString(),
                                    phone.text.toString(),password.text.toString())
                     saveData(user)


                }

            }

    }

    private fun saveData(user: User) {
        val userAccount = RetrofitHelper.getClient()?.create(ApiInterface::class.java)
        Log.d("dito na",user.email)
        val call: Call<User>? = userAccount?.saveUser(user)
        call!!.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                val user = response.body()
                if (response.isSuccessful) {
                    Toast.makeText(this@SignupActivity, "Data has been saved",Toast.LENGTH_LONG).show()
                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    startActivity(intent)
                   Log.d("hay naku", response.body().toString())
                }

            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                Toast.makeText(this@SignupActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }



}
