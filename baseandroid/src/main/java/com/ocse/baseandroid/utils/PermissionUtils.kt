package com.ocse.baseandroid.utils

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX

class PermissionUtils {
    companion object {
        val instance by lazy { PermissionUtils() }
        val normalPermission = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.LOCATION_HARDWARE,
        )
        val android13Permission = arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO,
        )

        @SuppressLint("CheckResult")
        fun getPermission(mContext: FragmentActivity) {
            val arrayList = arrayListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CONTROL_LOCATION_UPDATES,
                Manifest.permission.RECORD_AUDIO
            )
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                arrayList.add(Manifest.permission.READ_MEDIA_IMAGES)
                arrayList.add(Manifest.permission.READ_MEDIA_VIDEO)
                arrayList.add(Manifest.permission.READ_MEDIA_AUDIO)
            }
            PermissionX.init(mContext)
                .permissions(arrayList)
                .onExplainRequestReason { scope, deniedList ->
                    val message = "应用需要您同意以下权限才能正常使用"
                    scope.showRequestReasonDialog(deniedList, message, "确定", "取消")
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
//                    Toast.makeText(activity, "所有申请的权限都已通过", Toast.LENGTH_SHORT).show()
                    } else {
//                    Toast.makeText(activity, "您拒绝了如下权限：$deniedList", Toast.LENGTH_SHORT).show()
                    }
                }
        }


        @SuppressLint("CheckResult")
        fun addPermission(mContext: FragmentActivity, vararg permissions: String?) {
            PermissionX.init(mContext)
                .permissions(permissions.contentToString())
                .onExplainRequestReason { scope, deniedList ->
                    val message = "应用需要您同意以下权限才能正常使用"
                    scope.showRequestReasonDialog(deniedList, message, "确定", "取消")
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
//                    Toast.makeText(activity, "所有申请的权限都已通过", Toast.LENGTH_SHORT).show()
                    } else {
//                    Toast.makeText(activity, "您拒绝了如下权限：$deniedList", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }


}