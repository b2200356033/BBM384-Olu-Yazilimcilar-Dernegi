package com.example.oyd.Fragments.StudentPageFragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button

import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oyd.Adapters.CourseAdapter
import com.example.oyd.Models.Course
import com.example.oyd.R
import com.example.oyd.databinding.FragmentAddCourseBinding
import com.example.oyd.databinding.FragmentAddNewUserBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddCourseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddCourseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAddCourseBinding? = null
    private val binding get() = _binding!!
    val dummyCourseList = ArrayList<Course>()
    private var tempArrayList = ArrayList<Course>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView :SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            setHasOptionsMenu(true);

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeDummyList()
        //this is for search bar filter, later on change this to database list
        searchView=binding.searchView
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        recyclerView=binding.courseRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val courseAdapter = CourseAdapter(requireContext(),tempArrayList)
        courseAdapter.setOnItemClickListener { course ->
            // Handle item click event

            showCustomDialog(course)
        }
        recyclerView.adapter = courseAdapter

    }

    private fun filterList(newText: String?) {
        tempArrayList.clear()
        val searchText = newText!!.lowercase()
        recyclerView.adapter?.notifyDataSetChanged()
        if(searchText.isNotEmpty()){
            for(course in dummyCourseList){
                if(course.name.lowercase().contains(newText!!.lowercase())){
                    tempArrayList.add(course)
                }
            }
        }
        else{
            tempArrayList.clear()
            tempArrayList.addAll(dummyCourseList)
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddCourseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddCourseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun initializeDummyList(){
        dummyCourseList.add(Course(null,"BBM384","CS",6,"Mandatory"))
        dummyCourseList.add(Course(null,"BBM342","CS",6,"Mandatory"))
        dummyCourseList.add(Course(null,"BBM382","CS",4,"Mandatory"))
        dummyCourseList.add(Course(null,"ELE296","EE",6,"Elective"))
        dummyCourseList.add(Course(null,"BBM405","CS",6,"Mandatory"))
        tempArrayList.addAll(dummyCourseList);
    }
    private fun showCustomDialog(course: Course) {
        Toast.makeText(requireContext(),"function called", Toast.LENGTH_LONG).show()
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.add_course_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val courseNameBox: TextView = dialog.findViewById(R.id.courseNameTxt)
        val enrollBtn: Button = dialog.findViewById(R.id.enrollBtn)
        val cancelBtn: Button = dialog.findViewById(R.id.cancelBtn)
        courseNameBox.text=course.name
        enrollBtn.setOnClickListener {
            //add this course to this student's list
            Toast.makeText(context,"Clicked on enroll", Toast.LENGTH_LONG).show()
            dialog.cancel()
        }
        cancelBtn.setOnClickListener {
            dialog.cancel()
        }
    }
}