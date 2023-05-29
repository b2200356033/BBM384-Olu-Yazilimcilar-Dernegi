package com.example.oyd.Fragments.AdminPageFragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.oyd.API.RetrofitClient
import com.example.oyd.R
import com.example.oyd.Users.Admin
import com.example.oyd.Users.DepartmentManager
import com.example.oyd.Users.Instructor
import com.example.oyd.Users.Student
import com.example.oyd.databinding.FragmentManagePasswordsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManagePasswordsFragment : Fragment() {

    private var _binding: FragmentManagePasswordsBinding? = null
    private val binding get() = _binding!!

    private lateinit var email: String
    private lateinit var oldPassword: String
    private lateinit var newPassword: String
    private lateinit var applyChangesBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagePasswordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyChangesBtn = binding.applyChangesBtn

        applyChangesBtn.setOnClickListener {

            email = binding.userEmailLayoutEditText.text.toString().trim()
            oldPassword = binding.userOldPasswordLayoutEditText.text.toString().trim()
            newPassword = binding.userNewPasswordLayoutEditText.text.toString().trim()

            binding.userEmail.hint = "Email address"
            binding.userOldPassword.hint = "Old password"
            binding.userNewPassword.hint = "New password"

            if (validateInput(email, oldPassword, newPassword)) {
                changePassword(email, oldPassword, newPassword)
            }
            resetInputFields()
        }
    }

    private fun changePassword(email: String, oldPassword: String, newPassword: String) {
        CoroutineScope(Dispatchers.Main).launch {

            var userFound = false

            // Student Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiManagePasswordStudent(email, oldPassword, newPassword) }
                if (response.isSuccessful) {
                    userFound= true
                    val user: Student?  = response.body()
                    if (user?.password.equals(oldPassword)) {
                        Toast.makeText(requireContext(), "Student password is not correct", Toast.LENGTH_LONG).show()
                    }
                    else if (user?.password.equals(newPassword)) {
                        showSuccessDialog()
                        Toast.makeText(requireContext(), "Student password changed successfully", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                // Handle exception
            }

            // Department Manager Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiManagePasswordDepartmentManager(email, oldPassword, newPassword) }
                if (response.isSuccessful) {
                    userFound= true
                    val user: DepartmentManager?  = response.body()
                    if (user?.password.equals(oldPassword)) {
                        Toast.makeText(requireContext(), "Department Manager password is not correct", Toast.LENGTH_LONG).show()
                    }
                    else if (user?.password.equals(newPassword)) {
                        showSuccessDialog()
                        Toast.makeText(requireContext(), "Department Manager password changed successfully", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                // Handle exception
            }

            // Admin Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiManagePasswordAdmin(email, oldPassword, newPassword) }
                if (response.isSuccessful) {
                    userFound= true
                    val user: Admin?  = response.body()
                    if (user?.password.equals(oldPassword)) {
                        Toast.makeText(requireContext(), "Admin password is not correct", Toast.LENGTH_LONG).show()
                    }
                    else if (user?.password.equals(newPassword)) {
                        showSuccessDialog()
                        Toast.makeText(requireContext(), "Admin password changed successfully", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                // Handle exception
            }

            // Instructor Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiManagePasswordInstructor(email, oldPassword, newPassword) }
                if (response.isSuccessful) {
                    userFound= true
                    val user: Instructor?  = response.body()
                    if (user?.password.equals(oldPassword)) {
                        Toast.makeText(requireContext(), "Instructor password is not correct", Toast.LENGTH_LONG).show()
                    }
                    else if (user?.password.equals(newPassword)) {
                        showSuccessDialog()
                        Toast.makeText(requireContext(), "Instructor password changed successfully", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                // Handle exception
            }

            if (!userFound) {
                Toast.makeText(requireContext(), "User with email: $email not found", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateInput(email: String, oldPassword: String, newPassword: String): Boolean {

        var isValid = true

        if (email.isEmpty()) {
            binding.userEmail.error = "Please enter an email address"
            isValid = false

        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.userEmail.error ="Please enter a valid email"
            isValid = false

        } else if (oldPassword.isEmpty()) {
            binding.userOldPassword.error = "Please enter the old password"
            isValid = false

        } else if (newPassword.isEmpty()) {
            binding.userNewPassword.error = "Please enter the new password"
            isValid = false

        } else if (oldPassword.length < 8) {
            binding.userOldPassword.error ="Old password must be 8 digits"
            isValid = false

        } else if (newPassword.length < 8) {
            binding.userNewPassword.error ="New password must be 8 digits"
            isValid = false
        } else if (newPassword.equals(oldPassword)) {
            binding.userNewPassword.error ="Passwords should be different"
            binding.userOldPassword.error ="Passwords should be different"
            isValid = false
        }
        return isValid
    }

    private fun resetInputFields() {
        binding.userEmailLayoutEditText.setText("")
        binding.userOldPasswordLayoutEditText.setText("")
        binding.userNewPasswordLayoutEditText.setText("")
    }

    private fun showSuccessDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.course_creation_successful_dialog, null)
        val myDialog = this.context?.let { it1 -> Dialog(it1) }
        myDialog?.setContentView(dialogBinding)
        myDialog?.setCancelable(true)
        var textView=myDialog?.findViewById<TextView>(R.id.successMessage)
        textView?.setText("Password has been changed successfully")
        myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog?.show()

        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                myDialog?.dismiss()
            }

        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
