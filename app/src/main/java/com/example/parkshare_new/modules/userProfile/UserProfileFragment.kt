package com.example.parkshare_new.modules.userProfile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkshare_new.R
import com.example.parkshare_new.dao.UserDao
import com.example.parkshare_new.dao.UserDatabase
import com.example.parkshare_new.databinding.FragmentUserProfileBinding
import com.example.parkshare_new.models.LocalUser
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.models.Profile
import com.example.parkshare_new.modules.parkingSpots.adapter.ParkingSpotsRecyclerAdapter
import com.example.parkshare_new.services.ImagesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserProfileFragment : Fragment() {

    var userParkingSpotsRecyclerView: RecyclerView? = null
    private var usernameTextView: TextView? = null
    private var faveCityTextView: TextView? = null
    private var avatarImageView: ImageView? = null

    var adapter : ParkingSpotsRecyclerAdapter? = null
    var userParkingSpots: List<Parking>? = null
    private var database: UserDatabase? = null
    private var userDao: UserDao? = null
    private var currUser: LocalUser? = null
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!


    interface OnItemClickListener {
        fun onParkingClick(parking: Parking?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        adapter = ParkingSpotsRecyclerAdapter(userParkingSpots)

        database = UserDatabase.getInstance(requireContext().applicationContext)
        lifecycleScope.launch(Dispatchers.IO)  {
            userDao = database!!.userDao()
            currUser = userDao!!.getUser()

            withContext(Dispatchers.Main) {
                // Call setupUI() after currUser is retrieved and logged
                setupUI()
            }
        }

        userParkingSpotsRecyclerView = binding.rvUserParkingSpotsFragmentList
        userParkingSpotsRecyclerView?.setHasFixedSize(true)

        userParkingSpotsRecyclerView?.layoutManager = LinearLayoutManager(requireContext())

        adapter?.userProfileListener = object : OnItemClickListener {
            override fun onParkingClick(parking: Parking?) {
                Log.i("TAG", "PARKING: $parking")
            }
        }

        userParkingSpotsRecyclerView?.adapter = adapter

        return view
    }

    private fun setupUI() {

        usernameTextView = binding.tvUsernameUserProfileFragment
        faveCityTextView = binding.tvFaveCityUserProfileFragment
        avatarImageView = binding.imAvatarUserProfileFragment

        if (currUser!!.username == "") {
            getUserDetailsFromFirebase()
        } else {
            updateUI(currUser!!.username, currUser!!.faveCity, currUser!!.avatar)
        }

        getUserParkingSpots()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        var profileBtn = menu.findItem(R.id.miActionBarProfile)
        var editBtn = menu.findItem(R.id.miActionBarEditProfile)

        profileBtn.isVisible = false
        editBtn.isVisible = true

        //menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun updateUI(username: String, faveCity: String, avatar: String) {
        usernameTextView?.text = username
        faveCityTextView?.text = faveCity
        ImagesService.loadingImageFromStorage(requireContext(), avatarImageView!!, avatar)

    }

    private fun getUserDetailsFromFirebase() {
        Model.instance.getUserByEmail(currUser!!.email) {profile ->
            val localUser = LocalUser(profile!!.email, profile.userName, profile.faveCity, profile.avatar, System.currentTimeMillis())

            lifecycleScope.launch(Dispatchers.IO)  {
                userDao!!.updateUser(localUser)
            }

            updateUI(profile.userName, profile.faveCity, profile.avatar)
        }
    }

    private fun getUserParkingSpots() {
        Model.instance.getAllParkingSpotsPerUser(this.currUser!!.email) { parkingSpotsPerUser ->
            this.userParkingSpots = parkingSpotsPerUser
            adapter?.parkingSpots = parkingSpotsPerUser
            adapter?.notifyDataSetChanged()
        }
    }
}