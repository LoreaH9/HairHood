package com.example.hairhood.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.hairhood.R
import com.example.hairhood.activities.SelectorPeluquero
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class Map : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private lateinit var mMap: GoogleMap
    private var locationPermissionGranted = false
    private var uno = LatLng(43.25471423656013, -2.904199867091976)
    private var dos = LatLng(43.258482935494406, -2.9049796343857333)
    private var tres = LatLng(43.25781971657556, -2.9024068947132546)
    private var cuatro = LatLng(43.25953948584263, -2.899894325785889)
    private val locationArrayList = ArrayList<LatLng>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //val locationArrayList: ArrayList<LatLng>? = null
        locationArrayList.add(uno)
        locationArrayList.add(dos)
        locationArrayList.add(tres)
        locationArrayList.add(cuatro)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        getLocationPermission()

        if (locationPermissionGranted) {
            mMap.isMyLocationEnabled = true
        } else {
            getLocationPermission()
        }

        mMap.setOnMyLocationChangeListener { location ->
            val yourLocation = CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 16f)
            mMap.animateCamera(yourLocation)
        }

        createMarker()

        mMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val intent = Intent(activity, SelectorPeluquero::class.java)
        startActivity(intent)

        return false
    }


    private fun createMarker(){
        var height = 200
        val width = 200

        val pel1 = resources.getDrawable(R.drawable.corte1) as BitmapDrawable
        val p1 = pel1.bitmap
        val marker1 = Bitmap.createScaledBitmap(p1, width, height, false)

        val pel2 = resources.getDrawable(R.drawable.corte2) as BitmapDrawable
        val p2 = pel2.bitmap
        val marker2 = Bitmap.createScaledBitmap(p2, width, height, false)

        val pel3 = resources.getDrawable(R.drawable.corte3) as BitmapDrawable
        val p3 = pel3.bitmap
        val marker3 = Bitmap.createScaledBitmap(p3, width, height, false)

        val pel4= resources.getDrawable(R.drawable.corte4) as BitmapDrawable
        val p4 = pel4.bitmap
        val marker4 = Bitmap.createScaledBitmap(p4, width, height, false)


        var MUno = MarkerOptions()
            .position(uno)
            .icon(BitmapDescriptorFactory
                .fromBitmap(marker1)
            )

        var MDos = MarkerOptions()
            .position(dos)
            .icon(BitmapDescriptorFactory
                .fromBitmap(marker2)

            )

        var MTres = MarkerOptions()
            .position(tres)
            .icon(BitmapDescriptorFactory
                .fromBitmap(marker3)
            )

        var MCuatro = MarkerOptions()
            .position(cuatro)
            .icon(BitmapDescriptorFactory
                .fromBitmap(marker4)
            )

        for (i in 0 until locationArrayList.size) {
            //Marcador de los peluqueros
            //val marker = MarkerOptions().position(locationArrayList[i])
            mMap.addMarker(MUno)
            mMap.addMarker(MDos)
            mMap.addMarker(MTres)
            mMap.addMarker(MCuatro)

        }
    }


    private fun getLocationPermission() {

        if (ContextCompat.checkSelfPermission(activity!!.baseContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1)
        }
    }
}





