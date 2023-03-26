package com.example.oyd.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.oyd.Fragments.AdminSignUp
import com.example.oyd.Fragments.DepartmentManagerSignUp
import com.example.oyd.Fragments.InstructorSignUp
import com.example.oyd.Fragments.StudentSignUp

class UserFragmentAdapter(fragmentManager: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return StudentSignUp()
            1 -> return InstructorSignUp()
            2 -> return DepartmentManagerSignUp()
            3 -> return AdminSignUp()
            else -> StudentSignUp()
        }
        return StudentSignUp()
    }
}