package com.example.parkshare_new

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.parkshare_new.R
import com.example.parkshare_new.models.Parking

class HomepageActivity : AppCompatActivity() {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val navHostFragment: NavHostFragment? = supportFragmentManager.findFragmentById(R.id.navHostHomepage) as? NavHostFragment
        navController = navHostFragment?.navController
        navController?.let { NavigationUI.setupActionBarWithNavController(this, it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                navController?.popBackStack()
            }
            R.id.miActionBarProfile -> {
                navController?.navigate(R.id.action_global_userProfileFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)

        fun onParkingClicked(parking: Parking?)
    }
}