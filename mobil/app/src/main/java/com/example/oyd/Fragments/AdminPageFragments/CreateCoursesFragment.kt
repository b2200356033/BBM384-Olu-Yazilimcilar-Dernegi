package com.example.oyd.Fragments.AdminPageFragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.oyd.API.RetrofitClient
import com.example.oyd.Models.Course
import com.example.oyd.R
import com.example.oyd.databinding.FragmentCreateCoursesBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext




private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CreateCoursesFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentCreateCoursesBinding? = null
    private val binding get() = _binding!!

    private lateinit var courseNameBox: TextInputLayout
    private lateinit var courseNameBoxText: TextInputEditText
    private lateinit var courseCreditBox: TextInputLayout
    private lateinit var courseCreditBoxText: TextInputEditText
    private lateinit var addCourseBtn: Button

    private lateinit var courseName: String
    private lateinit var courseType: String
    private lateinit var courseDepartment: String
    private var courseCredit: Int = 0

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
        _binding = FragmentCreateCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setUpAutoCompleteTextViews()
        setAddCourseBtnClickListener()
    }

    private fun initViews() {
        courseCreditBox = binding.courseCreditBox
        courseCreditBoxText = binding.courseCreditBoxText
        courseNameBox = binding.courseNameBox
        courseNameBoxText = binding.courseNameBoxText
        addCourseBtn = binding.addCourseBtn
    }

    private fun setUpAutoCompleteTextViews() {
        val departments = listOf("Computer Engineering", "Electrical Engineering", "Biology", "Chemistry")
        val courseTypes = listOf("Mandatory", "Elective")

        val autoCompleteDepartments: AutoCompleteTextView = binding.autoCompleteDepartments
        val autoCompleteCourseType: AutoCompleteTextView = binding.autoCompleteCourseType

        setUpAdapterAndListener(autoCompleteDepartments, departments) { department ->
            courseDepartment = department
        }

        setUpAdapterAndListener(autoCompleteCourseType, courseTypes) { type ->
            courseType = type
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

    private fun setAddCourseBtnClickListener() {
        addCourseBtn.setOnClickListener {
            val courseName=courseNameBoxText.text.toString()
            val courseCreditString = courseCreditBoxText.text.toString().trim()
            if (validateInputs(courseName,courseDepartment,courseCreditString,courseType)){
                CoroutineScope(Dispatchers.Main).launch {
                    createCourse()
                    showSuccessDialog()
                    resetInputFields()
                }
            }

        }
    }


    private suspend fun createCourse() {
        courseName = courseNameBoxText.text.toString()

        courseCredit = courseCreditBoxText.text.toString().toInt()
        val course = Course(null,courseName, courseDepartment, courseCredit, courseType)


        sendCourseToServer(course)
    }




    private fun sendCourseToServer(course: Course) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                Toast.makeText(requireContext(),"course data " + course.toString(), Toast.LENGTH_LONG).show()
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apisendCourseToServer(course) }
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Course sent successfully: ${course.toString()}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Response Failed to send course: ${course.toString()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                //Toast.makeText(requireContext(), "Cant Connect server Failed to send course: ${course.toString()}", Toast.LENGTH_LONG).show()
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
                println("COULDNT SEND COURSE")
            }
        }
    }







    private fun showSuccessDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.course_creation_successful_dialog, null)
        val myDialog = this.context?.let { it1 -> Dialog(it1) }
        myDialog?.setContentView(dialogBinding)
        myDialog?.setCancelable(true)
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
        courseCreditBoxText.setText("")
        courseNameBoxText.setText("")
        binding.autoCompleteDepartments.setText(null)
        binding.autoCompleteDepartments.isFocusable = false
        binding.autoCompleteCourseType.setText(null)
        binding.autoCompleteCourseType.isFocusable = false
    }

    fun validateInputs(courseName: String,courseDepartment: String,courseCreditString: String,courseType: String): Boolean{
        if (courseName.isEmpty()) {
            println("Course name can not be empty")
            return false
        }else if (courseDepartment.isEmpty()){
            println("Please select a department")
            return false
        }else if (courseCreditString.isEmpty()){
            println("Course credit cannot be empty")
            return false
        }else if (courseType.isEmpty()){
            println("Please select a course type")
            return false
        }else if (courseCreditString.isNotEmpty()){
            //if credit is not an integer credit will be null
            val credit = courseCreditString.toIntOrNull()
            if (credit == null || credit < 1) {
                println("Invalid course credit")
                return false
            } else {
                courseCredit = credit

            }
        }/*else if (if there is already a course by that name){
            println("This course name is already taken")
            return false
        }*/
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateCoursesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

