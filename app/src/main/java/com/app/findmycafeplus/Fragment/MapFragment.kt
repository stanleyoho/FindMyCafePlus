package com.app.findmycafeplus.Fragment

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.findmycafeplus.Adapter.CafeInfoAdapter
import com.app.findmycafeplus.Constants.Constants
import com.app.findmycafeplus.CustomView.FilterDialog
import com.app.findmycafeplus.Interface.FilterDialogCallBackInterface
import com.app.findmycafeplus.Model.RMCafeInformation
import com.app.findmycafeplus.R
import com.app.findmycafeplus.Utils.MapUtils
import com.app.findmycafeplus.Utils.MarkerUtils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : BasicFragament() , OnMapReadyCallback{


    private lateinit var mMap: GoogleMap
    private lateinit var cafeList : RealmResults<RMCafeInformation>
    private lateinit var mLocationManager : LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cafeList = RMCafeInformation().getAll()

        mLocationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_map,container,false)

        val mapFragment : SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //set map style
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_json))

        //set map default zoom
        mMap.setMaxZoomPreference(16f)
        mMap.setMinZoomPreference(3f)
        mMap.uiSettings.isMapToolbarEnabled = false

        //check permission and set location click listener
        if(ContextCompat.checkSelfPermission(context!!,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mMap.isMyLocationEnabled = true
            mMap.setOnMyLocationClickListener (locationClickListener)

            //get current location
            var location : Location?  = null

            if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);  //使用GPS定位座標
                } else if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); //使用GPS定位座標
                }
            }
            try{
                mMap.animateCamera(MapUtils.getCameraLatLngZoom(location!!.latitude, location.longitude, 18))
            }catch (e:Exception){
                mMap.animateCamera(MapUtils.getCameraLatLngZoom(Constants.LOCATION_TAIPIE_STATION_LAT, Constants.LOCATION_TAIPIE_STATION_LOG, 18))
            }
        }

        //add mark
        addMarkOnMap(cafeList)

        //set custom info window
        mMap.setInfoWindowAdapter(CafeInfoAdapter(context))

        //set search listener
        btnSearch.setOnClickListener (clickListener)

    }

    private var locationClickListener = GoogleMap.OnMyLocationClickListener { location -> mMap.animateCamera(MapUtils.getCameraLatLngZoom(location.latitude, location.longitude, 18)) }

    private var clickListener = View.OnClickListener { v ->
        val viewId = v?.id
        when(viewId){
            R.id.btnSearch ->{
                FilterDialog(context!!,filterCallBack).show()
            }
        }
    }

    private var filterCallBack = object : FilterDialogCallBackInterface{
        override fun filterResult(result: RealmResults<RMCafeInformation>) {
            mMap.clear()
            addMarkOnMap(result)
        }
    }

    private fun addMarkOnMap(cafeList : RealmResults<RMCafeInformation>){

        // Add a marker in Sydney and move the camera
        for(index in 0 until cafeList.size){
            val cafeInfo: RMCafeInformation? = cafeList[index]
            val cafePosition = LatLng(cafeInfo!!.latitude, cafeInfo.longitude)
            val marker = mMap.addMarker(MarkerOptions().position(cafePosition).title(cafeInfo.name).icon(MarkerUtils.getMapNormalIcon(context!!)))
            marker.tag = cafeInfo
        }
    }
}