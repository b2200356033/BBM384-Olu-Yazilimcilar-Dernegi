package com.example.oyd.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.oyd.API.LoginRequest
import com.example.oyd.API.LoginResponse
import com.example.oyd.API.RetrofitClient
import com.example.oyd.databinding.ActivityMainBinding
// import io.jsonwebtoken.Jwts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.loginBtn.setOnClickListener {
            val username = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()

            // Validate username and password using "loginuser" fun. if successful, navigate to the ProfilePage
            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser(username, password)
            } else {
                Toast.makeText(this@MainActivity, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.forgotPassword.setOnClickListener {
            // Handle forgot password
        }

        binding.signUpText.setOnClickListener {
            startActivity(Intent(this@MainActivity, SignUpPage::class.java))
        }
    }


    private fun loginUser(username: String, password: String) {
        // Create a login request object
        val loginRequest = LoginRequest(username, password)

        // Create a call object
        val call = RetrofitClient.instance.loginUser(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("LoginResponse", "Response Code: ${response.code()} and Response Message: ${response.message()}")

                if (response.isSuccessful) {
                    val responseBody = response.body()

                    Log.d("LoginResponse", "Response Body: ${responseBody?.role}")

                    // Save user data in SharedPreferences
                    val sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("id", responseBody?.id.toString())
                        putString("role", responseBody?.role)
                        putString("name", responseBody?.name)
                        putString("surname", responseBody?.surname)
                        putString("email", responseBody?.email)
                        putString("photo", responseBody?.photo)
                        apply()
                    }

                    when (responseBody?.role) {
                        "Admin" -> {
                            // Open admin profile page
                            startActivity(Intent(this@MainActivity, AdminProfilePage::class.java))
                        }
                        "DepartmentManager" -> {
                            // Open department manager profile page
                            startActivity(Intent(this@MainActivity, DepartmentManagerProfilePage::class.java))
                        }
                        "Instructor" -> {
                            // Open instructor profile page
                            startActivity(Intent(this@MainActivity, InstructorProfilePage::class.java))
                        }
                        "Student" -> {
                            // Open student profile page
                            startActivity(Intent(this@MainActivity, StudentProfilePage::class.java))
                        }
                        else -> {
                            // handle case where the user role is not recognized
                            Toast.makeText(applicationContext, "Unrecognized user role", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    // Show an error message
                    Toast.makeText(applicationContext, "Invalid username or password", Toast.LENGTH_LONG).show()
                    Log.e("LoginError", "Response Code: ${response.code()} and Response Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Show an error message
                Log.e("LoginError", "An error occurred: ${t.message}")
                Toast.makeText(applicationContext, "An error occurred: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

}

