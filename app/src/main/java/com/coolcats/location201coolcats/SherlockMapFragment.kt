package com.coolcats.location201coolcats

import android.content.Context
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SherlockMapFragment : Fragment(), OnMapReadyCallback {


    private lateinit var mapInterface: MapFragmentInterface

    interface MapFragmentInterface {
        fun mapReady()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mapInterface = context as MainActivity
    }

    fun updateLocation(location: Location){
        Log.d("TAG_X", "Location received")
        if(this::map.isInitialized){
            val latLng = LatLng(location.latitude, location.longitude)
            map.addMarker(MarkerOptions().position(latLng).title("This is my position!"))
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        } else
            Log.d("TAG_X", "not initialized..")
    }
    private lateinit var map: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sherlock_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
    }
}