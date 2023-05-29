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
import com.example.oyd.Models.Survey
import com.example.oyd.R
import com.example.oyd.databinding.FragmentCreateSurveyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.android.material.textfield.TextInputLayout
import android.widget.TextView
import android.widget.Toast
import com.example.oyd.API.ApiService
import com.example.oyd.Models.Course


class CreateSurveyFragment : Fragment() {

    private var _binding: FragmentCreateSurveyBinding? = null
    private val binding get() = _binding!!

    private lateinit var questionList: MutableList<String> // to store questions
    private lateinit var questionListAdapter: RecyclerView.Adapter<*>
    private lateinit var questionLayoutManager: RecyclerView.LayoutManager

    private lateinit var instructorId: String
    private var courseList = ArrayList<String>()
    private var courses = ArrayList<Course>()
    private lateinit var courseName:String
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
                    }

                } else {
                    // Response failed
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
        }

        for (course in courses){
            if (course.name.equals(courseName)){
                courseId = course.id!!
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
                questionListAdapter.notifyDataSetChanged()
                binding.etQuestion.setText("") // clear the EditText
            } else {
                // Show error: question field is empty
                (binding.etQuestionLayout as TextInputLayout).error = "Question cannot be empty"
            }
        }

        binding.btnSubmitSurvey.setOnClickListener {
            if (binding.etQuestionLayout.editText?.text?.isNotEmpty() == true) {
                val questionText = binding.etQuestionLayout.editText?.text.toString()
                questionList.add(questionText)
                questionListAdapter.notifyItemInserted(questionList.size - 1)
                binding.etQuestionLayout.editText?.text?.clear()
            }

            // provide your course ID and instructor ID here



            val survey = Survey(
                null, // ID is usually generated by the server
                courseId, // provide your course ID here
                instructorId.toLong(), // provide your instructor ID here
                questionList // list of questions
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
                    sendSurveyToServer(survey)
                    questionList.clear()
                    questionListAdapter.notifyDataSetChanged()
                }
                setNegativeButton("Cancel", null)
                show()
            }

            //sendSurveyToServer(survey)
        }
    }

    private fun sendSurveyToServer(survey: Survey) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apiSendSurveyToServer(survey) }
                println(response.body())
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
