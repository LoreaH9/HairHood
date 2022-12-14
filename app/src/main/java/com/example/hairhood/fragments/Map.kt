package com.example.hairhood.fragments

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class Map : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

//    companion object {
//        var desdeUno = false
//        var desdeDos = false
//        var desdeTres = false
//        var desdeCuatro = false
//        var desdeCinco = false
//        var desdeSeis = false
//        var desdeSiete = false
//        var desdeOcho = false
//    }

    private lateinit var mMap: GoogleMap
    private var locationPermissionGranted = false
    private var uno = LatLng(43.25471423656013, -2.904199867091976)
    private var dos = LatLng(43.258482935494406, -2.9049796343857333)
    private var tres = LatLng(43.25781971657556, -2.9024068947132546)
    private var cuatro = LatLng(43.25953948584263, -2.899894325785889)
    private var cinco = LatLng(43.275170, -2.951951)
    private var seis = LatLng(43.237164, -2.888951)
    private var siete = LatLng(43.252665, -2.885288)
    private var ocho = LatLng(43.310618, -3.008234)
    private val locationArrayList = ArrayList<LatLng>()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }
    var db= Firebase.firestore


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

       /* db.collection("peluqueros")
            .get()
            .addOnSuccessListener{result->for( document in result){
                Log.d(TAG, "${document.id} => ${document.data["foto"]}")

            }}*/


        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //val locationArrayList: ArrayList<LatLng>? = null
        locationArrayList.add(uno)
        locationArrayList.add(dos)
        locationArrayList.add(tres)
        locationArrayList.add(cuatro)
        locationArrayList.add(cinco)
        locationArrayList.add(seis)
        locationArrayList.add(siete)
        locationArrayList.add(ocho)
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
//
//    var MUno = MarkerOptions()
//    var MDos = MarkerOptions()
//    var MTres = MarkerOptions()
//    var MCuatro = MarkerOptions()
//    var MCinco = MarkerOptions()
//    var MSeis = MarkerOptions()
//    var MSiete = MarkerOptions()
//    var MOcho = MarkerOptions()
//

    override fun onMarkerClick(marker: Marker): Boolean {

        val intent = Intent(activity, SelectorPeluquero::class.java)
        intent.putExtra("usuario", marker.title)
        intent.putExtra("foto", marker.id)
        startActivity(intent)

        return false
    }

    private fun createMarker() {
        db.collection("peluqueros")
            .get()
            .addOnSuccessListener{result->for( document in result){
                Log.d(TAG, "${document.id} => ${document.data["foto"]}")
             val Fotos: List<String> = listOf("${document.data["foto"]}")


        var height = 200
        val width = 200
        //https://www.youtube.com/watch?v=0pF9r0CsT_4 Video




        val pel1 =resources.getDrawable(R.drawable.peluquero1) as BitmapDrawable
        val p1 = pel1.bitmap
        val marker0 = Bitmap.createScaledBitmap(p1, width, height, false)

        val pel2 = resources.getDrawable(R.drawable.peluquero2) as BitmapDrawable
        val p2 = pel2.bitmap
        val marker1 = Bitmap.createScaledBitmap(p2, width, height, false)

        val pel3 = resources.getDrawable(R.drawable.peluquero3) as BitmapDrawable
        val p3 = pel3.bitmap
        val marker2 = Bitmap.createScaledBitmap(p3, width, height, false)

        val pel4= resources.getDrawable(R.drawable.peluquero4) as BitmapDrawable
        val p4 = pel4.bitmap
        val marker3 = Bitmap.createScaledBitmap(p4, width, height, false)

        val pel5= resources.getDrawable(R.drawable.peluquero5) as BitmapDrawable
        val p5 = pel5.bitmap
        val marker4 = Bitmap.createScaledBitmap(p5, width, height, false)

        val pel6= resources.getDrawable(R.drawable.peluquero6) as BitmapDrawable
        val p6 = pel6.bitmap
        val marker5 = Bitmap.createScaledBitmap(p6, width, height, false)

        val pel7= resources.getDrawable(R.drawable.peluquero7) as BitmapDrawable
        val p7 = pel7.bitmap
        val marker6 = Bitmap.createScaledBitmap(p7, width, height, false)

        val pel8= resources.getDrawable(R.drawable.peluquero8) as BitmapDrawable
        val p8 = pel8.bitmap
        val marker7 = Bitmap.createScaledBitmap(p8, width, height, false)

                val markerList = ArrayList<Bitmap>()
                markerList.add(marker0)
                markerList.add(marker1)
                markerList.add(marker2)
                markerList.add(marker3)
                markerList.add(marker4)
                markerList.add(marker5)
                markerList.add(marker6)
                markerList.add(marker7)



                db.collection("peluqueros")
                    .get()
                    .addOnSuccessListener { list ->
                        for (i in 0..7) {
                            val image = FirebaseStorage.getInstance().getReferenceFromUrl(list.documents[i].data!!["foto"].toString())
                            image.getBytes(10 * 1024 * 1024).addOnSuccessListener {
                                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                                val resized = Bitmap.createScaledBitmap(bitmap, 200, 200, false)
                                mMap.addMarker(MarkerOptions()
                                    .title(list.documents[i].data!!["usuario"].toString())
                                    .position(locationArrayList[i])
                                    .icon(BitmapDescriptorFactory
                                        .fromBitmap(resized)
                                    ))
                            }.addOnFailureListener {
                                mMap.addMarker(MarkerOptions()
                                    .title(list.documents[i].data!!["usuario"].toString())
                                    .position(locationArrayList[i])
                                    .icon(BitmapDescriptorFactory
                                        .fromBitmap(markerList[i])
                                    ))

                                Toast.makeText(requireContext(), "no tiene foto", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

//        MUno = MarkerOptions()
//            .title()
//            .position(uno)
//            .icon(BitmapDescriptorFactory
//                .fromBitmap(marker1)
//            )
//
//        MDos = MarkerOptions()
//            .position(dos)
//            .icon(BitmapDescriptorFactory
//                .fromBitmap(marker2)
//
//            )
//
//        MTres = MarkerOptions()
//            .position(tres)
//            .icon(BitmapDescriptorFactory
//                .fromBitmap(marker3)
//            )
//
//        MCuatro = MarkerOptions()
//            .position(cuatro)
//            .icon(BitmapDescriptorFactory
//                .fromBitmap(marker4)
//            )
//
//        MCinco = MarkerOptions()
//            .position(cinco)
//            .icon(BitmapDescriptorFactory
//                .fromBitmap(marker5)
//            )
//
//        MSeis = MarkerOptions()
//            .position(seis)
//            .icon(BitmapDescriptorFactory
//                .fromBitmap(marker6)
//            )
//
//        MSiete = MarkerOptions()
//            .position(siete)
//            .icon(BitmapDescriptorFactory
//                .fromBitmap(marker7)
//            )
//
//        MOcho = MarkerOptions()
//            .position(ocho)
//            .icon(BitmapDescriptorFactory
//                .fromBitmap(marker8)
//            )
//
//        for (i in 0 until locationArrayList.size) {
//            //Marcador de los peluqueros
//            //val marker = MarkerOptions().position(locationArrayList[i])
//            mMap.addMarker(MUno)
//            mMap.addMarker(MDos)
//            mMap.addMarker(MTres)
//            mMap.addMarker(MCuatro)
//            mMap.addMarker(MCinco)
//            mMap.addMarker(MSeis)
//            mMap.addMarker(MSiete)
//            mMap.addMarker(MOcho)
//
//        }
    }

}}
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





