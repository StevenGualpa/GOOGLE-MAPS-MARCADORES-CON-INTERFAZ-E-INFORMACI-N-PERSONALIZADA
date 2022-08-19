package com.example.googlemaps_marcadoresconinterfazeinformacinpersonalizada

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map:GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MuestraMapa()
    }

    private fun MuestraMapa(){
        val mapFragment:SupportMapFragment=supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    //Funcion Para Agregar Marcador
    private fun CreaMarcador(){
        val coordenadas=LatLng(-1.0803351324691082, -79.50145350501472)
        val marcador: MarkerOptions =MarkerOptions().position(coordenadas).title("UTEQ")
        map.addMarker(marcador)
        //Animacion para Dirigir la camara hacia el marcador
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordenadas,18f),5000,null)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        CreaMarcador()
    }
}