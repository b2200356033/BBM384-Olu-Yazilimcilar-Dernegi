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
import com.example.oyd.Adapters.CourseAdapter
import com.example.oyd.Adapters.ViewStudentCoursesAdapter
import com.example.oyd.Models.Course
import com.example.oyd.R
import com.example.oyd.databinding.FragmentAddCourseBinding
import com.example.oyd.databinding.FragmentViewCoursesBinding

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
    val dummyCourseList = ArrayList<Course>()
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
        initializeDummyList()
        recyclerView=binding.myCourseRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        //burdaki dummylist databseden studenta özel listi çektikten sonra gelecek
        val courseAdapter = ViewStudentCoursesAdapter(requireContext(),dummyCourseList)
        courseAdapter.setOnItemClickListener { course ->
            // Handle item click event

            //showCourseInfoDialog(course)
        }
        recyclerView.adapter = courseAdapter

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
    private fun initializeDummyList(){
        dummyCourseList.add(Course(null,"BBM384","CS",6,"Mandatory",null,null,null))
        dummyCourseList.add(Course(null,"BBM342","CS",6,"Mandatory",null,null,null))
        dummyCourseList.add(Course(null,"BBM382","CS",4,"Mandatory",null,null,null))
        dummyCourseList.add(Course(null,"ELE296","EE",6,"Elective",null,null,null))
        dummyCourseList.add(Course(null,"BBM405","CS",6,"Mandatory",null,null,null))
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
            dialog.cancel()
        }
        cancelBtn.setOnClickListener {
            dialog.cancel()
        }
    }
}