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
import com.example.oyd.R
import com.example.oyd.Users.Admin
import com.example.oyd.Users.DepartmentManager
import com.example.oyd.Users.Instructor
import com.example.oyd.Users.Student
import com.example.oyd.databinding.FragmentAddNewUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNewUserFragment : Fragment() {

    private var _binding: FragmentAddNewUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var addUserBtn: Button
    private lateinit var userName: String
    private lateinit var userSurname: String
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private var role:String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addUserBtn = binding.addUserBtn
        setUpAutoCompleteTextViews()

        binding.addUserBtn.setOnClickListener {
            userName = binding.userNameEditText.text.toString()
            userSurname = binding.userSurnameEditText.text.toString()
            userEmail = binding.userEmailEditText.text.toString()
            userPassword = binding.userPasswordEditText.text.toString()
            if(validateInput(role,userName.trim(),userSurname.trim(),userEmail.trim(),userPassword.trim())){
                if(role.equals("Student")){
                    val student = Student(null,userName,userSurname,userEmail,userPassword,"")
                    sendStudentToServer(student)
                }
                else if(role.equals("Instructor")){
                    val instructor = Instructor(null,userName,userSurname,userEmail,userPassword,"")
                    sendInstructorToServer(instructor)
                }
                else if(role.equals("Department Manager")){
                    val dm = DepartmentManager(null,userName,userSurname,userEmail,userPassword,"")
                    sendDepartmentManagerToServer(dm)
                }
                else{
                    val admin = Admin(null,userName, userSurname, userEmail, userPassword, "")
                    sendAdminToServer(admin)
                }
                //if successful, then create success dialog, else show error, for now, it will be always successful
                showSuccessDialog()
                //to reset the boxes
                resetInputFields()
            }
        }
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
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apisendAdminToServer(admin) }
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Admin sent successfully: ${admin.name} ${admin.surname}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to send the admin: ${admin.name} ${admin.surname}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Can not create the admin user", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sendStudentToServer(student: Student) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apisendStudentToServer(student) }
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Student sent successfully: ${student.name} ${student.surname}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to send the student: ${student.name} ${student.surname}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(),"Can not create the student user", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sendInstructorToServer(instructor: Instructor) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apisendInstructorToServer(instructor) }
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Instructor sent successfully: ${instructor.name} ${instructor.surname}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to send the instructor: ${instructor.name} ${instructor.surname}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Can not create the instructor user", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sendDepartmentManagerToServer(dm: DepartmentManager) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apisendDepartmentManagerToServer(dm) }
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Department Manager sent successfully: ${dm.name} ${dm.surname}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to send the department manager: ${dm.name} ${dm.surname}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Can not create the department manager user", Toast.LENGTH_LONG).show()
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

        if (roleOfUser.isBlank() || roleOfUser.equals("Roles")) {
            println("Please select a role")
            binding.userRoleEditText.error="This field can't be empty"
            isValid = false
        }else{
            binding.userRoleEditText.error=null
        }

        if (userName.isEmpty()) {
            println("Please enter a first name")
            binding.userNameEditLayout.error ="This field can't be empty"
            isValid = false
        }
        else{
            binding.userNameEditLayout.error =null
        }

        if (userSurname.isEmpty()) {
            println("Please enter a last name")
            binding.userSurnameEditLayout.error ="This field can't be empty"
            isValid = false
        }
        else{
            binding.userSurnameEditLayout.error =null
        }

        if (userEmail.isEmpty()) {
            println("Please enter an email address")
            binding.userEmailEditLayout.error ="This field can't be empty"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            println("Please enter a valid email address")
            println("current email: $userEmail")
            binding.userEmailEditLayout.error ="Please enter a valid email"
            isValid = false
        }
        else{
            binding.userEmailEditLayout.error =null
        }

        if (userPassword.isEmpty()) {
            println("Please enter a password")
            binding.userPasswordEditLayout.error ="This field can't be empty"
            isValid = false
        } else if (userPassword.length < 8) {
            println("Password must be at least 8 characters long")
            binding.userPasswordEditLayout.error ="Password must be 8 digits"
            isValid = false
        }
        else{
            binding.userPasswordEditLayout.error =null
        }

        return isValid
    }
    fun validateInputForTest(roleOfUser:String,userName:String,userSurname:String,userEmail:String,userPassword:String): Boolean {
        var isValid = true

        if (roleOfUser.isBlank() || roleOfUser.equals("Roles")) {
            println("Please select a role")

            isValid = false
        }else{

        }

        if (userName.isEmpty()) {
            println("Please enter a first name")

            isValid = false
        }
        else{

        }

        if (userSurname.isEmpty()) {
            println("Please enter a last name")

            isValid = false
        }
        else{

        }

        if (userEmail.isEmpty()) {
            println("Please enter an email address")

            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            println("Please enter a valid email address")
            println("current email: $userEmail")

            isValid = false
        }
        else{

        }

        if (userPassword.isEmpty()) {
            println("Please enter a password")

            isValid = false
        } else if (userPassword.length < 8) {
            println("Password must be at least 8 characters long")

            isValid = false
        }
        else{

        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

