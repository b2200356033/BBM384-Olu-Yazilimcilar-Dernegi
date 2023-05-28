package com.example.oyd.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oyd.Adapters.UserFragmentAdapter
import com.example.oyd.databinding.ActivitySignUpPageBinding
import com.google.android.material.tabs.TabLayoutMediator


class SignUpPage : AppCompatActivity() {
    lateinit var binding : ActivitySignUpPageBinding
    var tabTitle = arrayOf("Student","Instructor","Department Manager","Admin")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_sign_up_page)

        //this part is to switch between different user fragments
        var pager = binding.viewPager2
        var tl = binding.tabLayout
        pager.adapter = UserFragmentAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(tl, pager){
            tab,position ->tab.text = tabTitle[position]
        }.attach()

    }

}