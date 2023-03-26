package com.example.oyd.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oyd.databinding.ActivityAdminProfilePageBinding
import com.example.oyd.databinding.ActivityStudentProfilePageBinding

class AdminProfilePage : AppCompatActivity() {
    lateinit var binding : ActivityAdminProfilePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfilePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}