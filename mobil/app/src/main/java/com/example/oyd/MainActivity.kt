package com.example.oyd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.oyd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Create binding so that we can use elements in the xml files such as button, textview by their ids.
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //Login page button assignment
        val loginBtn = binding.loginBtn
        // when login button is clicked
        loginBtn.setOnClickListener(){
            //send login information to database and if correct, load the ProfilePage
            startActivity(Intent(this@MainActivity,ProfilePage::class.java))
        }
        val forgotPassword =binding.forgotPassword
        // when forgot password message is clicked
        forgotPassword.setOnClickListener(){
            //send new password to user email
        }
        val signUpText=binding.signUpText
        //when sign up text is clicked
        signUpText.setOnClickListener(){
            //change activity to SignUpPage
            startActivity(Intent(this@MainActivity,SignUpPage::class.java))
        }
    }
}