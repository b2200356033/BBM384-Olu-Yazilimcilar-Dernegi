package com.example.oyd.Fragments.InstructorPageFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oyd.API.RetrofitClient
import com.example.oyd.Models.Course
import com.example.oyd.Models.Survey
import com.example.oyd.R
import com.example.oyd.databinding.FragmentViewSurveysBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewSurveysFragment : Fragment() {

    private var _binding: FragmentViewSurveysBinding? = null
    private val binding get() = _binding!!

    private lateinit var surveyList: MutableList<String>
    private lateinit var surveyListAdapter: RecyclerView.Adapter<*>
    private lateinit var surveyLayoutManager: RecyclerView.LayoutManager

    private lateinit var instructorId: String
    private var courseList = ArrayList<String>()
    private var courses = ArrayList<Course>()
    private lateinit var courseName:String
    private var courseId:Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewSurveysBinding.inflate(inflater, container, false)

        val user = context?.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        instructorId = user?.getString("id","")!!

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiGetInstructorCoursesFromServer(instructorId.toLong()) }
                if (response.isSuccessful) {
                    for (Course in response.body()!!){
                        courseList.add(Course.name)
                        courses.add(Course)
                    }
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }

        setUpAutoCompleteTextViews()

        return binding.root
    }

    private fun setUpAutoCompleteTextViews() {
        val types = courseList
        val autoComplete: AutoCompleteTextView = binding.autoCompleteCourses
        setUpAdapterAndListener(autoComplete, types) { type ->
            courseName = type
            for (course in courses){
                if (course.name.equals(courseName)){
                    courseId = course.id!!
                    loadSurveyForCourse(courseId)
                }
            }
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

    private fun loadSurveyForCourse(courseId: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiGetSurveyForCourse(courseId) }
                if (response.isSuccessful) {
                    // Show the survey questions in the RecyclerView
                    surveyList.clear()
                    surveyList.addAll(response.body()!!)
                    surveyListAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        surveyList = mutableListOf()
        surveyLayoutManager = LinearLayoutManager(activity)
        surveyListAdapter = SurveyAdapter(surveyList)

        binding.rvQuestions.apply {
            layoutManager = surveyLayoutManager
            adapter = surveyListAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class SurveyAdapter(private val myDataset: List<String>) :
        RecyclerView.Adapter<SurveyAdapter.MyViewHolder>() {

        inner class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val textView = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
            return MyViewHolder(textView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.textView.text = (position+1).toString()+". "+ myDataset[position]
        }

        override fun getItemCount() = myDataset.size
    }
}
