package com.example.oyd.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.oyd.Fragments.AdminPageFragments.*
import com.example.oyd.R
import com.example.oyd.databinding.ActivityAdminProfilePageBinding

class AdminProfilePage : AppCompatActivity() {
    private lateinit var binding: ActivityAdminProfilePageBinding
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.adminDrawerLayout
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
                    replaceFragment(ManagePasswordsFragment(),menuItem.title.toString())

                }
                R.id.nav_manage_roles -> {
                    // Handle manage roles click
                    replaceFragment(ManageRolesFragment(),menuItem.title.toString())

                }
                R.id.nav_manage_emails -> {
                    // Handle manage emails click
                    replaceFragment(ManageEmailsFragment(),menuItem.title.toString())

                }
                R.id.nav_add_courses -> {
                    // Handle add courses click
                    replaceFragment(CreateCoursesFragment(),menuItem.title.toString())
                    Toast.makeText(this,"Add course fragment",Toast.LENGTH_SHORT).show()
                }
                R.id.nav_start_semester -> {
                    // Handle start semester click
                    replaceFragment(StartSemesterFragment(),menuItem.title.toString())
                }
                R.id.nav_manage_eval_forms -> {
                    // Handle manage evaluation forms click
                    replaceFragment(ManageEvaluationFormsFragment(),menuItem.title.toString())
                }
                R.id.nav_check_eval_forms -> {
                    // Handle check evaluation forms click
                    replaceFragment(CheckEvaluationFormsFragment(),menuItem.title.toString())
                }
                R.id.nav_add_user -> {
                    // Handle add user click
                    replaceFragment(AddNewUserFragment(),menuItem.title.toString())
                }
                R.id.nav_delete_user -> {
                    // Handle delete user click
                    replaceFragment(DeleteUserFragment(),menuItem.title.toString())
                }
                R.id.nav_ban_user -> {
                    // Handle ban user click
                    replaceFragment(BanUserFragment(),menuItem.title.toString())
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
    private fun replaceFragment(fragment : Fragment, title : String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.adminFragmentContainer,fragment)
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
