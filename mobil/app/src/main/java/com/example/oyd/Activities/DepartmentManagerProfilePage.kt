package com.example.oyd.Activities

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.oyd.Fragments.DepartmentManagerPageFragments.*
import com.example.oyd.R
import com.example.oyd.databinding.ActivityDepartmentManagerProfilePageBinding
import de.hdodenhof.circleimageview.CircleImageView

class DepartmentManagerProfilePage : AppCompatActivity() {
    private lateinit var binding: ActivityDepartmentManagerProfilePageBinding
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView
    private lateinit var userImage: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartmentManagerProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.departmentManagerDrawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val headerView = binding.navView.getHeaderView(0)
        userName = headerView.findViewById(R.id.user_name)
        userEmail = headerView.findViewById(R.id.user_email)
        userImage = headerView.findViewById(R.id.user_image)

        val sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

        val name = sharedPref.getString("name", "")
        val surname = sharedPref.getString("surname", "")
        val email = sharedPref.getString("email", "")
        val photo = sharedPref.getString("photo", "")

        userName.text = "$name $surname"
        userEmail.text = email

        // Use Glide to load the image from the URL
        // Glide.with(this).load(photo).into(userImage)

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> Toast.makeText(applicationContext, "Clicked Profile", Toast.LENGTH_SHORT).show()
                R.id.nav_view_survey_results -> replaceFragment(ViewSurveyResultsFragment(), menuItem.title.toString())
                R.id.nav_evaluate_surveys -> replaceFragment(EvaluateSurveysFragment(), menuItem.title.toString())
                R.id.nav_manage_instructors -> replaceFragment(ManageInstructorsFragment(), menuItem.title.toString())
                R.id.nav_view_eval_results -> replaceFragment(ViewEvaluationResultsFragment(), menuItem.title.toString())
                R.id.nav_re_evaluate_surveys -> replaceFragment(ReEvaluateSurveysFragment(), menuItem.title.toString())
                R.id.nav_share_results -> replaceFragment(ShareResultsWithInstructorsFragment(), menuItem.title.toString())
                R.id.nav_upload_resources -> replaceFragment(UploadResourcesFragment(), menuItem.title.toString())
                R.id.nav_download_resources -> replaceFragment(DownloadResourcesFragment(), menuItem.title.toString())
                R.id.nav_share_newsletter -> replaceFragment(ShareNewsletterFragment(), menuItem.title.toString())
                R.id.nav_logout -> logout(this)
            }
            binding.departmentManagerDrawerLayout.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.departmentManagerFragmentContainer, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    fun logout(activity: Activity) {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
        //
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("YES",
            DialogInterface.OnClickListener { dialogInterface, i ->
                activity.finishAffinity()
                System.exit(0)
            })
        builder.setNegativeButton("NO",
            DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
        builder.show()
    }
}
