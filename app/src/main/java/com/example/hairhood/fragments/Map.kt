package com.example.hairhood.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import com.example.hairhood.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class Map : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    //coordenadas para probar puntos distintos
    /*var uno = LatLng(43.25839014754069, -2.903211572459352)
    var dos = LatLng(43.25629610747372, -2.9024390858037306)
    var tres = LatLng(43.258257292021135, -2.8998748668282484)*/

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
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        createMarker()
    }

    private fun createMarker(){
        val coordenadas = LatLng(43.25781971657556, -2.9024068947132546)
        val marker = MarkerOptions().position(coordenadas)
        //.title()
        //el marcador que sea una foto
        //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_favorite))

        /*val conf = Bitmap.Config.ARGB_8888
        val bmp = Bitmap.createBitmap(80, 80, conf)
        val canvas = Canvas(bmp)

        // paint defines the text color, stroke width and size
        val color = Paint()
        color.setTextSize(35F)
        color.setColor(Color.BLACK)

        // modify canvas
        canvas.drawBitmap( BitmapFactory.decodeResource( resources,
                com.example.hairhood.R.drawable.prueba), 40, 40, color)*/


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
        val bitmap = BitmapFactory.decodeResource(context!!.resources, R.mipmap.ic_prueba)
        return Bitmap.createScaledBitmap(bitmap, width, height, false)

        /*val bitmap = BitmapFactory.decodeResource(context!!.resources, R.drawable.prueba)
        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(
            resources, bitmap)
        val roundPx = bitmap.width.toFloat() * 0.06f
        roundedBitmapDrawable.cornerRadius = roundPx*/

    }



}

