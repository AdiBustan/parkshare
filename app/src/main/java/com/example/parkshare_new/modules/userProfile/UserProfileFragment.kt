package com.example.parkshare_new.modules.userProfile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkshare_new.HomepageActivity
import com.example.parkshare_new.R
import com.example.parkshare_new.dao.UserDatabase
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.modules.parkingLots.ParkingLotsFragmentDirections
import com.example.parkshare_new.modules.parkingLots.adapter.ParkingLotsRecyclerAdapter


class UserProfileFragment : Fragment() {
    var parkingLotsRecyclerView: RecyclerView? = null
    var parkingLots: List<Parking>? = null
    var adapter : ParkingLotsRecyclerAdapter? = null
    val database = UserDatabase.getInstance(requireContext().applicationContext)
    val userDao = database.userDao()
    private val currUser = userDao.getUser()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        adapter = ParkingLotsRecyclerAdapter(parkingLots)

        Model.instance.getAllParkingLotsPerUser(currUser.email) {
            this.parkingLots = parkingLots
            adapter?.parkingLots = parkingLots
            adapter?.notifyDataSetChanged()
        }

        parkingLotsRecyclerView = view.findViewById(R.id.nPostsTxt)
        parkingLotsRecyclerView?.setHasFixedSize(true)

        //set the layout manager and adapter
        parkingLotsRecyclerView?.layoutManager = LinearLayoutManager(context)

//        adapter?.listener = object : HomepageActivity.OnItemClickListener {
//            override fun onItemClick(position: Int) {
//                Log.i("TAG", "ParkingLotsRecyclerAdapter: position clicked on: $position")
//                val parking = parkingLots?.get(position)
//                parking?.let {
//                    val action = ParkingLotsFragmentDirections.actionParkingLotsFragmentToParkingFragment(it.address, it.city, it.avatar)
//                    Navigation.findNavController(view).navigate(action)
//                }
//            }
//
//            override fun onParkingClicked(parking: Parking?) {
//                Log.i("TAG", "PARKING: $parking")
//            }
//        }

        parkingLotsRecyclerView?.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        // TODO - get the username for send
        Model.instance.getAllParkingLotsPerUser(currUser.email) { parkingLots ->
            this.parkingLots = parkingLots
            adapter?.parkingLots = parkingLots
            adapter?.notifyDataSetChanged()
        }
    }
}