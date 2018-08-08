package com.app.findmycafeplus.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.findmycafeplus.Adapter.CafeInfoAdapter
import com.app.findmycafeplus.Model.CafeInformation
import com.app.findmycafeplus.Model.RMCafeInformation
import com.app.findmycafeplus.Model.test
import com.app.findmycafeplus.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import io.realm.RealmResults

class MapFragment : BasicFragament() , OnMapReadyCallback{


    private lateinit var mMap: GoogleMap
    private lateinit var cafeList : RealmResults<RMCafeInformation>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cafeList = RMCafeInformation().getAll()
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

        //disable toolBar
        mMap.uiSettings.isMapToolbarEnabled = false

        // Add a marker in Sydney and move the camera
        for(index in 0 until cafeList.size){
            val cafeInfo: RMCafeInformation? = cafeList[index]
            val cafePosition = LatLng(cafeInfo!!.latitude, cafeInfo.longitude)
            val marker = mMap.addMarker(MarkerOptions().position(cafePosition).title(cafeInfo.name))
            marker.tag = cafeInfo
        }
//        val sydney = LatLng(-34.0, 151.0)
//        val marker = mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))

//        marker.tag = test(3f)

        mMap.setInfoWindowAdapter(CafeInfoAdapter(context))

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}