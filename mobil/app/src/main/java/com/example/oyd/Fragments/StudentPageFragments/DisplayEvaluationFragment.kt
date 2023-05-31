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
import com.example.oyd.Adapters.QuestionAdapter
import com.example.oyd.Models.Course
import com.example.oyd.Models.Question
import com.example.oyd.Models.Survey
import com.example.oyd.R
import com.example.oyd.databinding.FragmentDisplayEvaluationBinding
import com.example.oyd.databinding.FragmentEvaluateCoursesBinding
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
 * Use the [DisplayEvaluationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisplayEvaluationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentDisplayEvaluationBinding? = null
    private val binding get() = _binding!!
    private var surveyQuestionList = ArrayList<String>()
    private var surveyAnswerList = ArrayList<Float>()
    private var questionObjectList = ArrayList<Question>()
    private lateinit var survey: Survey
    private lateinit var recyclerView: RecyclerView
    private lateinit var submitBtn: Button

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
        _binding = FragmentDisplayEvaluationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=binding.evaluationQuestionRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val courseAdapter = QuestionAdapter(questionObjectList)
        recyclerView.adapter=courseAdapter
        val courseId = arguments?.getLong("courseId", 0L)
        val studentId = arguments?.getLong("studentId",0L)
        println("CourseId retrieved is:"+courseId)
        // Use the course ID as needed
        if (courseId != null) {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    // Switch to the IO dispatcher for making the network request
                    val response = withContext(Dispatchers.IO) {
                        RetrofitClient.instance.apiGetSurveyWithCourseID(courseId)
                    }

                    if (response.isSuccessful) {

                        println("survey retreived successfully, survey question list:"+ response.body()!!)
                        surveyQuestionList.addAll(response.body()!!)
                        for(questionString in surveyQuestionList){
                            questionObjectList.add(Question(questionString,0f))
                            println("created question object")
                            recyclerView.adapter?.notifyDataSetChanged()
                        }

                    } else {
                        // Handle error response
                        // ...
                    }
                } catch (e: Exception) {
                    // Handle the failure scenario
                    // ...
                }

            }

        }
        submitBtn=binding.surveySubmitBtn
        submitBtn.setOnClickListener{
            //send float answers of the question objects to server
            for(question in questionObjectList){
                surveyAnswerList.add(question.rating)
            }
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    // Switch to the IO dispatcher for making the network request
                    val activity = requireActivity() as StudentProfilePage
                    val response = withContext(Dispatchers.IO) {
                        RetrofitClient.instance.apiSendEvaluationOfSurveyWithStudentAndCourseID(studentId!!,courseId!!,surveyAnswerList)
                    }

                    if (response.isSuccessful) {
                       Toast.makeText(context,"Evaluation sent succssfully",Toast.LENGTH_LONG).show()
                        //showCustomDialog()
                    } else {
                        // Handle error response
                        // ...
                    }
                } catch (e: Exception) {
                    // Handle the failure scenario
                    // ...
                }

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
         * @return A new instance of fragment DisplayEvaluationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DisplayEvaluationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun showCustomDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.course_creation_successful_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val successMessageTextView: TextView = dialog.findViewById(R.id.successMessage)
        successMessageTextView.text="You have evaluated this course successfully"
    }
}