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
import android.widget.TextView
import android.widget.Toast
import com.example.oyd.Models.Course
import com.example.oyd.R
import com.example.oyd.Users.Admin
import com.example.oyd.Users.DepartmentManager
import com.example.oyd.Users.Instructor
import com.example.oyd.Users.Student
import com.example.oyd.databinding.FragmentAddNewUserBinding
import com.example.oyd.databinding.FragmentCreateCoursesBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddNewUserFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAddNewUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var addUserBtn: Button
    private lateinit var userName: String
    private lateinit var userSurname: String
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var role:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setUpAutoCompleteTextViews()

        binding.addUserBtn.setOnClickListener {
            if(validateInput(role,userName.trim(),userSurname.trim(),userEmail.trim(),userPassword.trim())){
                if(role.equals("Student")){
                    val student = Student(userName,userSurname,userEmail,userEmail,"")
                    sendStudentToServer(student)
                }
                else if(role.equals("Instructor")){
                    val instructor = Instructor(userName,userSurname,userEmail,userEmail,"")
                    sendInstructorToServer(instructor)
                }
                else if(role.equals("Department Manager")){
                    val dm = DepartmentManager(userName,userSurname,userEmail,userEmail,"")
                    sendDepartmentManagerToServer(dm)
                }
                else{
                    val admin = Admin(userName, userSurname, userEmail, userPassword, photo="")
                    sendAdminToServer(admin)
                }
                //if successful, then create success dialog, else show error, for now, it will be always successful
                //
                // course creation SUCCESSFUL DIALOG CHECK!!!
                //
                showSuccessDialog()
                //to reset the boxes
                resetInputFields()

            }

        }
    }

    private fun initViews() {
        userName = binding.userNameEditText.toString()
        userSurname = binding.userSurnameEditText.toString()
        userEmail = binding.userEmailEditText.toString()
        userPassword = binding.userPasswordEditText.toString()
        addUserBtn = binding.addUserBtn
    }

    private fun setUpAutoCompleteTextViews() {
        val types = listOf<String>("Student","Instructor","Department Manager","Admin")
        val autoComplete:AutoCompleteTextView = binding.autoCompleteRoles
        setUpAdapterAndListener(autoComplete, types) { type ->
            role = type
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

    private fun sendAdminToServer(admin: Admin) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                Toast.makeText(requireContext(),"Admin data " + admin.toString(), Toast.LENGTH_LONG).show()
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apisendAdminToServer(admin) }
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Admin sent successfully: ${admin.toString()}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Response Failed to send course: ${admin.toString()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                //Toast.makeText(requireContext(), "Cant Connect server Failed to send course: ${course.toString()}", Toast.LENGTH_LONG).show()
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sendStudentToServer(student: Student) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                Toast.makeText(requireContext(),"Student data " + student.toString(), Toast.LENGTH_LONG).show()
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apisendStudentToServer(student) }
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Student sent successfully: ${student.toString()}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Response Failed to send course: ${student.toString()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                //Toast.makeText(requireContext(), "Cant Connect server Failed to send course: ${course.toString()}", Toast.LENGTH_LONG).show()
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sendInstructorToServer(instructor: Instructor) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                Toast.makeText(requireContext(),"Instructor data " + instructor.toString(), Toast.LENGTH_LONG).show()
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apisendInstructorToServer(instructor) }
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Instructor sent successfully: ${instructor.toString()}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Response Failed to send course: ${instructor.toString()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                //Toast.makeText(requireContext(), "Cant Connect server Failed to send course: ${course.toString()}", Toast.LENGTH_LONG).show()
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sendDepartmentManagerToServer(dm: DepartmentManager) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                Toast.makeText(requireContext(),"Department Manager data " + dm.toString(), Toast.LENGTH_LONG).show()
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apisendDepartmentManagerToServer(dm) }
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Department Manager sent successfully: ${dm.toString()}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Response Failed to send course: ${dm.toString()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                //Toast.makeText(requireContext(), "Cant Connect server Failed to send course: ${course.toString()}", Toast.LENGTH_LONG).show()
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showSuccessDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.course_creation_successful_dialog, null)
        val myDialog = this.context?.let { it1 -> Dialog(it1) }
        myDialog?.setContentView(dialogBinding)
        myDialog?.setCancelable(true)
        var textView=myDialog?.findViewById<TextView>(R.id.successMessage)
        textView?.setText("User is added successfully")
        myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog?.show()

        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                myDialog?.dismiss()
            }
        }.start()
    }

    private fun resetInputFields() {
        binding.userNameEditText.setText("")
        binding.userSurnameEditText.setText("")
        binding.userEmailEditText.setText("")
        binding.userPasswordEditText.setText("")
        binding.autoCompleteRoles.setText(null)
        binding.autoCompleteRoles.isFocusable = false
    }

    fun validateInput(roleOfUser:String,userName:String,userSurname:String,userEmail:String,userPassword:String): Boolean {
        var isValid = true

        if (roleOfUser.isBlank()) {
            println("Please select a role")
            isValid = false
        }

        if (userName.isEmpty()) {
            println("Please enter a first name")
            isValid = false
        }

        if (userSurname.isEmpty()) {
            println("Please enter a last name")
            isValid = false
        }

        if (userEmail.isEmpty()) {
            println("Please enter an email address")
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            println("Please enter a valid email address")
            isValid = false
        }

        if (userPassword.isEmpty()) {
            println("Please enter a password")
            isValid = false
        } else if (userPassword.length < 8) {
            println("Password must be at least 8 characters long")
            isValid = false
        }

        return isValid
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddNewUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}