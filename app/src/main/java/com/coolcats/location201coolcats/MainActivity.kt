package com.coolcats.location201coolcats

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var locationManager: LocationManager
    private lateinit var mapFragment: SherlockMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SherlockMapFragment

        Log.d("TAG_X", "OnCreate...")
    }

    private val PERMISSION_CODE = 777

    override fun onStart() {
        super.onStart()
        if(ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            registerLocationListener()
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(ACCESS_FINE_LOCATION), PERMISSION_CODE)
    }

    private val locationLisetner = LocationListener {
       // location_text.text = "${it.latitude}, ${it.longitude}"
        mapFragment.updateLocation(it)
    }

    @SuppressLint("MissingPermission")
    private fun registerLocationListener() {
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                2f, locationLisetner
        )

    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG_X", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG_X", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG_X", "onDestroy")

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSION_CODE){

            if(permissions[0] == ACCESS_FINE_LOCATION){

                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    registerLocationListener()
                else {
                    if(shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)){
                        requestLocationPermission()
                    } else{
                        AlertDialog.Builder(ContextThemeWrapper(this, R.style.ThemeOverlay_AppCompat)).setTitle("Location Permission!")
                                .setMessage("You will need location permission to use this app. Otherwise, please uninstall this app.")
                                .setPositiveButton("Open Settings") { dialog, _ ->
                                    //Implicit Intent
                                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    intent.data = Uri.fromParts("package", packageName, null)
                                    startActivity(intent)

                                    //TODO: Open settings..
                                }.create().show()
                    }



                }
            }


        }
    }


}