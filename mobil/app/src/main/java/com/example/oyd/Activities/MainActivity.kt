package com.example.oyd.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.oyd.databinding.ActivityMainBinding

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
    /*
        //////// THIS CODE WILL RUN AFTER SPRING LOGIN API IS READY ////////
        val apiService = RetrofitClient.instance
        val call = apiService.loginUser(LoginRequest(username, password))

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Handle successful login, e.g., navigate to the ProfilePage
                    startActivity(Intent(this@MainActivity, ProfilePage::class.java))
                } else {
                    // Handle unsuccessful login, e.g., show an error message
                    Toast.makeText(this@MainActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle network error or other unexpected issues
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

    */
    }
}
// Handle forgot password
