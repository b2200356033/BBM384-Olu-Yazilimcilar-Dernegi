package com.example.oyd.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.oyd.R
import com.example.oyd.databinding.ActivityDepartmentManagerProfilePageBinding

class DepartmentManagerProfilePage : AppCompatActivity() {
    private lateinit var binding: ActivityDepartmentManagerProfilePageBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartmentManagerProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the navigation drawer
        val drawerLayout = binding.departmentManagerDrawerLayout
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        binding.departmentManagerDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up navigation menu item clicks
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    // Handle profile click
                    Toast.makeText(applicationContext,"Clicked Profile",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_manage_courses -> {
                    // Handle manage courses click
                    Toast.makeText(applicationContext,"Clicked Manage Courses",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_view_survey_results -> {
                    // Handle view survey results click
                    Toast.makeText(applicationContext,"Clicked View Survey Results",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_evaluate_surveys -> {
                    // Handle evaluate surveys click
                    Toast.makeText(applicationContext,"Clicked Evaluate Surveys",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_manage_instructors -> {
                    // Handle manage instructors click
                    Toast.makeText(applicationContext,"Clicked Manage Instructors",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_view_eval_results -> {
                    // Handle view evaluation results click
                    Toast.makeText(applicationContext,"Clicked View Evaluation Results",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_re_evaluate_surveys -> {
                    // Handle re-evaluate surveys click
                    Toast.makeText(applicationContext,"Clicked Re-Evaluate Surveys",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_share_results -> {
                    // Handle share results with instructors click
                    Toast.makeText(applicationContext,"Clicked Share Results with Instructors",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_upload_resources -> {
                    // Handle upload resources click
                    Toast.makeText(applicationContext,"Clicked Upload Resources",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_download_resources -> {
                    // Handle download resources click
                    Toast.makeText(applicationContext,"Clicked Download Resources",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_share_newsletter -> {
                    // Handle share newsletter click
                    Toast.makeText(applicationContext,"Clicked Share Newsletter",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_logout -> {
                    // Handle logout click
                    Toast.makeText(applicationContext,"Clicked Logout",Toast.LENGTH_SHORT).show()
                }
            }
            binding.departmentManagerDrawerLayout.closeDrawers()
            true
        }
    }

    // Handle toggle button clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
