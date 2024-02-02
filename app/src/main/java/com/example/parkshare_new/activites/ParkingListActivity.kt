package com.example.parkshare_new.activites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.parkshare_new.R
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking
import com.google.firebase.FirebaseApp

class ParkingListActivity : AppCompatActivity() {
    var parkingListView : ListView? = null
    var parkings: MutableList<Parking>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContentView(R.layout.activity_parking_list)

        parkings = Model.instance.parkingLots

        parkingListView  = findViewById(R.id.lvParkingList)
        parkingListView?.adapter = parkingListAdapter(parkings)

        parkingListView?.setOnItemClickListener { parent, view, position, id ->
            Log.i("TAG", "Row was clicked on: $position")
        }
    }

    class parkingListAdapter(val parkingLots: MutableList<Parking>?): BaseAdapter() {
        override fun getCount(): Int {
            return parkingLots?.size ?: 0
            TODO("Not yet implemented")
        }

        override fun getItem(position: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val parking = parkingLots?.get(position)
            var view: View? = null
            if (convertView == null) {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.parking_list_row, parent, false)
                val parkingCheckBox: CheckBox? = view?.findViewById(R.id.cbAvalibleParking)
                parkingCheckBox?.setOnClickListener {
                    (parkingCheckBox.tag as? Int)?.let {tag ->
                        var parking = parkingLots?.get(tag)
                        parking?.isChecked = parkingCheckBox.isChecked ?: false

                    }
                }
            }

            view = view ?: convertView

            val parkingAddressTextView: TextView? = view?.findViewById(R.id.tvParkingAdress)
            val parkingCityTextView: TextView? = view?.findViewById(R.id.tvParkingCity)
            val parkingCheckBox: CheckBox? = view?.findViewById(R.id.cbAvalibleParking)

            parkingAddressTextView?.text = parking?.address
            parkingCityTextView?.text = parking?.city
            parkingCheckBox?.apply {
                isChecked = parking?.isChecked ?: false
                tag = position
            }

            return view!! // ?:convertView
        }

    }

}