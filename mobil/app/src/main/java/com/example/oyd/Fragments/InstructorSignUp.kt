package com.example.oyd.Fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.oyd.API.RetrofitClient
import com.example.oyd.API.SignupRequest
import com.example.oyd.R
import com.example.oyd.Users.Instructor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InstructorSignUp : Fragment(R.layout.instructor_signup) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameInput = view.findViewById<EditText>(R.id.edit_name)
        val surnameInput = view.findViewById<EditText>(R.id.edit_surname)
        val emailInput = view.findViewById<EditText>(R.id.edit_email)
        val passwordInput = view.findViewById<EditText>(R.id.edit_password)
        val signupButton = view.findViewById<View>(R.id.signup_button)

        signupButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val surname = surnameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(activity, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val signupRequest = SignupRequest(
                name = name,
                surname = surname,
                email = email,
                password = password,
                photo = null
            )

            RetrofitClient.instance.signupInstructor(signupRequest)
                .enqueue(object : Callback<Instructor> {
                    override fun onResponse(call: Call<Instructor>, response: Response<Instructor>) {
                        if(response.isSuccessful) {
                            Toast.makeText(activity, "Signup Successful", Toast.LENGTH_SHORT).show()
                        } else if(response.code() == 409) {
                            Toast.makeText(activity, "User already exists with this email", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, "Signup Failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Instructor>, t: Throwable) {
                        Toast.makeText(activity, "Signup Failed", Toast.LENGTH_SHORT).show()
                    }
                })

        }
    }
}
