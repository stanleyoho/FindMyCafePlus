package com.app.findmycafeplus.Fragment

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.findmycafeplus.Adapter.CafeInfoAdapter
import com.app.findmycafeplus.Constants.Constants
import com.app.findmycafeplus.CustomView.FilterDialog
import com.app.findmycafeplus.Interface.FilterDialogCallBackInterface
import com.app.findmycafeplus.Model.RMCafeInformation
import com.app.findmycafeplus.Preference.LevelPreference
import com.app.findmycafeplus.R
import com.app.findmycafeplus.Utils.MapUtils
import com.app.findmycafeplus.Utils.MarkerUtils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.layout_cafe_detail.view.*


class MapFragment : BasicFragment() , OnMapReadyCallback{

    private lateinit var mMap: GoogleMap
    private lateinit var cafeList : RealmResults<RMCafeInformation>
    private var location : Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cafeList = RMCafeInformation().getAll()

        initCurrentLocation()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_map,container,false)

        val mapFragment : SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {

        initMap(googleMap)

        //add mark
//        addMarkOnMap(cafeList)

        MapUtils.moveCameraTo(mMap,location,Constants.ZOOM_NORMAL)
    }

    /**
     * location click listener
     * */
    private var locationClickListener = GoogleMap.OnMyLocationClickListener { location -> MapUtils.moveCameraTo(mMap,location.latitude,location.longitude,Constants.ZOOM_NORMAL) }

    /**
     * button click listener
     * */
    private var clickListener = View.OnClickListener { v ->
        val viewId = v?.id
        when(viewId){
            R.id.btnSearch ->{
                FilterDialog(context!!,filterCallBack).show()
            }
        }
    }

    /**
     * 初始化地圖
     * */
    private fun  initMap(googleMap: GoogleMap){
        mMap = googleMap

        //set map style
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_json))

        //set map default zoom
        mMap.setMaxZoomPreference(Constants.ZOOM_MAX)
        mMap.setMinZoomPreference(Constants.ZOOM_MIN)
        mMap.uiSettings.isMapToolbarEnabled = false

        //check permission and set location click listener
        if(ContextCompat.checkSelfPermission(context!!,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mMap.isMyLocationEnabled = true
            mMap.setOnMyLocationClickListener (locationClickListener)
        }

        //set custom info window
        mMap.setInfoWindowAdapter(CafeInfoAdapter(context))

        //set mark click listener
        mMap.setOnMarkerClickListener (onMarkClickListener)

        //set search listener
        btnSearch.setOnClickListener (clickListener)
    }

    /**
     * filter dialog callBack
     * */
    private var filterCallBack = object : FilterDialogCallBackInterface{
        override fun filterResult(result: RealmResults<RMCafeInformation>) {
            mMap.clear()
            addMarkOnMap(result)
            addMemberExperience()
        }
    }

    /**
     * 在地圖中加入mark
     * @param cafeList mark的資料
     * */
    private fun addMarkOnMap(cafeList : RealmResults<RMCafeInformation> ){

        // Add a marker in Sydney and move the camera
        for(index in 0 until cafeList.size){
            val cafeInfo: RMCafeInformation? = cafeList[index]
            val cafePosition = LatLng(cafeInfo!!.latitude, cafeInfo.longitude)
            val marker = mMap.addMarker(MarkerOptions().position(cafePosition).title(cafeInfo.name).icon(MarkerUtils.getMapNormalIcon(context!!)))
            marker.tag = cafeInfo
        }
    }

    private var onMarkClickListener = object : GoogleMap.OnMarkerClickListener{
        override fun onMarkerClick(p0: Marker?): Boolean {
            if(p0 != null){
                val cafeInfo = p0.tag as RMCafeInformation
                setCafeDetailView(cafeInfo)
            }
            return false
        }
    }
    /**
     * 初始化手機 location
     * */
    private fun initCurrentLocation(){
        val mLocationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager

        if(ContextCompat.checkSelfPermission(context!!,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //get current location
            if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)  //使用GPS定位座標
                } else if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) //使用GPS定位座標
                }
            }
        }
    }

    /**
     * 設定咖啡詳細資料的畫面
     * */
    private fun setCafeDetailView(cafeInfo : RMCafeInformation){
        layoutCafeDetail.textCafeDetailNameContent.text = cafeInfo.name
        layoutCafeDetail.textCafeDetailAddressContent.text = cafeInfo.address
        layoutCafeDetail.textCafeDetailOpenTimeContent.text = cafeInfo.open_time
        layoutCafeDetail.ivCafeDetailNavigate.setOnClickListener { MapUtils.getGoogleMapIntent(context!!,cafeInfo) }
    }

    /**
     * 增加使用者者經驗值
     * */
    private fun addMemberExperience(){
        //add preference experience
        LevelPreference(context!!).experience ++
        //notify navigationView
        val intent = Intent()
        intent.action = Constants.LEVEL_BROADCAST_INTENT
        LocalBroadcastManager.getInstance(context!!).sendBroadcast(intent)
    }
}