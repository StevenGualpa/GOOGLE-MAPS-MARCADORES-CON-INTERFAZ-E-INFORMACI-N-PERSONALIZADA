package com.example.googlemaps_marcadoresconinterfazeinformacinpersonalizada

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.LocaleList
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener , GoogleMap.OnMapClickListener{

    private lateinit var map:GoogleMap
    var REQUEST_CODE_LOCATION=0
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
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        map.setOnMapClickListener(this)
        enableLocation()
    }




    fun MensajeLargo(Mensaje: String)
    {
        Toast.makeText(this, Mensaje.toString(), Toast.LENGTH_LONG).show()

    }

    //Gestion de Permisos

    private fun isLocatedPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    )==PackageManager.PERMISSION_GRANTED



    private fun enableLocation(){
        if(!::map.isInitialized)return
        if(isLocatedPermissionGranted()){
            map.isMyLocationEnabled=true
        }else{
            requesLocationPermission() //Pide el Permiso
        }

    }
    private fun requesLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
        MensajeLargo("Acepta los Permisos")
        }
        else{
            ActivityCompat.requestPermissions(  this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
           REQUEST_CODE_LOCATION->if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
               map.isMyLocationEnabled=true
           }else{
               MensajeLargo("Para Actucar la localizacion ve a ajustes y acepta los permisos")
           }
           else->{}

       }
    }


    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized) return
        if (!isLocatedPermissionGranted()) {
            map.isMyLocationEnabled = false
            MensajeLargo("Para Activar la localizacion acepta los permisos")
        }
    }


    override fun onMyLocationButtonClick(): Boolean {
        MensajeLargo("Boton Pulsado")
        return true   //False A mi Ubicacion true no hace nada
    }

    override fun onMyLocationClick(p0: Location) {
            MensajeLargo("Estas en ${p0.latitude},${p0.longitude}")
    }

    override fun onMapClick(p0: LatLng) {
        MensajeLargo("Estas en ${p0.latitude},${p0.longitude}")

    }


}