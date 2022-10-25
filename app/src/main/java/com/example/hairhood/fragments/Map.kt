package com.example.hairhood.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
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
import com.google.firebase.firestore.FirebaseFirestore


class Map : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{



    private lateinit var mMap: GoogleMap

    private var locationPermissionGranted = false
    var nom = ""
    var img = ""
    val db = FirebaseFirestore.getInstance()
   /* private var unoF = BitmapFactory.decodeFile(img)
    private var unoF2 =BitmapDescriptorFactory.fromBitmap(unoF)*/
    private var unoL = LatLng(43.25471423656013, -2.904199867091976)
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
        locationArrayList.add(unoL)
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
        db.collection("peluqueros").document(nom).get().addOnSuccessListener {
            nom = it.get("usuario").toString()
            img = it.get("foto").toString()

        }
        for (i in 0 until locationArrayList.size) {
            //Marcador de los peluqueros
            val marker = MarkerOptions().position(locationArrayList[i])
            mMap.addMarker(marker
                .icon(BitmapDescriptorFactory.fromBitmap(icono()))
            )
        }

//        //Marcador tu posicion
//        val coordenadas = LatLng(43.258859663296036, -2.897823693297347)
//        mMap.addMarker(MarkerOptions().position(coordenadas).title("HEMEN ZAUDE"))
//
//        //Animacion de camara, enfoca a tu localizacion
//        mMap.animateCamera(
//            CameraUpdateFactory.newLatLngZoom(coordenadas, 18f),
//            4000,
//            null
//        )

    }

    private fun icono(): Bitmap {
        val height = 200
        val width = 200
        //IMAGEN REDONDA
        //val bitmap = BitmapFactory.decodeResource(context!!.resources, R.mipmap.ic_prueba)
        val bitmap = BitmapFactory.decodeFile(img)
        return Bitmap.createScaledBitmap(bitmap, width, height, false)

        /*val bitmap = BitmapFactory.decodeResource(context!!.resources, R.drawable.prueba)
        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(
            resources, bitmap)
        val roundPx = bitmap.width.toFloat() * 0.06f
        roundedBitmapDrawable.cornerRadius = roundPx
        return bitmap*/

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




