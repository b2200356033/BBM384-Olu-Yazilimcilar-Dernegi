package com.example.oyd.Fragments.DepartmentManagerPageFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.example.oyd.R
import com.example.oyd.databinding.FragmentCreateCoursesBinding
import com.example.oyd.databinding.FragmentManageInstructorsBinding

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
    private lateinit var instructor: String
    private lateinit var course: String
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
        initView()
        setupAutoComplete()


    }

    private fun setupAutoComplete() {
        // TODO Database den gelen verileri insturctors ve courses listelerine ata

        val instructors = listOf("ABC", "KAYHAN", "TUGBA", "HARUN")
        val courses = listOf("BBM432", "BBM382","BBM371")
        setUpAdapterAndListener(autoCompleteCourses, courses) { item ->
            course = item
        }

        setUpAdapterAndListener(autoCompleteInstructor, instructors) { item ->
            instructor = item
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

    fun initView(){
        autoCompleteInstructor =binding.instructorAuto
        autoCompleteCourses = binding.courseAuto
        assignButton = binding.assign
        assignButton.setOnClickListener {
            // TODO ASSING OR UPDATE . Check update or assign is here or in backend?
            println(course)
            println(instructor)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ManageInstructorsFragment.
         */
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