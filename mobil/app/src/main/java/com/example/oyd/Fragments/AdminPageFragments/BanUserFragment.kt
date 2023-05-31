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
import com.example.oyd.databinding.FragmentBanUserBinding
import android.widget.TextView
import android.widget.Toast
import com.example.oyd.R
import com.example.oyd.Users.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BanUserFragment : Fragment() {

    private var _binding: FragmentBanUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var banUserBtn: Button
    private var banCriteria: String = ""
    private lateinit var banValue: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBanUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        banUserBtn = binding.banUserBtn
        setUpAutoCompleteTextViews()

        banUserBtn.setOnClickListener {
            banValue = binding.userBanLayoutEditText.text.toString().trim()
            binding.userBanLayout.hint = "User email or user full name"

            if (validateInput(banCriteria, banValue)) {
                if (banCriteria == "E-mail") {
                    searchAndBanUserByEmail(banValue)
                } else {
                    searchAndBanUserByName(banValue)
                }
            }
            resetInputFields()
        }
    }

    private fun setUpAutoCompleteTextViews() {
        val types = listOf<String>("Name and surname","E-mail")
        val autoComplete: AutoCompleteTextView = binding.autoCompleteFillType
        setUpAdapterAndListener(autoComplete, types) { type ->
            banCriteria = type
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

    private fun searchAndBanUserByEmail(email: String) {
        CoroutineScope(Dispatchers.Main).launch {
            // Student Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiBanStudentByEmail(email) }
                if (response.isSuccessful) {
                    val student: Student? = response.body()
                    if (student?.banned.equals("No")) {
                        Toast.makeText(requireContext(), "Student with email: $email banned successfully", Toast.LENGTH_LONG).show()
                        showSuccessDialog()
                    }
                    else {
                        Toast.makeText(requireContext(), "Student with email: $email has already banned", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Student with email: $email can not be found", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun searchAndBanUserByName(fullname: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val parts = fullname.split(" ")
            val surname = parts.last()
            val name = parts.dropLast(1).joinToString(" ")

            // Student Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiBanStudentByName(name, surname) }
                if (response.isSuccessful) {
                    val student: Student? = response.body()
                    if (student?.banned.equals("No")) {
                        Toast.makeText(requireContext(), "Student with name: $fullname banned successfully", Toast.LENGTH_LONG).show()
                        showSuccessDialog()
                    }
                    else {
                        Toast.makeText(requireContext(), "Student with name: $fullname has already banned", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Student with name: $fullname can not be found", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateInput(criteria: String, value: String): Boolean {
        var isValid = true

        if (criteria.isBlank()) {
            // Ban criteria not selected
            Toast.makeText(requireContext(), "Choose one criteria to ban a user", Toast.LENGTH_LONG).show()
            isValid = false
        }

        if (value.isEmpty()) {
            // Ban value is empty
            Toast.makeText(requireContext(), "Fill the blank space", Toast.LENGTH_LONG).show()
            isValid = false
        }

        if (criteria == "E-mail" && !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            Toast.makeText(requireContext(), "Please enter a valid email", Toast.LENGTH_LONG).show()
            isValid = false
        }

        if (criteria == "Name and surname" && value.split(" ").size <= 1) {
            Toast.makeText(requireContext(), "Please enter a valid user name and surname", Toast.LENGTH_LONG).show()
            isValid = false
        }

        return isValid
    }

    private fun resetInputFields() {
        binding.userBanLayoutEditText.setText("")
        binding.autoCompleteFillType.setText(null)
        binding.autoCompleteFillType.isFocusable = false
    }

    private fun showSuccessDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.course_creation_successful_dialog, null)
        val myDialog = this.context?.let { it1 -> Dialog(it1) }
        myDialog?.setContentView(dialogBinding)
        myDialog?.setCancelable(true)
        var textView=myDialog?.findViewById<TextView>(R.id.successMessage)
        textView?.setText("User is banned successfully")
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
