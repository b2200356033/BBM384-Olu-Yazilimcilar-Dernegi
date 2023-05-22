package com.example.oyd.Fragments.DepartmentManagerPageFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import com.example.oyd.API.RetrofitClient
import com.example.oyd.Models.Course
import com.example.oyd.Users.Instructor
import com.example.oyd.databinding.FragmentManageInstructorsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ManageInstructorsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ManageInstructorsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentManageInstructorsBinding? = null
    private val binding get() = _binding!!
    private lateinit var autoCompleteInstructor: AutoCompleteTextView;
    private lateinit var autoCompleteCourses: AutoCompleteTextView;
    private lateinit var assignButton : Button;
    private lateinit var instructor: Instructor
    private lateinit var course: Course
    private  var instructors: List<Instructor> = ArrayList<Instructor>()
    private  var courses: List<Course> = ArrayList<Course>()
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
        _binding = FragmentManageInstructorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get instructors and courses from database and wait for them to be fetched
        val coroutineScope= CoroutineScope(Dispatchers.IO)
        val job=coroutineScope.launch {
            val call = RetrofitClient.instance.apiGetAllInstructorFromServer()
            val body = call.body()
            if (body != null) {
                instructors = body
                println("instructors found")
                //   Toast.makeText(requireContext(),"Instructors found",Toast.LENGTH_SHORT).show()
            }
            else{
                println("no instructors found")
                // Toast.makeText(requireContext(),"No instructors found",Toast.LENGTH_SHORT).show()

            }

            val call2 = RetrofitClient.instance.apiGetAllCourseFromServer()
            val body2 = call2.body()
            if (body2 != null) {
                courses = body2
                println("courses found")
                //Toast.makeText(requireContext(),"Courses found",Toast.LENGTH_SHORT).show()
            }
            else{
                println("no courses found")
                //  Toast.makeText(requireContext(),"No courses found",Toast.LENGTH_SHORT).show()
            }
        }
        runBlocking {
            job.join()
        }
        initView()
        setupAutoComplete()




    }

    private fun setupAutoComplete() {
        println("setupAutoComplete")
        // TODO Database den gelen verileri insturctors ve courses listelerine ata
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

        }

    }

    fun initView(){
        println("initView")
        autoCompleteInstructor =binding.instructorAuto
        autoCompleteCourses = binding.courseAuto
        assignButton = binding.assign
        assignButton.setOnClickListener {
            println("button click")
            // TODO ASSING OR UPDATE . Check update or assign is here or in backend?

            val coroutineScope= CoroutineScope(Dispatchers.IO)
            val job=coroutineScope.launch {
               // course.InstructorId=instructor.id
                //val call = RetrofitClient.instance.apisetInstructorToCourse(course)

                if (call.isSuccessful) {
                    println("instructor assigned")
                    Toast.makeText(requireContext(),"Instructor assigned",Toast.LENGTH_SHORT).show()
                }
                else{
                    println("instructor not assigned")
                    Toast.makeText(requireContext(),"Instructor not assigned",Toast.LENGTH_SHORT).show()

                }
            }
            println(instructors)
            println(courses)
        }


    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ManageInstructorsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}