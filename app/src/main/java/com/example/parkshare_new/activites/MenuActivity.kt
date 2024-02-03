package com.example.parkshare_new.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.parkshare_new.R
import com.example.parkshare_new.modules.addParking.AddParkingFragment
import com.example.parkshare_new.modules.parkingLots.ParkingListActivity
import com.example.parkshare_new.modules.parkingLots.ParkingLotsFragment
import com.example.parkshare_new.modules.userProfile.UserProfileFragment

class MenuActivity : AppCompatActivity() {

    var parkingLotsFragment: ParkingLotsFragment? = null
    var addParkingFragment: AddParkingFragment? = null
    var userProfileFragment: UserProfileFragment? = null

    var parkingLotsButton: Button? = null
    var addParkingButton: Button? = null
    var userProfileButton: Button? = null

    var inDisplayFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        parkingLotsFragment = ParkingLotsFragment()
        addParkingFragment = AddParkingFragment()
        userProfileFragment = UserProfileFragment()

        parkingLotsButton = findViewById(R.id.btnParkingLots)
        addParkingButton = findViewById(R.id.btnAddParking)
        userProfileButton = findViewById(R.id.btnUserProfile)

        parkingLotsButton?.setOnClickListener {
            parkingLotsFragment?.let {
                displayFragment(it)
            }
        }

        addParkingButton?.setOnClickListener {
            addParkingFragment?.let {
                displayFragment(it)
            }
        }

        userProfileButton?.setOnClickListener {
            userProfileFragment?.let {
                displayFragment(it)
            }
        }

        parkingLotsFragment?.let { displayFragment(it)}
        inDisplayFragment = parkingLotsFragment
    }

    fun displayFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fvMainFragment, fragment)

        inDisplayFragment?.let {
            transaction.remove(it)
        }

        transaction.addToBackStack("TAG")
        transaction.commit()
        inDisplayFragment = fragment
    }
}