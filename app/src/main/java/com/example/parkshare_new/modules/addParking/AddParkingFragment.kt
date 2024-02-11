package com.example.parkshare_new.modules.addParking

import android.os.Bundle
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import com.example.parkshare_new.R
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking

class AddParkingFragment : Fragment() {

    private var addressTextField: EditText? = null
    private var cityTextField: EditText? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_parking, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        addressTextField = view.findViewById(R.id.signinNameVal)
        cityTextField = view.findViewById(R.id.sininCityVal)
        saveButton = view.findViewById(R.id.btnSaveSignin)
        cancelButton = view.findViewById(R.id.btnCancelSignin)

        saveButton?.setOnClickListener {
            val address = addressTextField?.text.toString()
            val city = cityTextField?.text.toString()

            val parking = Parking(address, "", city, false)
            Model.instance.addParking(parking) {
                Navigation.findNavController(it).popBackStack(R.id.parkingLotsFragment, false)
            }
        }

        cancelButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }
}