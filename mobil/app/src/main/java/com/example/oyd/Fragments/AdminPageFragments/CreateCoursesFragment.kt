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
import com.example.oyd.Models.Course
import com.example.oyd.R
import com.example.oyd.databinding.FragmentCreateCoursesBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateCoursesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateCoursesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding:FragmentCreateCoursesBinding? = null
    private val binding get() = _binding!!
    private lateinit var courseNameBox : TextInputLayout
    private lateinit var courseNameBoxText : TextInputEditText
    private lateinit var courseCreditBox : TextInputLayout
    private lateinit var courseCreditBoxText : TextInputEditText
    private lateinit var addCourseBtn: Button
    private lateinit var courseName:String
    private lateinit var courseType:String
    private lateinit var courseDepartment:String

    private var courseCredit:Int = 0

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
        // Inflate the layout for this fragment
        _binding = FragmentCreateCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        courseCreditBox=binding.courseCreditBox
        courseCreditBoxText=binding.courseCreditBoxText
        courseNameBox=binding.courseNameBox
        courseNameBoxText=binding.courseNameBoxText
        val departments = listOf<String>("Computer Engineering","Electrical Engineering","Biology","Chemistry")
        val courseTypes = listOf<String>("Mandatory","Elective")
        val autoComplete:AutoCompleteTextView = binding.autoCompleteDepartments
        val autoCompleteCourseType:AutoCompleteTextView = binding.autoCompleteCourseType
        val adapter= ArrayAdapter(requireContext(),R.layout.list_item,departments)
        val adapter2= ArrayAdapter(requireContext(),R.layout.list_item,courseTypes)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener{
                adapterView, view, i, l ->
            courseDepartment =adapterView.getItemAtPosition(i).toString()
        }
        autoCompleteCourseType.setAdapter(adapter2)
        autoCompleteCourseType.onItemClickListener = AdapterView.OnItemClickListener{
                adapterView, view, i, l ->
            courseType =adapterView.getItemAtPosition(i).toString()
        }
        addCourseBtn=binding.addCourseBtn
        addCourseBtn.setOnClickListener{
            val courseName = courseNameBoxText.text.toString().trim()
            val courseDepartment = autoComplete.text.toString().trim()
            val courseCreditString = courseCreditBoxText.text.toString().trim()
            val courseType = autoCompleteCourseType.text.toString().trim()

            if (validateInputs(courseName,courseDepartment,courseCreditString,courseType)){
                courseCredit=courseCreditBoxText.text.toString().toInt()
                val course = Course(courseName,courseDepartment,courseCredit,courseType)
                //send course object to database


                //if successful, create a popup screen, for now, it will always be successful
                val dialogBinding = layoutInflater.inflate(R.layout.course_creation_successful_dialog, null)
                val myDialog = this.context?.let { it1 -> Dialog(it1) }
                myDialog?.setContentView(dialogBinding)
                myDialog?.setCancelable(true)
                myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                myDialog?.show()
                object : CountDownTimer(3000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        // TODO Auto-generated method stub
                    }

                    override fun onFinish() {
                        // TODO Auto-generated method stub
                        myDialog?.dismiss()
                    }
                }.start()
                //reset the inputs in the boxes
                courseCreditBoxText.setText("")
                courseNameBoxText.setText("")
                autoComplete.setText(null)
                autoComplete.isFocusable = false
                autoCompleteCourseType.setText(null)
                autoCompleteCourseType.isFocusable=false
            }

        }
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateCoursesFragment.
         */
        // TODO: Rename and change types and number of parameters
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

