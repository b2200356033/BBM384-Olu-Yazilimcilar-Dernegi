package com.example.oyd.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.oyd.Fragments.InstructorPageFragments.CreateSurveyFragment
import com.example.oyd.Fragments.InstructorPageFragments.ReEvaluateResultsFragment
import com.example.oyd.Fragments.InstructorPageFragments.SurveyResultsFragment
import com.example.oyd.Fragments.InstructorPageFragments.ViewSurveysFragment
import com.example.oyd.R
import com.example.oyd.databinding.ActivityInstructorProfilePageBinding
import com.example.oyd.databinding.ActivityStudentProfilePageBinding

class InstructorProfilePage : AppCompatActivity() {
    lateinit var binding : ActivityInstructorProfilePageBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstructorProfilePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        drawerLayout = binding.instructorDrawerLayout
        val navView = binding.navView

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_profile -> Toast.makeText(applicationContext,"Clicked Profile", Toast.LENGTH_SHORT).show()
                R.id.nav_createCourses -> replaceFragment(CreateSurveyFragment(),it.title.toString())
                R.id.nav_viewSurveys -> replaceFragment(ViewSurveysFragment(),it.title.toString())
                R.id.nav_surveyResults -> replaceFragment(SurveyResultsFragment(),it.title.toString())
                R.id.nav_notifications -> Toast.makeText(applicationContext,"Clicked notifications", Toast.LENGTH_SHORT).show()
                R.id.nav_reevaluateResults -> replaceFragment(ReEvaluateResultsFragment(),it.title.toString())
                R.id.nav_logout -> Toast.makeText(applicationContext,"Clicked Logout", Toast.LENGTH_SHORT).show()

            }
            true

        }

    }

    private fun replaceFragment(fragment : Fragment, title : String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.instructorFragmentContainer,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }
}