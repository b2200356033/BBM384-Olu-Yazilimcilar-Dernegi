package com.example.oyd.Fragments.AdminPageFragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.example.oyd.R
import com.example.oyd.Users.Admin
import com.example.oyd.Users.DepartmentManager
import com.example.oyd.Users.Instructor
import com.example.oyd.Users.Student
import com.example.oyd.databinding.FragmentAddNewUserBinding
import com.example.oyd.databinding.FragmentCreateCoursesBinding
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddNewUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddNewUserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var role:String
    private lateinit var userNameBox:TextInputEditText
    private lateinit var userSurnameBox:TextInputEditText
    private lateinit var userEmailBox:TextInputEditText
    private lateinit var userPasswordBox:TextInputEditText
    private var _binding:FragmentAddNewUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userTypes = listOf<String>("Student","Instructor","Department Manager","Admin")
        val autoComplete:AutoCompleteTextView = binding.autoCompleteRoles
        val adapter= ArrayAdapter(requireContext(),R.layout.list_item,userTypes)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener{
                adapterView, view, i, l ->
            role =adapterView.getItemAtPosition(i).toString()
        }
        binding.addUserBtn.setOnClickListener {
            val userName:String = binding.userNameEditText.toString()
            val userSurName:String = binding.userSurnameEditText.toString()
            val userEmail:String = binding.userEmailEditText.toString()
            val userPassword:String = binding.userPasswordEditText.toString()
            if(role.equals("Student")){
                val student = Student(userName,userSurName,userEmail,userEmail,"")
                //send object to database
            }
            else if(role.equals("Instructor")){
                val instructor = Instructor(userName,userSurName,userEmail,userEmail,"")
                //send object to database
            }
            else if(role.equals("Department Manager")){
                val dm = DepartmentManager(userName,userSurName,userEmail,userEmail,"")
                //send object to database
            }
            else{
                val admin = Admin(userName,userSurName,userEmail,userEmail,"")
                //send object to database
            }
            //if successful, then create success dialog, else show error, for now, it will be always successful
            val dialogBinding = layoutInflater.inflate(R.layout.course_creation_successful_dialog, null)
            val myDialog = this.context?.let { it1 -> Dialog(it1) }
            myDialog?.setContentView(dialogBinding)
            myDialog?.setCancelable(true)
            var textView=myDialog?.findViewById<TextView>(R.id.successMessage)
            textView?.setText("User is added successfully")
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
            //to reset the boxes
            binding.userNameEditText.setText("")
            binding.userSurnameEditText.setText("")
            binding.userEmailEditText.setText("")
            binding.userPasswordEditText.setText("")
            binding.autoCompleteRoles.setText(null)
            binding.autoCompleteRoles.isFocusable = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddNewUserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddNewUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}