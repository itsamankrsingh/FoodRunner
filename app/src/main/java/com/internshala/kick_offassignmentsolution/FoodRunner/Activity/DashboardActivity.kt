package com.internshala.kick_offassignmentsolution.FoodRunner.Activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.internshala.kick_offassignmentsolution.FoodRunner.Fragment.*
import com.internshala.kick_offassignmentsolution.R

class DashboardActivity : AppCompatActivity() {

    /*Declaring the textview used for displaying the data*/
    lateinit var txtData: TextView
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    var previousMenuItem: MenuItem? = null

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

        openHome()

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

            /*checkingif previous item is checked or not*/
            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }

            it?.isCheckable = true
            it?.isChecked = true
            previousMenuItem = it

            when (it.itemId) {
                R.id.menuHomePage -> {
                    openHome()
                    drawerLayout.closeDrawers()
                }
                R.id.menuProfilePage -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, ProfileFragment())
                        .commit()
                    supportActionBar?.title = "My Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.menuFavoritesPage -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FavoriteFragment())
                        .commit()
                    supportActionBar?.title = "Favorite Restaurants"
                    drawerLayout.closeDrawers()
                }
                R.id.menuOrderHistory -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, OrderHistoryFragment())
                        .commit()
                    supportActionBar?.title = "My Previous Orders"
                    drawerLayout.closeDrawers()
                }
                R.id.menuFaqPage -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, FaqFragment())
                        .commit()
                    supportActionBar?.title = "Frequently Asked Questions"
                    drawerLayout.closeDrawers()
                }
                //add dialog alert box to show logout message
                R.id.menuLogout -> {
                    var builder = AlertDialog.Builder(this@DashboardActivity)
                    builder.setTitle("Confirmation")
                    builder.setMessage("Are you sure you want to exit?")
                    builder.setPositiveButton(
                        "Yes",
                        DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(
                                this@DashboardActivity,
                                "You are Logged Out!",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
                            finish()
                        })
                    builder.setNegativeButton(
                        "Cancel",
                        DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(
                                this@DashboardActivity,
                                "You are canceled the action!",
                                Toast.LENGTH_SHORT
                            ).show()
                            it.isChecked = false
                            openHome()
                            drawerLayout.closeDrawers()
                        })
                    builder.create()
                    builder.show()
                }
            }
            return@setNavigationItemSelectedListener true
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

    fun openHome() {
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = "All Restaurants"
        navigationView.setCheckedItem(R.id.menuHomePage)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)

        when (frag) {
            !is HomeFragment -> openHome()

            else -> super.onBackPressed()
        }
    }
}
