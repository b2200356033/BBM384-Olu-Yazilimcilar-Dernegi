package com.example.oyd.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oyd.databinding.ActivityInstructorProfilePageBinding
import com.example.oyd.databinding.ActivityStudentProfilePageBinding

class InstructorProfilePage : AppCompatActivity() {
    lateinit var binding : ActivityInstructorProfilePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstructorProfilePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}