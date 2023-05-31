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
import android.view.WindowManager
import android.widget.Button
import android.widget.DatePicker
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.oyd.API.RetrofitClient
import com.example.oyd.Models.Semester
import com.example.oyd.R
import com.example.oyd.databinding.FragmentStartSemesterBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale



class StartSemesterFragment : Fragment() {
    var isUploading = false
    private var _binding: FragmentStartSemesterBinding? = null

    private val binding get() = _binding!!
    private lateinit var startSemesterButton: Button
    private lateinit var startDateEditText: TextInputEditText
    private lateinit var endDateEditText: TextInputEditText
    private lateinit var progressBar:ProgressBar
    private lateinit var progressText:TextView
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

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
        startDateEditText=binding.startDateEditText
        endDateEditText=binding.endDateEditText
        progressBar=binding.progressBar
        progressText=binding.progressText
        startSemesterButton.setOnClickListener {
            if(startDateEditText.text.toString().isEmpty() || endDateEditText.text.toString().isEmpty()){
                Toast.makeText(requireContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val startDate = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(startDateEditText.text.toString())
            val endDate = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(endDateEditText.text.toString())
            if (startDate != null && endDate != null) {
                if(startDate.after(endDate)){
                    Toast.makeText(requireContext(),"Start date cannot be after end date",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            else{
                Toast.makeText(requireContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val semester =Semester(startDateEditText.text.toString(),endDateEditText.text.toString())
            val coroutineScope = CoroutineScope(Dispatchers.Main)
            val job = coroutineScope.launch {
                try {

                    withContext(Dispatchers.Main) {
                        sendingSemester()
                        val response = withContext(Dispatchers.IO) { RetrofitClient.instance.apisendSemesterToServer(semester) }
                        if (response.isSuccessful) {
                            dialogBox(0)
                        } else {
                            dialogBox(-1)
                        }
                    }
                } catch (e: Exception) {
                    println("Exception: $e")
                    dialogBox(-1)
                    Toast.makeText(requireContext(), "Exception: $e", Toast.LENGTH_LONG).show()
                }
            }
            job.invokeOnCompletion {
                completedSending()
                // İşlem tamamlandığında veya iptal edildiğinde yapılacak işlemler
            }

        }

    }
    fun dialogBox(answer: Int){
        val myDialog: Dialog?
        var textView: TextView?

        if(answer==0){
            val dialogBinding = layoutInflater.inflate(R.layout.course_creation_successful_dialog, null)
            myDialog = this.context?.let { it1 -> Dialog(it1) }
            myDialog?.setContentView(dialogBinding)
            myDialog?.setCancelable(true)
            textView=myDialog?.findViewById<TextView>(R.id.successMessage)
            textView?.text = "Semester has started successfully"
        }
        else  {
            val dialogBinding = layoutInflater.inflate(R.layout.error_page, null)
            myDialog = this.context?.let { it1 -> Dialog(it1) }
            myDialog?.setContentView(dialogBinding)
            myDialog?.setCancelable(true)
            textView=myDialog?.findViewById<TextView>(R.id.message)
            textView?.text = "Semester started failed"
        }
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartSemesterBinding.inflate(inflater, container, false)
        return binding.root
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
    private fun sendingSemester() {
        progressBar.visibility = View.VISIBLE
        progressText.visibility = View.VISIBLE
        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        isUploading = true
    }
    private fun completedSending() {
        isUploading = false
        progressBar.visibility = View.GONE
        progressText.visibility = View.INVISIBLE
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
    override fun onResume() {
        super.onResume()

        if (isUploading) {
            progressBar.visibility = View.VISIBLE
            progressText.visibility = View.VISIBLE
        }
    }

}