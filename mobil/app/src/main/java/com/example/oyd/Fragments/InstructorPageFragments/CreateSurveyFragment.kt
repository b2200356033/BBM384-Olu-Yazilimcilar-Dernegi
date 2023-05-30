package com.example.oyd.Fragments.InstructorPageFragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oyd.API.RetrofitClient
import com.example.oyd.R
import com.example.oyd.databinding.FragmentCreateSurveyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.android.material.textfield.TextInputLayout
import android.widget.TextView
import android.widget.Toast
import com.example.oyd.Models.Course
import com.example.oyd.Models.SurveyFinder


class CreateSurveyFragment : Fragment() {

    private var _binding: FragmentCreateSurveyBinding? = null
    private val binding get() = _binding!!

    private lateinit var questionList: MutableList<String> // to store questions
    private lateinit var questionListAdapter: RecyclerView.Adapter<*>
    private lateinit var questionLayoutManager: RecyclerView.LayoutManager

    private lateinit var instructorId: String
    private var courseList = ArrayList<String>()
    private var courses = ArrayList<Course>()
    private var courseName:String =""
    private var courseId:Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateSurveyBinding.inflate(inflater, container, false)

        val user= context?.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        instructorId = user?.getString("id","")!!
        println(instructorId)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiGetInstructorCoursesFromServer(instructorId.toLong()) }
                if (response.isSuccessful) {
                    // Survey sent successfully
                    // you can also clear the list of questions here
                    for (Course in response.body()!!){
                        courseList.add(Course.name)
                        courses.add(Course)
                        println(Course.name)
                        setUpAutoCompleteTextViews()

                    }

                } else {
                    // Response failed
                    println("Get instructor courses failed")
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }



        return binding.root
    }

    private fun setUpAutoCompleteTextViews() {
        /*
        println("Function is called")

        val types = courseList
        val autoComplete: AutoCompleteTextView = binding.autoCompleteCourses
        setUpAdapterAndListener(autoComplete, types) { type ->
            courseName = type

        }
        println("courses list size is: "+courses.size)
        println("course name is "+ courseName)
        for (course in courses){
            println("Current course name is: "+course.name + "is equal to?" + courseName)
            if (course.name.equals(courseName)){
                courseId = course.id!!
                println("Updating courseId as:"+courseId)
            }
        }
         */
        val autoComplete: AutoCompleteTextView = binding.autoCompleteCourses

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, courseList)
        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, i, _ ->
            courseName = adapterView.getItemAtPosition(i).toString()

            for (course in courses) {
                if (course.name == courseName) {
                    courseId = course.id!!
                    break
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

            // Update the courseId here
            for (course in courses){
                if (course.name == adapterView.getItemAtPosition(i).toString()){
                    courseId = course.id!!
                }
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionList = mutableListOf()
        questionLayoutManager = LinearLayoutManager(activity)
        // You will need to define a RecyclerView adapter for your question list
        questionListAdapter = QuestionAdapter(questionList) // replace with your adapter

        binding.rvQuestions.apply {
            layoutManager = questionLayoutManager
            adapter = questionListAdapter
        }

        binding.btnAddQuestion.setOnClickListener {
            val questionText = binding.etQuestion.text.toString()
            if (questionText.isNotEmpty()) {
                questionList.add(questionText)
                println(questionList)
                questionListAdapter.notifyDataSetChanged()
                binding.etQuestion.setText("") // clear the EditText
            } else {
                // Show error: question field is empty
                (binding.etQuestionLayout as TextInputLayout).error = "Question cannot be empty"
            }
        }

        binding.btnSubmitSurvey.setOnClickListener {


            // provide your course ID and instructor ID here


            println("question list is: "+questionList)
            var newList= ArrayList<String>()
            newList.addAll(questionList)
            val surveyFinder = SurveyFinder(
                null, // ID is usually generated by the server
                courseId, // provide your course ID here
                instructorId.toLong(), // provide your instructor ID here
                newList // list of questions
            )
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Confirm Submission")
                setMessage("Are you sure you want to submit the survey?")
                setPositiveButton("Submit") { _, _ ->
                    //sendSurveyToServer(survey)
                    AlertDialog.Builder(requireContext())
                        .setTitle("Survey Submitted")
                        .setMessage("Your survey is submitted successfully!")
                        .setPositiveButton("OK", null)
                        .show()
                    sendSurveyToServer(surveyFinder)
                    questionList.clear()
                    questionListAdapter.notifyDataSetChanged()
                }
                setNegativeButton("Cancel", null)
                show()
            }

            //sendSurveyToServer(survey)
        }
    }

    private fun sendSurveyToServer(survey: SurveyFinder) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiSendSurveyToServer(survey) }
                if (response.isSuccessful) {
                    // Survey sent successfully
                    // you can also clear the list of questions here
                } else {
                    // Response failed
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class QuestionAdapter(private val myDataset: List<String>) :
        RecyclerView.Adapter<QuestionAdapter.MyViewHolder>() {
        // Provide a reference to the views for each data item
        inner class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            // create a new view
            val textView = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
            // set the view's size, margins, paddings and layout parameters
            return MyViewHolder(textView)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.textView.text = (position+1).toString()+". "+ myDataset[position]
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size
    }
}
