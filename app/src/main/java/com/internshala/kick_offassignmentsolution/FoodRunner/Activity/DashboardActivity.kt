package com.internshala.kick_offassignmentsolution.FoodRunner.Activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.internshala.kick_offassignmentsolution.FoodRunner.Fragment.HomeFragment
import com.internshala.kick_offassignmentsolution.R

class DashboardActivity : AppCompatActivity() {

    /*Declaring the textview used for displaying the data*/
    lateinit var txtData: TextView
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    /*Life-cycle method*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbarDashboard)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationViewDashboard)
        setSupportToolBar()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@DashboardActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        /*setting up click listener for menu items in drawer*/
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuHomePage -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment())
                        .commit()
                    drawerLayout.closeDrawers()
                }
                R.id.menuProfilePage -> {
                    Toast.makeText(this@DashboardActivity, "Clicked on Profile", Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.menuFavoritesPage -> {
                    Toast.makeText(
                        this@DashboardActivity,
                        "Clicked on Favorite",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.menuOrderHistory -> {
                    Toast.makeText(this@DashboardActivity, "Clicked on Order", Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.menuFaqPage -> {
                    Toast.makeText(this@DashboardActivity, "Clicked on FAQ", Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.menuLogout -> {
                    Toast.makeText(this@DashboardActivity, "Clicked on Logout", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            return@setNavigationItemSelectedListener true
        }

        /*Initialising the textview and linking it with the textview created in XML*/
        txtData = findViewById(R.id.txtData)

        /*Checking whether any data was received through the intent or not*/
        if (intent != null) {

            /*Fetching the details from the intent*/
            val details = intent.getBundleExtra("details")

            /*Getting the value of data from the bundle object*/
            val data = details.getString("data")

            /*Checking the location from which data was sent*/
            if (data == "login") {
                /*Creating the text to be displayed*/
                val text = "Mobile Number : ${details.getString("mobile")} \n " +
                        "Password : ${details.getString("password")}"
                txtData.text = text
            }

            if (data == "register") {
                val text = " Name : ${details.getString("name")} \n " +
                        "Mobile Number : ${details.getString("mobile")} \n " +
                        "Password : ${details.getString("password")} \n " +
                        "Address: ${details.getString("address")}"
                txtData.text = text
            }

            if (data == "forgot") {
                val text = "Mobile Number : ${details.getString("mobile")} \n " +
                        "Email : ${details.getString("email")}"
                txtData.text = text
            }

        } else {
            /*No data was received through the intent*/
            txtData.text = "No data received!!"
        }
    }

    fun setSupportToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
}
