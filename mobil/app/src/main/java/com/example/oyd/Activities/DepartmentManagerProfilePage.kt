package com.example.oyd.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oyd.databinding.ActivityDepartmentManagerProfilePageBinding
import com.example.oyd.databinding.ActivityStudentProfilePageBinding

class DepartmentManagerProfilePage : AppCompatActivity() {
    lateinit var binding : ActivityDepartmentManagerProfilePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartmentManagerProfilePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}