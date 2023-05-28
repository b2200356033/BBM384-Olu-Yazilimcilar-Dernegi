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
import com.example.oyd.databinding.FragmentManageEmailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManageEmailsFragment : Fragment() {

    private var _binding: FragmentManageEmailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var oldEmail: String
    private lateinit var newEmail: String
    private lateinit var applyChangesBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageEmailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyChangesBtn = binding.applyChangesBtn

        applyChangesBtn.setOnClickListener {

            oldEmail = binding.userOldEmailLayoutEditText.text.toString().trim()
            newEmail = binding.userNewEmailLayoutEditText.text.toString().trim()

            binding.userNewEmail.hint = "New mail address"
            binding.userOldEmail.hint = "Old mail address"

            if (validateInput(oldEmail, newEmail)) {
                changeEmailAddress(oldEmail, newEmail)
            }
            resetInputFields()
        }
    }

    private fun changeEmailAddress(oldEmail: String, newEmail: String) {
        CoroutineScope(Dispatchers.Main).launch {

            var studentFound = false
            var adminFound = false
            var instructorFound = false
            var departmentManagerFound = false


            // Student Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiManageEmailStudent(oldEmail, newEmail) }
                if (response.isSuccessful) {
                    val student: Student? = response.body()
                    studentFound = true
                    if (student?.email.equals(newEmail)) {
                        Toast.makeText(requireContext(), "Student with email: $oldEmail changed successfully", Toast.LENGTH_LONG).show()
                        showSuccessDialog()
                    }
                    else {
                        Toast.makeText(requireContext(), "New email address is already taken", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                //USER NOT FOUND
            }

            // Department Manager Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiManageEmailDepartmentManager(oldEmail, newEmail) }
                if (response.isSuccessful) {
                    val dm: DepartmentManager? = response.body()
                    departmentManagerFound = true
                    if (dm?.email.equals(newEmail)) {
                        Toast.makeText(requireContext(), "Department Manager with email: $oldEmail changed successfully", Toast.LENGTH_LONG).show()
                        showSuccessDialog()
                    }
                    else {
                        Toast.makeText(requireContext(), "New email address is already taken", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                //USER NOT FOUND
            }

            // Admin Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiManageEmailAdmin(oldEmail, newEmail) }
                if (response.isSuccessful) {
                    val admin: Admin? = response.body()
                    adminFound = true
                    if (admin?.email.equals(newEmail)) {
                        Toast.makeText(requireContext(), "Admin with email: $oldEmail changed successfully", Toast.LENGTH_LONG).show()
                        showSuccessDialog()
                    }
                    else {
                        Toast.makeText(requireContext(), "New email address is already taken", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                //USER NOT FOUND
            }

            // Instructor Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiManageEmailInstructor(oldEmail, newEmail) }
                if (response.isSuccessful) {
                    val instructor: Instructor? = response.body()
                    instructorFound = true
                    if (instructor?.email.equals(newEmail)) {
                        Toast.makeText(requireContext(), "Instructor with email: $oldEmail changed successfully", Toast.LENGTH_LONG).show()
                        showSuccessDialog()
                    }
                    else {
                        Toast.makeText(requireContext(), "New email address is already taken", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                //USER NOT FOUND
            }

            if (!studentFound && !adminFound && !departmentManagerFound && !instructorFound) {
                Toast.makeText(requireContext(), "User with email: $oldEmail not found", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateInput(oldEmail: String, newEmail: String): Boolean {
        var isValid = true

        if (oldEmail.isEmpty()) {
            Toast.makeText(requireContext(), "Fill the blank space", Toast.LENGTH_LONG).show()
            isValid = false
        }

        else if (newEmail.isEmpty()) {
            Toast.makeText(requireContext(), "Fill the blank space", Toast.LENGTH_LONG).show()
            isValid = false
        }

        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(oldEmail).matches()) {
            Toast.makeText(requireContext(), "Rewrite a valid email address for old email space", Toast.LENGTH_LONG).show()
            isValid = false
        }

        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            Toast.makeText(requireContext(), "Rewrite a valid email address for new email space", Toast.LENGTH_LONG).show()
            isValid = false
        }

        else if (oldEmail.equals(newEmail)) {
            Toast.makeText(requireContext(), "Do not enter same email addresses", Toast.LENGTH_LONG).show()
            isValid = false
        }

        return isValid
    }

    private fun resetInputFields() {
        binding.userNewEmailLayoutEditText.setText("")
        binding.userOldEmailLayoutEditText.setText("")
    }

    private fun showSuccessDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.course_creation_successful_dialog, null)
        val myDialog = this.context?.let { it1 -> Dialog(it1) }
        myDialog?.setContentView(dialogBinding)
        myDialog?.setCancelable(true)
        var textView=myDialog?.findViewById<TextView>(R.id.successMessage)
        textView?.setText("Email address has been changed successfully")
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