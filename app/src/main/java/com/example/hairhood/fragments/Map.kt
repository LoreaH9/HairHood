package com.example.hairhood.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.hairhood.databinding.FragmentMapBinding



class Map : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var marker: Marker
    lateinit var binding: FragmentMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMapBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
        crearFargment()
    }

    private fun crearFargment() {
        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        createMarker()

        mMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val intent = Intent(activity, SelectorPeluquero::class.java)
        startActivity(intent)
        
        return false
    }


    private fun createMarker(){
        val coordenadas = LatLng(43.25781971657556, -2.9024068947132546)
        val marker = MarkerOptions().position(coordenadas)
        //.title()
        //el marcador que sea una foto
        //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_favorite))

        mMap.addMarker(marker
            .icon(BitmapDescriptorFactory.fromBitmap(icono()))
//            .icon(BitmapDescriptorFactory.fromBitmap(bmp))
//            .anchor(0.5f, 1F)
        )

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas, 18f),
            4000,
            null
        )

    }

    private fun icono(): Bitmap {
        val height = 200
        val width = 200
        //IMAGEN REDONDA
        //val bitmap = BitmapFactory.decodeResource(context!!.resources, R.mipmap.ic_prueba)
        val bitmap = BitmapFactory.decodeResource(context!!.resources, R.drawable.prueba)
        return Bitmap.createScaledBitmap(bitmap, width, height, false)

        /*val bitmap = BitmapFactory.decodeResource(context!!.resources, R.drawable.prueba)
        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(
            resources, bitmap)
        val roundPx = bitmap.width.toFloat() * 0.06f
        roundedBitmapDrawable.cornerRadius = roundPx
        return bitmap*/

    }
}




