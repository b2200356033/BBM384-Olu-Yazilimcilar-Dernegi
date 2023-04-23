package com.example.oyd.Fragments.AdminPageFragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import com.example.oyd.R
import com.example.oyd.databinding.FragmentCreateCoursesBinding
import com.example.oyd.databinding.FragmentStartSemesterBinding
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import java.text.SimpleDateFormat
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StartSemesterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartSemesterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentStartSemesterBinding? = null
    private val binding get() = _binding!!
    private lateinit var startSemesterButton: Button
    private lateinit var startDateEditText: TextInputEditText
    private lateinit var endDateEditText: TextInputEditText
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        binding.startDateInputLayout.setEndIconOnClickListener{
            //date picker
            val datePickerDialog = DatePickerDialog(requireContext(),
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                    // Update selected date
                    year = selectedYear
                    month = selectedMonth
                    day = selectedDay

                    // Display selected date
                    binding.startDateEditText.setText("$day/${month + 1}/$year")
                }, year, month, day)

            // Show Date Picker Dialog
            datePickerDialog.show()
        }
        binding.endDateInputLayout.setEndIconOnClickListener {
            //date picker
            val datePickerDialog = DatePickerDialog(requireContext(),
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                    // Update selected date
                    year = selectedYear
                    month = selectedMonth
                    day = selectedDay

                    // Display selected date
                    binding.endDateEditText.setText("$day/${month + 1}/$year")
                }, year, month, day)

            // Show Date Picker Dialog
            datePickerDialog.show()
        }
        startSemesterButton=binding.startSemesterBtn
        startSemesterButton.setOnClickListener {
            if (validateDates(startDateEditText.text.toString(),endDateEditText.text.toString())) {
                //pop up saying it is successful
                val dialogBinding = layoutInflater.inflate(R.layout.course_creation_successful_dialog, null)
                val myDialog = this.context?.let { it1 -> Dialog(it1) }
                myDialog?.setContentView(dialogBinding)
                myDialog?.setCancelable(true)
                var textView=myDialog?.findViewById<TextView>(R.id.successMessage)
                textView?.setText("Semester has started successfully")
                myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                myDialog?.show()
                object : CountDownTimer(3000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        // TODO Auto-generated method stub
                    }

                    override fun onFinish() {
                        // TODO Auto-generated method stub
                        myDialog?.dismiss()
                    }
                }.start()
            }

        }

    }

     fun validateDates(startDateText: String, endDateText:String): Boolean {


        if (startDateText.isEmpty()) {
            println("Start date is required")
            return false
        }

        if (endDateText.isEmpty()) {
            println("End date is required")
            return false
        }

        val startDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(startDateText)
        val endDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(endDateText)

        if (startDate != null && endDate != null) {
            if (endDate.before(startDate)) {
                println("End date must be after start date")
                return false
            }
        }

        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartSemesterBinding.inflate(inflater, container, false)
        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StartSemesterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StartSemesterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}