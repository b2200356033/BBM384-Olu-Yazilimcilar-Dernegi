package com.example.oyd.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.oyd.API.LoginRequest
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
        // Check if username is admin
        if (username == "admin") {
            // Open admin profile page
            startActivity(Intent(this@MainActivity, AdminProfilePage::class.java))
        } else if (username == "manager") {
            // Open instructor profile page
            startActivity(Intent(this@MainActivity, DepartmentManagerProfilePage::class.java))

        }else if (username == "instructor") {
            // Open instructor profile page
            startActivity(Intent(this@MainActivity, InstructorProfilePage::class.java))

        } else {
            // Open student profile page
            startActivity(Intent(this@MainActivity, StudentProfilePage::class.java))
        }

    }


    /*
    private fun loginUser(email: String, password: String) {
        val apiService = RetrofitClient.instance
        val call = apiService.loginUser(LoginRequest(email, password))

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    // Handle successful login, e.g., navigate to the ProfilePage
                    val jwt = response.body()
                    if (jwt != null) {
                        val claims = Jwts.parser()
                            .setSigningKey("oydsecret")
                            .parseClaimsJws(jwt)
                            .body
                        val userType = claims["userType"] as String
                        navigateToProfilePage(userType)
                    } else {
                        Toast.makeText(this@MainActivity, "JWT is null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle unsuccessful login, e.g., show an error message
                    Toast.makeText(this@MainActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // Handle network error or other unexpected issues
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToProfilePage(userType: String) {
        val intent = when (userType) {
            "Admin" -> Intent(this@MainActivity, AdminProfilePage::class.java)
            "DepartmentManager" -> Intent(this@MainActivity, DepartmentManagerProfilePage::class.java)
            "Instructor" -> Intent(this@MainActivity, InstructorProfilePage::class.java)
            "Student" -> Intent(this@MainActivity, StudentProfilePage::class.java)
            else -> {
                Toast.makeText(this@MainActivity, "Invalid user type", Toast.LENGTH_SHORT).show()
                return
            }
        }
        startActivity(intent)
    }
    */
}

