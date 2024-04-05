package com.example.parkshare_new

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.parkshare_new.R
import com.example.parkshare_new.dao.UserDao
import com.example.parkshare_new.dao.UserDatabase
import com.example.parkshare_new.databinding.ActivityHomepageBinding
import com.example.parkshare_new.databinding.ActivityMainBinding
import com.example.parkshare_new.models.FirebaseModel
import com.example.parkshare_new.models.LocalUser
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.modules.userProfile.EditUserProfileFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomepageActivity : AppCompatActivity() {

    private var navController: NavController? = null
    private lateinit var binding: ActivityHomepageBinding
    private var auth: FirebaseAuth = Firebase.auth
    private var database: UserDatabase? = null
    private var userDao: UserDao? = null
    private var currUser: LocalUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)

        database = UserDatabase.getInstance(applicationContext)
        lifecycleScope.launch(Dispatchers.IO)  {
            userDao = database!!.userDao()
            currUser = userDao!!.getUser()

            withContext(Dispatchers.Main) {

            }
        }
        setContentView(binding.root)

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
            R.id.miActionLogout -> {
                userLogoutConfirmation()
            }
            R.id.miActionBarProfile -> {
                navController?.navigate(R.id.action_global_userProfileFragment)
            }
            R.id.miActionBarEditProfile -> {

                //TODO: send the arguments of the curr user
//                val action = EditUserProfileFragmentDirections.actionGlobalEditUserProfileFragment(
//                    , "user@example.com"
//                )
                navController?.navigate(R.id.action_global_editUserProfileFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun userLogoutConfirmation() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to logout?")

        builder.setPositiveButton("Yes") { _, _ ->
            auth.signOut()
            lifecycleScope.launch(Dispatchers.IO)  {
                userDao!!.deleteUser(currUser!!)
            }
            navigateToStartPage()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    fun navigateToStartPage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onParkingClicked(parking: Parking?)
    }
}