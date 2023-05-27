package com.example.oyd.Fragments.StudentPageFragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oyd.API.RetrofitClient
import com.example.oyd.Activities.StudentProfilePage
import com.example.oyd.Adapters.CourseAdapter
import com.example.oyd.Adapters.ViewStudentCoursesAdapter
import com.example.oyd.Models.Course
import com.example.oyd.R
import com.example.oyd.databinding.FragmentAddCourseBinding
import com.example.oyd.databinding.FragmentViewCoursesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewCoursesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewCoursesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentViewCoursesBinding? = null
    private val binding get() = _binding!!
    private var courseList = ArrayList<Course>()
    private lateinit var recyclerView: RecyclerView

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
        _binding = FragmentViewCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView=binding.myCourseRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        //burdaki dummylist databseden studenta özel listi çektikten sonra gelecek
        val courseAdapter = ViewStudentCoursesAdapter(requireContext(),courseList)
        courseAdapter.setOnItemClickListener { course ->
            // Handle item click event

            //showCourseInfoDialog(course)
        }
        courseAdapter.setOnDeleteBtnClickListener { course ->
            dropCourse(course)
        }
        recyclerView.adapter = courseAdapter
        CoroutineScope(Dispatchers.Main).launch {
            val activity = requireActivity() as StudentProfilePage
            val response = withContext(Dispatchers.IO) {
                RetrofitClient.instance.apiGetStudentCoursesFromServer(activity.getStudentId())
            }
            if (response.isSuccessful) {
                courseList.addAll(response.body()!!)
                if(courseList.isEmpty()){
                    System.out.println("Course list is empty")
                }
                else{
                    for(Course in courseList){
                        System.out.println(Course)
                    }
                }
                recyclerView.adapter?.notifyDataSetChanged()


            } else {
                Toast.makeText(context,"Student courses fetch failed",Toast.LENGTH_LONG).show()
            }

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewCoursesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewCoursesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun showCourseInfoDialog(course: Course) {
        //burayı info gösterecek şekilde düzelt

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.drop_course_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val courseNameBox: TextView = dialog.findViewById(R.id.courseNameTxt)
        val dropBtn: Button = dialog.findViewById(R.id.dropCourseBtn)
        val cancelBtn: Button = dialog.findViewById(R.id.cancelBtn)
        courseNameBox.text=course.name
        dropBtn.setOnClickListener {
            //drop this course to this student's list
            Toast.makeText(context,"Clicked on drop", Toast.LENGTH_LONG).show()
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context,"In coroutine", Toast.LENGTH_LONG).show()
                try{
                    val activity = requireActivity() as StudentProfilePage
                    val response = withContext(Dispatchers.IO) {
                        RetrofitClient.instance.apiDeleteCourseFromStudent(activity.getStudentId(), course.id!!)
                    }
                    if (response.isSuccessful) {
                        Toast.makeText(context,"Dropped course ${course.name} successfully",Toast.LENGTH_LONG).show()
                        recyclerView.adapter?.notifyDataSetChanged()


                    } else {
                        Toast.makeText(context,"Drop failed",Toast.LENGTH_LONG).show()
                    }
                }catch (e: Exception ){
                    System.out.println(e)
                }

            }
            dialog.cancel()
        }
        cancelBtn.setOnClickListener {
            dialog.cancel()
        }
    }
    private fun dropCourse(course: Course) {
        //burayı info gösterecek şekilde düzelt

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.drop_course_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val courseNameBox: TextView = dialog.findViewById(R.id.courseNameTxt)
        val dropBtn: Button = dialog.findViewById(R.id.dropCourseBtn)
        val cancelBtn: Button = dialog.findViewById(R.id.cancelBtn)
        courseNameBox.text=course.name
        dropBtn.setOnClickListener {
            //drop this course to this student's list
            Toast.makeText(context,"Clicked on drop", Toast.LENGTH_LONG).show()
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context,"In coroutine", Toast.LENGTH_LONG).show()
                try{
                    val activity = requireActivity() as StudentProfilePage
                    val response = withContext(Dispatchers.IO) {
                        RetrofitClient.instance.apiDeleteCourseFromStudent(activity.getStudentId(), course.id!!)
                    }
                    if (response.isSuccessful) {
                        Toast.makeText(context,"Dropped course ${course.name} successfully",Toast.LENGTH_LONG).show()
                        courseList.remove(course)
                        recyclerView.adapter?.notifyDataSetChanged()


                    } else {
                        Toast.makeText(context,"Drop failed",Toast.LENGTH_LONG).show()
                    }
                }catch (e: Exception ){
                    System.out.println(e)
                }

            }
            dialog.cancel()
        }
        cancelBtn.setOnClickListener {
            dialog.cancel()
        }
    }
}