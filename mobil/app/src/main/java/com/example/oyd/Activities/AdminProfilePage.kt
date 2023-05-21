package com.example.oyd.Activities

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.oyd.Fragments.AdminPageFragments.*
import com.example.oyd.R
import com.example.oyd.databinding.ActivityAdminProfilePageBinding
import de.hdodenhof.circleimageview.CircleImageView

class AdminProfilePage : AppCompatActivity() {
    private lateinit var binding: ActivityAdminProfilePageBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView
    private lateinit var userImage: CircleImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.adminDrawerLayout
        toggle  = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        binding.adminDrawerLayout.addDrawerListener(toggle)
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
        val name = sharedPref.getString("name", "")
        val surname = sharedPref.getString("surname", "")
        val email = sharedPref.getString("email", "")
        val photo = sharedPref.getString("photo", "")

        // Update the TextViews and ImageView
        userName.text = "$name $surname" // Concatenating name and surname
        userEmail.text = email

        // Use Glide to load the image from the URL
        //Glide.with(this).load(photo).into(userImage)

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> Toast.makeText(applicationContext, "Clicked Profile", Toast.LENGTH_SHORT).show()
                R.id.nav_manage_passwords -> replaceFragment(ManagePasswordsFragment(), menuItem.title.toString())
                R.id.nav_manage_roles -> replaceFragment(ManageRolesFragment(), menuItem.title.toString())
                R.id.nav_manage_emails -> replaceFragment(ManageEmailsFragment(), menuItem.title.toString())
                R.id.nav_add_courses -> {
                    replaceFragment(CreateCoursesFragment(), menuItem.title.toString())
                    Toast.makeText(applicationContext, "Add course fragment", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_start_semester -> replaceFragment(StartSemesterFragment(), menuItem.title.toString())
                R.id.nav_manage_eval_forms -> replaceFragment(ManageEvaluationFormsFragment(), menuItem.title.toString())
                R.id.nav_check_eval_forms -> replaceFragment(CheckEvaluationFormsFragment(), menuItem.title.toString())
                R.id.nav_add_user -> replaceFragment(AddNewUserFragment(), menuItem.title.toString())
                R.id.nav_delete_user -> replaceFragment(DeleteUserFragment(), menuItem.title.toString())
                R.id.nav_ban_user -> replaceFragment(BanUserFragment(), menuItem.title.toString())
                R.id.nav_logout -> Toast.makeText(applicationContext, "Clicked Logout", Toast.LENGTH_SHORT).show()
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

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.adminFragmentContainer, fragment)
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
}
