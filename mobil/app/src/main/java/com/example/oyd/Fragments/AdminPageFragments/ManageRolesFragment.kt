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
import com.example.oyd.API.SignupRequest
import com.example.oyd.R
import com.example.oyd.Users.Admin
import com.example.oyd.Users.DepartmentManager
import com.example.oyd.Users.Instructor
import com.example.oyd.Users.Student
import com.example.oyd.databinding.FragmentManageRolesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageRolesFragment : Fragment() {

    private var _binding: FragmentManageRolesBinding? = null
    private val binding get() = _binding!!

    private lateinit var applyChangesBtn: Button
    private lateinit var userEmail: String
    private var role:String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManageRolesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyChangesBtn = binding.applyChangesBtn
        setUpAutoCompleteTextViews()

        binding.applyChangesBtn.setOnClickListener {
            userEmail = binding.userEmailEditText.text.toString()

            if (validateInput(role, userEmail.trim())) {
                changeRole(userEmail.trim(), role)
            }
            resetInputFields()
        }
    }


    private fun changeRole(email: String, role: String) {
        CoroutineScope(Dispatchers.Main).launch {

            var foundStudent = false
            var foundDepartmentManager = false
            var foundAdmin = false
            var foundInstructor = false

            val signupRequest = SignupRequest(
                name = "",
                surname = "userSurname",
                email = "userEmail",
                password = "userPassword",
                photo = null
            )

            // Student Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiGetStudent(email) }
                if (response.isSuccessful) {
                    val user: Student? = response.body()
                    foundStudent = true
                    signupRequest.name = user?.name.toString()
                    signupRequest.surname = user?.surname.toString()
                    signupRequest.photo = user?.photo.toString()
                    signupRequest.password = user?.password.toString()
                    signupRequest.email = user?.email.toString()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }

            // Department Manager Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiGetDepartmentMenager(email) }
                if (response.isSuccessful) {
                    val user: DepartmentManager? = response.body()
                    foundDepartmentManager = true
                    signupRequest.name = user?.name.toString()
                    signupRequest.surname = user?.surname.toString()
                    signupRequest.photo = user?.photo.toString()
                    signupRequest.password = user?.password.toString()
                    signupRequest.email = user?.email.toString()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }

            // Admin Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiGetAdmin(email) }
                if (response.isSuccessful) {
                    val user: Admin? = response.body()
                    foundAdmin = true
                    signupRequest.name = user?.name.toString()
                    signupRequest.surname = user?.surname.toString()
                    signupRequest.photo = user?.photo.toString()
                    signupRequest.password = user?.password.toString()
                    signupRequest.email = user?.email.toString()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }

            // Instructor Response
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiGetInstructor(email) }
                if (response.isSuccessful) {
                    val user: Instructor? = response.body()
                    foundInstructor = true
                    signupRequest.name = user?.name.toString()
                    signupRequest.surname = user?.surname.toString()
                    signupRequest.photo = user?.photo.toString()
                    signupRequest.password = user?.password.toString()
                    signupRequest.email = user?.email.toString()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }

            // All
            if (!foundStudent && !foundAdmin && !foundInstructor && !foundDepartmentManager) {
                Toast.makeText(requireContext(), "User with e-mail: $email can not be found", Toast.LENGTH_LONG).show()
            }

            else {
                if(role.equals("Student")){
                    if (foundStudent) {
                        Toast.makeText(requireContext(), "User with e-mail: $email is already a student", Toast.LENGTH_LONG).show()
                    }
                    else {
                        deleteUser(foundStudent, foundDepartmentManager, foundInstructor, foundAdmin, email)
                        sendStudentToServer(signupRequest)
                    }
                }
                else if(role.equals("Instructor")){
                    if (foundInstructor) {
                        Toast.makeText(requireContext(), "User with e-mail: $email is already an instructor", Toast.LENGTH_LONG).show()
                    }
                    else {
                        deleteUser(foundStudent, foundDepartmentManager, foundInstructor, foundAdmin, email)
                        sendInstructorToServer(signupRequest)
                    }
                }
                else if(role.equals("Department Manager")){
                    if (foundDepartmentManager) {
                        Toast.makeText(requireContext(), "User with e-mail: $email is already a department manager", Toast.LENGTH_LONG).show()
                    }
                    else {
                        deleteUser(foundStudent, foundDepartmentManager, foundInstructor, foundAdmin, email)
                        sendDepartmentManagerToServer(signupRequest)
                    }
                }
                else{
                    if (foundAdmin) {
                        Toast.makeText(requireContext(), "User with e-mail: $email is already an admin", Toast.LENGTH_LONG).show()
                    }
                    else {
                        deleteUser(foundStudent, foundDepartmentManager, foundInstructor, foundAdmin, email)
                        sendAdminToServer(signupRequest)
                    }
                }
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

    private fun sendAdminToServer(req: SignupRequest) {
        RetrofitClient.instance.apisendAdminToServer(req)
            .enqueue(object : Callback<Admin> {
                override fun onResponse(call: Call<Admin>, response: Response<Admin>) {
                    if(response.isSuccessful) {
                        showSuccessDialog()
                        //Toast.makeText(activity, "Role changed successfully", Toast.LENGTH_SHORT).show()
                    } else if(response.code() == 409) {
                        //Toast.makeText(activity, "User already exists with this email", Toast.LENGTH_SHORT).show()
                    } else {
                        //Toast.makeText(activity, "Failed to add the admin", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Admin>, t: Throwable) {
                    //Toast.makeText(activity, "Failed to add the admin", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun sendStudentToServer(req: SignupRequest) {
        RetrofitClient.instance.apisendStudentToServer(req)
            .enqueue(object : Callback<Student> {
                override fun onResponse(call: Call<Student>, response: Response<Student>) {
                    if(response.isSuccessful) {
                        showSuccessDialog()
                        //Toast.makeText(activity, "Role changed successfully", Toast.LENGTH_SHORT).show()
                    } else if(response.code() == 409) {
                        //Toast.makeText(activity, "User already exists with this email", Toast.LENGTH_SHORT).show()
                    } else {
                        //Toast.makeText(activity, "Failed to add the student", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Student>, t: Throwable) {
                    //Toast.makeText(activity, "Failed to add the student", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun sendInstructorToServer(req: SignupRequest) {
        RetrofitClient.instance.apisendInstructorToServer(req)
            .enqueue(object : Callback<Instructor> {
                override fun onResponse(call: Call<Instructor>, response: Response<Instructor>) {
                    if(response.isSuccessful) {
                        showSuccessDialog()
                        //Toast.makeText(activity, "Role changed successfully", Toast.LENGTH_SHORT).show()
                    } else if(response.code() == 409) {
                        //Toast.makeText(activity, "User already exists with this email", Toast.LENGTH_SHORT).show()
                    } else {
                        //Toast.makeText(activity, "Failed to add the instructor", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Instructor>, t: Throwable) {
                    //Toast.makeText(activity, "Failed to add the instructor", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun sendDepartmentManagerToServer(req: SignupRequest) {
        RetrofitClient.instance.apisendDepartmentManagerToServer(req)
            .enqueue(object : Callback<DepartmentManager> {
                override fun onResponse(call: Call<DepartmentManager>, response: Response<DepartmentManager>) {
                    if(response.isSuccessful) {
                        showSuccessDialog()
                        //Toast.makeText(activity, "Role changed successfully", Toast.LENGTH_SHORT).show()
                    } else if(response.code() == 409) {
                        //Toast.makeText(activity, "User already exists with this email", Toast.LENGTH_SHORT).show()
                    } else {
                        //Toast.makeText(activity, "Failed to add the department manager", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DepartmentManager>, t: Throwable) {
                    //Toast.makeText(activity, "Failed to add the department manager", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private suspend fun deleteUser(stu: Boolean, dm: Boolean, ins: Boolean, ad: Boolean, email: String) {
        if (ad) {
            try {
                val admin = withContext(Dispatchers.IO) { RetrofitClient.instance.apiDeleteAdminByEmail(email) }
                if (admin.isSuccessful) {
                    Toast.makeText(activity, "Role changed successfully", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }
        }

        else if (ins) {
            try {
                val instructor = withContext(Dispatchers.IO) { RetrofitClient.instance.apiDeleteInstructorByEmail(email) }
                if (instructor.isSuccessful) {
                    Toast.makeText(activity, "Role changed successfully", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }
        }

        else if (stu) {
            try {
                val student = withContext(Dispatchers.IO) { RetrofitClient.instance.apiDeleteStudentByEmail(email) }
                if (student.isSuccessful) {
                    Toast.makeText(activity, "Role changed successfully", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }
        }

        else if (dm){
            try {
                val dm = withContext(Dispatchers.IO) { RetrofitClient.instance.apiDeleteDepartmentManagerByEmail(email) }
                if (dm.isSuccessful) {
                    Toast.makeText(activity, "Role changed successfully", Toast.LENGTH_SHORT).show()                            }
            } catch (e: Exception) {
                // Error occurred during the search process
                // Handle the exception or display an error message
            }
        }
    }

    private fun showSuccessDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.course_creation_successful_dialog, null)
        val myDialog = this.context?.let { it1 -> Dialog(it1) }
        myDialog?.setContentView(dialogBinding)
        myDialog?.setCancelable(true)
        var textView=myDialog?.findViewById<TextView>(R.id.successMessage)
        textView?.setText("User's role changed successfully")
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
        binding.userEmailEditText.setText("")
        binding.autoCompleteRoles.setText(null)
        binding.autoCompleteRoles.isFocusable = false
    }

    fun validateInput(roleOfUser:String,userEmail:String): Boolean {
        var isValid = true

        if (roleOfUser.isBlank() || roleOfUser.equals("Roles")) {
            println("Please select a role")
            binding.userRoleEditText.error="This field can't be empty"
            isValid = false
        }else{
            binding.userRoleEditText.error=null
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

