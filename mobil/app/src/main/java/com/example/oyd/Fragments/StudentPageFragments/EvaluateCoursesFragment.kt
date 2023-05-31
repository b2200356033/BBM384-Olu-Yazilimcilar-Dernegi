package com.example.oyd.Fragments.StudentPageFragments
import android.os.Parcel
import android.os.Parcelable
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
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oyd.API.RetrofitClient
import com.example.oyd.Activities.StudentProfilePage
import com.example.oyd.Adapters.CourseAdapter
import com.example.oyd.Models.Course
import com.example.oyd.R
import com.example.oyd.databinding.FragmentAddCourseBinding
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
 * Use the [EvaluateCoursesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EvaluateCoursesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentEvaluateCoursesBinding? = null
    private val binding get() = _binding!!
    var CourseList = ArrayList<Course>()
    private var tempArrayList = ArrayList<Course>()
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
        _binding = FragmentEvaluateCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=binding.courseWithSurveysRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        tempArrayList.addAll(CourseList)
        recyclerView.adapter?.notifyDataSetChanged()
        val courseAdapter = CourseAdapter(requireContext(),tempArrayList)
        courseAdapter.setOnItemClickListener { course ->
            // Handle item click event

            showCustomDialog(course)
        }
        recyclerView.adapter = courseAdapter
        //Retreieve courses from database
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Switch to the IO dispatcher for making the network request
                val activity = requireActivity() as StudentProfilePage
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.instance.apiGetAllCoursesWithSurveyFromServer(activity.getStudentId())
                }

                if (response.isSuccessful) {
                    CourseList = response.body()!!
                    tempArrayList.addAll(CourseList)
                    recyclerView.adapter?.notifyDataSetChanged()


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
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EvaluateCoursesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EvaluateCoursesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun navigateToDisplayEvaluationFragment(courseId:Long) {
        val targetFragment = DisplayEvaluationFragment()
        val activity = requireActivity() as StudentProfilePage
        // Pass the course ID to the target fragment
        val bundle = Bundle().apply {
            putLong("courseId", courseId)
            putLong("studentId",activity.getStudentId())
        }
        targetFragment.arguments = bundle


        // Perform fragment transaction

        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, targetFragment)
        transaction.addToBackStack(null)
        transaction.commit()


    }
    private fun showCustomDialog(course: Course) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.add_course_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val courseNameBox: TextView = dialog.findViewById(R.id.courseNameTxt)
        val questionText:TextView=dialog.findViewById(R.id.dialogQuestionText)
        val enrollBtn: Button = dialog.findViewById(R.id.enrollBtn)
        val cancelBtn: Button = dialog.findViewById(R.id.cancelBtn)
        courseNameBox.text=course.name + "Survey"
        enrollBtn.text="Answer"
        questionText.text="Do you want to fill this course's survey?"
        enrollBtn.setOnClickListener {
            //send the user to DisplayEvaluationObject Fragment with intent and send course object with it
            navigateToDisplayEvaluationFragment(course.id!!)

            dialog.cancel()
        }
        cancelBtn.setOnClickListener {
            dialog.cancel()
        }
    }
}