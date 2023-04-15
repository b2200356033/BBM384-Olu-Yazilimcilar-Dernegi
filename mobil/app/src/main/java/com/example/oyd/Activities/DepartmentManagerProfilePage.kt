package com.example.oyd.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.oyd.Fragments.DepartmentManagerPageFragments.*
import com.example.oyd.R
import com.example.oyd.databinding.ActivityAdminProfilePageBinding
import com.example.oyd.databinding.ActivityDepartmentManagerProfilePageBinding

class DepartmentManagerProfilePage : AppCompatActivity() {
    private lateinit var binding: ActivityDepartmentManagerProfilePageBinding
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartmentManagerProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the navigation drawer
        drawerLayout = binding.departmentManagerDrawerLayout
        toggle  = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
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

                R.id.nav_view_survey_results -> {
                    // Handle manage roles click
                    replaceFragment(ViewSurveyResultsFragment(),menuItem.title.toString())
                }
                R.id.nav_evaluate_surveys -> {
                    // Handle manage emails click
                    replaceFragment(EvaluateSurveysFragment(),menuItem.title.toString())
                }
                R.id.nav_manage_instructors -> {
                    // Handle add courses click
                    replaceFragment(ManageInstructorsFragment(),menuItem.title.toString())
                }
                R.id.nav_view_eval_results -> {
                    // Handle start semester click
                    replaceFragment(ViewEvaluationResultsFragment(),menuItem.title.toString())
                }
                R.id.nav_re_evaluate_surveys -> {
                    // Handle manage evaluation forms click
                    replaceFragment(ReEvaluateSurveysFragment(),menuItem.title.toString())
                }
                R.id.nav_share_results -> {
                    // Handle check evaluation forms click
                    replaceFragment(ShareResultsWithInstructorsFragment(),menuItem.title.toString())
                }
                R.id.nav_upload_resources -> {
                    // Handle add user click
                    replaceFragment(UploadResourcesFragment(),menuItem.title.toString())
                }
                R.id.nav_download_resources -> {
                    // Handle delete user click
                    replaceFragment(DownloadResourcesFragment(),menuItem.title.toString())
                }
                R.id.nav_share_newsletter -> {
                    // Handle ban user click
                    replaceFragment(ShareNewsletterFragment(),menuItem.title.toString())
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
    private fun replaceFragment(fragment : Fragment, title : String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.departmentManagerFragmentContainer,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }
    override fun onBackPressed() {
        if(this.drawerLayout.isDrawerOpen(GravityCompat.START)){
            this.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }

    }
}
