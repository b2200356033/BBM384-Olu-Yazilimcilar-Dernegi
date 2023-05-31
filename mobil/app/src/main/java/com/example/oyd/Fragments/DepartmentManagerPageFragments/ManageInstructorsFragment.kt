package com.example.oyd.Fragments.DepartmentManagerPageFragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.oyd.API.RetrofitClient
import com.example.oyd.Models.Course
import com.example.oyd.R
import com.example.oyd.Users.Instructor
import com.example.oyd.databinding.FragmentManageInstructorsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ManageInstructorsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentManageInstructorsBinding? = null
    private val binding get() = _binding!!
    private var isGetting = false
    var error=true
    private lateinit var autoCompleteInstructor: AutoCompleteTextView;
    private lateinit var autoCompleteCourses: AutoCompleteTextView;
    private lateinit var instructorName : TextView;
    private lateinit var assignButton : Button;
    private lateinit var instructor: Instructor
    private lateinit var course: Course
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private  var instructors: List<Instructor> = ArrayList<Instructor>()
    private  var courses: List<Course> = ArrayList<Course>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentManageInstructorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        val job = coroutineScope.launch {
            try {
                withContext(Dispatchers.Main) {
                    gettingFile()
                }

                withContext(Dispatchers.Default) {
                    val call = RetrofitClient.instance.apiGetAllInstructorFromServer()
                    val call2 = RetrofitClient.instance.apiGetAllCourseFromServer()
                    error= !(call.isSuccessful && call2.isSuccessful)
                    val InstructorBody = call.body()
                    val CourseBody = call2.body()
                    if (InstructorBody != null) {
                        instructors = InstructorBody
                        println("instructors found")
                        //   Toast.makeText(requireContext(),"Instructors found",Toast.LENGTH_SHORT).show()
                    } else {
                        println("no instructors found")
                        // Toast.makeText(requireContext(),"No instructors found",Toast.LENGTH_SHORT).show()

                    }
                    if (CourseBody != null) {
                        courses = CourseBody
                        println("courses found")
                        //Toast.makeText(requireContext(),"Courses found",Toast.LENGTH_SHORT).show()
                    } else {
                        println("no courses found")
                        //  Toast.makeText(requireContext(),"No courses found",Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                println("Exception: $e")
                error=true
                showStatus("Exception: $e")
                Toast.makeText(requireContext(), "Exception: $e", Toast.LENGTH_LONG).show()
            }
        }
        job.invokeOnCompletion {
            gettingComplete()
            setupAutoComplete()
            // İşlem tamamlandığında veya iptal edildiğinde yapılacak işlemler
        }
        assignButton.setOnClickListener {
            if(!::course.isInitialized && !::instructor.isInitialized){
                Toast.makeText(requireContext(),"Please select Course and Instructor",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else if(!::course.isInitialized){
                Toast.makeText(requireContext(),"Please select Course",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else if(!::instructor.isInitialized){
                Toast.makeText(requireContext(),"Please select Instructor",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val coroutineScope = CoroutineScope(Dispatchers.Main)
            val job = coroutineScope.launch {
                try {
                    withContext(Dispatchers.Main) {
                        progressText.text="Assigning Instructor to Course"
                        gettingFile()
                    }
                    withContext(Dispatchers.Default) {
                        course.instructor=instructor
                        val call = RetrofitClient.instance.apiassignInstructortoCourse(instructor.id!!,course.id!!)

                        if (call.isSuccessful) {
                            error=false
                            instructor=call.body()!!
                            println(instructor)

                        }
                        else{
                            error=true
                            println("instructor not assigned")
                        }
                    }
                } catch (e: Exception) {
                    println("Exception: $e")
                    error=true
                    Toast.makeText(requireContext(), "Exception: $e", Toast.LENGTH_LONG).show()
                }
            }
            job.invokeOnCompletion {
                gettingComplete()
                if(!error){
                    updateTextInstructor()
                    showStatus("Instructor assigned successfully")
                }
                else{
                    showStatus("Instructor not assigned")
                }



            }

        }
    }

    private fun setupAutoComplete() {
        println("setupAutoComplete")
        for(tempInstructor in instructors){
            if(tempInstructor.courses!!.size!=0){
                for(tempCourse in tempInstructor.courses!!){
                    for(course in courses){
                        if(course.id==tempCourse.id){
                            course.instructor=tempInstructor
                        }
                    }
                }

            }
        }
        val adapter=ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line,android.R.id.text1,instructors.map { "${it.name} ${it.surname}" })
        autoCompleteInstructor.setAdapter(adapter)
        val adapterCourses=ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line,android.R.id.text1,courses.map { it.name })
        autoCompleteCourses.setAdapter(adapterCourses)

        //view the course name and instructor name and surname
        autoCompleteInstructor.setOnItemClickListener { adapterView, view, i, l -> instructor=instructors.get(i)
            Toast.makeText(requireContext(),"Instructor: ${instructor.name} ${instructor.surname}",Toast.LENGTH_SHORT).show()

        }
        autoCompleteCourses.setOnItemClickListener { adapterView, view, i, l -> course=courses.get(i)
            Toast.makeText(requireContext(),"Course: ${course.name}",Toast.LENGTH_SHORT).show()
            if(course.instructor!=null){
                instructorName.setText("${course.instructor?.name} ${course.instructor?.surname}")
            }
            else{
                instructorName.setText("Empty")
            }

        }

    }
    fun updateTextInstructor(){
        println("updateTextInstructor")
        if(course!=null && instructor!=null){
            instructorName.setText("${course.instructor?.name} ${course.instructor?.surname}")
        }
        else{
            instructorName.setText("Empty")
        }
    }
    fun initView(View: View){
        println("initView")
        autoCompleteInstructor =binding.instructorAuto
        autoCompleteCourses = binding.courseAuto
        assignButton = binding.assign
        instructorName = binding.instructorName
        progressBar = binding.progressBar
        progressText = binding.progressText
    }
    private fun gettingFile() {
        progressBar.visibility = View.VISIBLE
        progressText.visibility = View.VISIBLE
        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        isGetting = true
    }
    private fun gettingComplete() {
        isGetting = false
        progressBar.visibility = View.GONE
        progressText.visibility = View.INVISIBLE
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
    override fun onResume() {
        super.onResume()

        if (isGetting) {
            progressBar.visibility = View.VISIBLE
            progressText.visibility = View.VISIBLE
        }
    }

    fun showStatus(textShow: String){
        val text:String
        val dialogBinding:View
        if (!error)
        {
            dialogBinding = layoutInflater.inflate(R.layout.succesfull_page, null)
            text=textShow
        }
        else{
            dialogBinding = layoutInflater.inflate(R.layout.error_page, null)
            text="Assign Instructor to Course Failed"
        }
        val myDialog = this.context?.let { it1 -> Dialog(it1) }
        myDialog?.setContentView(dialogBinding)
        myDialog?.setCancelable(true)
        var textView=myDialog?.findViewById<TextView>(R.id.message)
        textView?.text=text
        myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog?.show()
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                myDialog?.dismiss()
            }
        }.start()
    }
}