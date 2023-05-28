package com.example.oyd.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.oyd.Fragments.StudentPageFragments.*
import com.example.oyd.R
import com.example.oyd.databinding.ActivityStudentProfilePageBinding
import de.hdodenhof.circleimageview.CircleImageView

class StudentProfilePage : AppCompatActivity() {
    private lateinit var binding: ActivityStudentProfilePageBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var userName: TextView
    var userID: Long=0
    private lateinit var userEmail: TextView
    private lateinit var userImage: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentProfilePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        drawerLayout = binding.studentDrawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Find the TextViews and ImageView in the header
        val headerView = binding.navView.getHeaderView(0)
        userName = headerView.findViewById(R.id.user_name)
        userEmail = headerView.findViewById(R.id.user_email)
        userImage = headerView.findViewById(R.id.user_image)

        // Get the SharedPreferences
        val sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

        // Get the stored user data
        userID = sharedPref.getString("id","")?.toLong()!!
        val name = sharedPref.getString("name", "")
        val surname = sharedPref.getString("surname", "")
        val email = sharedPref.getString("email", "")
        val photo = sharedPref.getString("photo", "")

        // Update the TextViews and ImageView
        userName.text = "$name $surname" // Concatenating name and surname
        userEmail.text = email

        // Use Glide to load the image from the URL
        // Glide.with(this).load(photo).into(userImage)

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> Toast.makeText(applicationContext, "Clicked Profile", Toast.LENGTH_SHORT).show()
                R.id.nav_addCourses -> replaceFragment(AddCourseFragment(), menuItem.title.toString())
                R.id.nav_viewCourseList -> replaceFragment(ViewCoursesFragment(), menuItem.title.toString())
                R.id.nav_weeklySchedule -> replaceFragment(WeeklyScheduleFragment(), menuItem.title.toString())
                R.id.nav_notifications -> replaceFragment(AddCourseFragment(), menuItem.title.toString())
                R.id.nav_evaluateCourses -> replaceFragment(EvaluateCoursesFragment(), menuItem.title.toString())
                R.id.nav_viewResults -> replaceFragment(ViewEvaluationResultsStudentFragment(), menuItem.title.toString())
                R.id.nav_logout -> Toast.makeText(applicationContext, "Clicked Logout", Toast.LENGTH_SHORT).show()
            }
            binding.studentDrawerLayout.closeDrawers()
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
        fragmentTransaction.replace(R.id.studentFragmentContainer, fragment)
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
    fun getStudentId(): Long {
        return userID
    }
}
