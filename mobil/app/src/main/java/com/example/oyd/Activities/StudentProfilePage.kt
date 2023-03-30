package com.example.oyd.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.oyd.R
import com.example.oyd.databinding.ActivitySignUpPageBinding
import com.example.oyd.databinding.ActivityStudentProfilePageBinding

class StudentProfilePage : AppCompatActivity() {
    lateinit var binding : ActivityStudentProfilePageBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout:DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentProfilePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        drawerLayout = binding.studentDrawerLayout
        val navView = binding.navView

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_profile -> Toast.makeText(applicationContext,"Clicked Profile",Toast.LENGTH_SHORT).show()
                R.id.nav_addCourses -> Toast.makeText(applicationContext,"Clicked Add Courses",Toast.LENGTH_SHORT).show()
                R.id.nav_viewCourseList -> Toast.makeText(applicationContext,"Clicked View Course List",Toast.LENGTH_SHORT).show()
                R.id.nav_weeklySchedule -> Toast.makeText(applicationContext,"Clicked Weekly Schedule",Toast.LENGTH_SHORT).show()
                R.id.nav_notifications -> Toast.makeText(applicationContext,"Clicked Notifications",Toast.LENGTH_SHORT).show()
                R.id.nav_evaluateCourses -> Toast.makeText(applicationContext,"Clicked Evaluate Courses",Toast.LENGTH_SHORT).show()
                R.id.nav_viewResults -> Toast.makeText(applicationContext,"Clicked View Evaluation Results",Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> Toast.makeText(applicationContext,"Clicked Logout",Toast.LENGTH_SHORT).show()

            }
            true

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun replaceFragment(fragment : Fragment, title : String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.studentFragmentContainer,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

}