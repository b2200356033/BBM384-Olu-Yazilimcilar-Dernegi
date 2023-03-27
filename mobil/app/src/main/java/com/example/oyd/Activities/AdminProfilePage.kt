package com.example.oyd.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.oyd.R
import com.example.oyd.databinding.ActivityAdminProfilePageBinding

class AdminProfilePage : AppCompatActivity() {
    private lateinit var binding: ActivityAdminProfilePageBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout = binding.adminDrawerLayout

        toggle  = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        binding.adminDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    // Handle profile click
                    Toast.makeText(applicationContext,"Clicked Profile",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_manage_passwords -> {
                    // Handle manage passwords click
                    Toast.makeText(applicationContext,"Clicked Manage Passwords",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_manage_roles -> {
                    // Handle manage roles click
                    Toast.makeText(applicationContext,"Clicked Manage Roles",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_manage_emails -> {
                    // Handle manage emails click
                    Toast.makeText(applicationContext,"Clicked Manage Emails",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_add_courses -> {
                    // Handle add courses click
                    Toast.makeText(applicationContext,"Clicked Add Courses",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_start_semester -> {
                    // Handle start semester click
                    Toast.makeText(applicationContext,"Clicked Start Semester",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_manage_eval_forms -> {
                    // Handle manage evaluation forms click
                    Toast.makeText(applicationContext,"Clicked Manage Evaluation Forms",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_check_eval_forms -> {
                    // Handle check evaluation forms click
                    Toast.makeText(applicationContext,"Clicked Check Evaluation Forms",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_add_user -> {
                    // Handle add user click
                    Toast.makeText(applicationContext,"Clicked Add User",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_delete_user -> {
                    // Handle delete user click
                    Toast.makeText(applicationContext,"Clicked Delete User",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_ban_user -> {
                    // Handle ban user click
                    Toast.makeText(applicationContext,"Clicked Ban User",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_logout -> {
                    // Handle logout click
                    Toast.makeText(applicationContext,"Clicked Logout",Toast.LENGTH_SHORT).show()
                }
            }
            binding.adminDrawerLayout.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
