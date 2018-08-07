package com.app.findmycafeplus.Utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

class PermissionCheckUtils(builder: Builder){

    var  permissions = ArrayList<String>()

    init{
        permissions =  builder.permissionList
    }

    companion object {

        open class Builder constructor(context: Context){

            private val mContext : Context = context
            var  permissionList  = ArrayList<String>()

            fun setCameraPermissionCheck() : Builder{
                if(ContextCompat.checkSelfPermission(mContext,Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                    permissionList.add(Manifest.permission.CAMERA)
                }
                return this
            }

            fun setReadStoragePermissionCheck() : Builder{
                if(ContextCompat.checkSelfPermission(mContext,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                return this
            }

            fun setWriteStoragePermissionCheck() : Builder{
                if(ContextCompat.checkSelfPermission(mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                return this
            }

            fun setLocationFindPermissionCheck() : Builder{
                if(ContextCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                    permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
                }
                return this
            }

            fun build() : PermissionCheckUtils{
                return PermissionCheckUtils(this)
            }
        }
    }

    fun getPersmissions() : Array<String>{
        return permissions.toTypedArray()
    }
}