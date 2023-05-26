package com.example.oyd.Fragments.AdminPageFragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import com.example.oyd.API.RetrofitClient
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.example.oyd.databinding.FragmentDeleteUserBinding
import android.widget.TextView
import android.widget.Toast
import com.example.oyd.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DeleteUserFragment : Fragment() {

    private var _binding: FragmentDeleteUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var deleteUserBtn: Button
    private var deleteCriteria: String = ""
    private lateinit var deleteValue: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deleteUserBtn = binding.deleteUserBtn
        setUpAutoCompleteTextViews()

        deleteUserBtn.setOnClickListener {
            deleteValue = binding.userDeleteLayoutEditText.text.toString().trim()
            binding.userDeleteLayout.hint = "User e-mail or user fullname"

            if (validateInput(deleteCriteria, deleteValue)) {
                if (deleteCriteria == "E-mail") {
                    searchAndDeleteUserbyEmail(deleteValue)
                }
                else {
                    searchAndDeleteUserbyName(deleteValue)
                }
            }
            resetInputFields()
        }
    }

    private fun setUpAutoCompleteTextViews() {
        val types = listOf<String>("Name and surname","E-mail")
        val autoComplete: AutoCompleteTextView = binding.autoCompleteFillType
        setUpAdapterAndListener(autoComplete, types) { type ->
            deleteCriteria = type
        }
    }

    private fun setUpAdapterAndListener(
        autoCompleteTextView: AutoCompleteTextView,
        items: List<String>,
        onItemSelected: (String) -> Unit
    ) {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, i, _ ->
            onItemSelected(adapterView.getItemAtPosition(i).toString())
        }

    }

    private fun searchAndDeleteUserbyEmail(email: String) {
        CoroutineScope(Dispatchers.Main).launch {
            var foundUser = false

            // Student Response
            try {
                val student = withContext(Dispatchers.IO) { RetrofitClient.instance.apiDeleteStudentByEmail(email) }
                if (student.isSuccessful) {
                    foundUser = true
                    Toast.makeText(requireContext(), "Student with email: $email deleted successfully", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }

            // Department Manager Response
            try {
                val dm = withContext(Dispatchers.IO) { RetrofitClient.instance.apiDeleteDepartmentManagerByEmail(email) }
                if (dm.isSuccessful) {
                    foundUser = true
                    Toast.makeText(requireContext(), "Department Manager with email: $email deleted successfully", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }

            // Admin Response
            try {
                val admin = withContext(Dispatchers.IO) { RetrofitClient.instance.apiDeleteAdminByEmail(email) }
                if (admin.isSuccessful) {
                    foundUser = true
                    Toast.makeText(requireContext(), "Admin with email: $email deleted successfully", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }

            // Instructor Response
            try {
                val instructor = withContext(Dispatchers.IO) { RetrofitClient.instance.apiDeleteInstructorByEmail(email) }
                if (instructor.isSuccessful) {
                    foundUser = true
                    Toast.makeText(requireContext(), "Instructor with email: $email deleted successfully", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }

            // All
            if (!foundUser) {
                Toast.makeText(requireContext(), "User with e-mail: $email can not be found", Toast.LENGTH_LONG).show()
            }

            if (foundUser) {
                showSuccessDialog()
            }
        }
    }

    private fun searchAndDeleteUserbyName(fullname: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val parts = fullname.split(" ")
            val surname = parts.last()
            val name = parts.dropLast(1).joinToString(" ")
            var foundUser = false;

            // Student Response
            try {
                val student = withContext(Dispatchers.IO) { RetrofitClient.instance.apiDeleteStudentByName(name, surname) }
                if (student.isSuccessful) {
                    foundUser = true
                    Toast.makeText(requireContext(), "Student with name: $fullname deleted successfully", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }

            // Department Manager Response
            try {
                val dm = withContext(Dispatchers.IO) { RetrofitClient.instance.apiDeleteDepartmentManagerByName(name, surname) }
                if (dm.isSuccessful) {
                    foundUser = true
                    Toast.makeText(requireContext(), "Department Manager with name: $fullname deleted successfully", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }

            // Admin Response
            try {
                val admin = withContext(Dispatchers.IO) { RetrofitClient.instance.apiDeleteAdminByName(name, surname) }
                if (admin.isSuccessful) {
                    foundUser = true
                    Toast.makeText(requireContext(), "Admin with name: $fullname deleted successfully", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }

            // Instructor Response
            try {
                val instructor = withContext(Dispatchers.IO) { RetrofitClient.instance.apiDeleteInstructorByName(name, surname) }
                if (instructor.isSuccessful) {
                    foundUser = true
                    Toast.makeText(requireContext(), "Instructor with name: $fullname deleted successfully", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }

            // All
            if (!foundUser) {
                Toast.makeText(requireContext(), "User with name: $fullname can not be found", Toast.LENGTH_LONG).show()
            }

            if (foundUser) {
                showSuccessDialog()
            }
        }
    }

    private fun validateInput(criteria: String, value: String): Boolean {
        var isValid = true

        if (criteria.isBlank()) {
            // Delete criteria not selected
            //binding.autoCompleteFillType.error="This field can't be empty"
            Toast.makeText(requireContext(), "Choose one criteria to delete a user", Toast.LENGTH_LONG).show()
            isValid = false
        }

        if (value.isEmpty()) {
            // Delete value is empty
            // Display an error message or handle the case
            //binding.userDeleteLayoutEditText.error="This field can't be empty"
            Toast.makeText(requireContext(), "Fill the blank space", Toast.LENGTH_LONG).show()
            isValid = false
        }

        if (criteria == "E-mail" && !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            //println("Please enter a valid email address")
            //println("current email: $value")
            Toast.makeText(requireContext(), "Please enter a valid email", Toast.LENGTH_LONG).show()
            //binding.userDeleteLayoutEditText.error ="Please enter a valid email"
            isValid = false
        }

        if (criteria == "Name and surname" && value.split(" ").size <= 1) {
            //println("Please enter a valid user name and surname")
            //println("current name and surname: $value")
            Toast.makeText(requireContext(), "Please enter a valid user name and surname", Toast.LENGTH_LONG).show()
            //binding.userDeleteLayoutEditText.error ="Please enter a valid user name and surname"
            isValid = false
        }

        return isValid
    }

    private fun resetInputFields() {
        binding.userDeleteLayoutEditText.setText("")
        binding.autoCompleteFillType.setText(null)
        binding.autoCompleteFillType.isFocusable = false
    }

    private fun showSuccessDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.course_creation_successful_dialog, null)
        val myDialog = this.context?.let { it1 -> Dialog(it1) }
        myDialog?.setContentView(dialogBinding)
        myDialog?.setCancelable(true)
        var textView=myDialog?.findViewById<TextView>(R.id.successMessage)
        textView?.setText("User is deleted successfully")
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