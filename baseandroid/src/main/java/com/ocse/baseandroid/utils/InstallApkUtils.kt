package com.ocse.baseandroid.utils

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Message
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.ocse.baseandroid.R
import com.ocse.baseandroid.base.BaseApplication
import com.ocse.baseandroid.databinding.LayoutUpdateDialogBinding
import com.ocse.baseandroid.view.NumberProgressBar
import java.io.File

@SuppressLint("HandlerLeak")
class InstallApkUtils {
    companion object {
        private lateinit var mProgress: NumberProgressBar
        private lateinit var dialog: AlertDialog

        private const val DOWNSTATE = 1
        private const val DOWNOVER = 2
        private const val DOWNFAILE = 3
        init {
            installPremission()
        }
        val versionCode: Int
            get() {
                try {
                    return ObtainApplication.getApp()!!.packageManager
                        .getPackageInfo(ObtainApplication.getApp()!!.packageName, 0).versionCode
                } catch (ignored: PackageManager.NameNotFoundException) {
                }
                return 0
            }

        private val versionName: String?
            private get() {
                try {
                    return ObtainApplication.getApp()!!.packageManager
                        .getPackageInfo(ObtainApplication.getApp()!!.packageName, 0).versionName
                } catch (ignored: PackageManager.NameNotFoundException) {
                }
                return null
            }

        fun checkVersion(urlPath: String?, bt: String, isForce: Boolean) {
            val view = LayoutInflater.from(ObtainApplication.getApp()!!.applicationContext)
                .inflate(R.layout.layout_update_dialog, null)
            val builder =
                AlertDialog.Builder(
                    BaseApplication.activities[BaseApplication.activities.size - 1]
                )
            val binding=LayoutUpdateDialogBinding.bind(view)
            builder.setView(view)
            if (isForce) {
                binding.tvNoUpgrade.visibility = View.INVISIBLE
            }
            val dialog = builder.create()
            binding.tvNoUpgrade.setOnClickListener { dialog.dismiss() }
            binding.tvNoUpgrade.setOnClickListener {
                downLoadApk(urlPath, bt)
                dialog.dismiss()

            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            if (!dialog.isShowing)
                dialog.show()
        }


        private fun downLoadApk(urlPath: String?, bt: String) {
            val view =
                LayoutInflater.from(ObtainApplication.getApp())
                    .inflate(R.layout.download_layout, null)
            mProgress = view.findViewById(R.id.progressbar) as NumberProgressBar
            dialog =
                AlertDialog.Builder(BaseApplication.activities[BaseApplication.activities.size - 1])
                    .setView(view)
                    .setCancelable(false).show()

            DownLoadFileUtils.download(urlPath, bt, object : DownLoadFileUtils.OnDownloadListener {

                override fun onDownloading(progress: Int) {
                    val message = Message()
                    message.what = DOWNSTATE
                    message.arg1 = progress
                    handler.sendMessage(message)
                }

                override fun onDownloadFailed() {
                    handler.sendEmptyMessage(DOWNFAILE)
                }

                override fun onDownloadSuccess(file: File) {
                    handler.sendEmptyMessage(DOWNOVER)
                    installApk(file)
                }

            })
        }

        var handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    DOWNSTATE -> mProgress.progress = msg.arg1
                    DOWNOVER -> dialog.dismiss()
                    DOWNFAILE -> {
                        ToastUtil.show("下载APK失败")
                        dialog.dismiss()
                    }
                }
            }
        }

        private fun installPremission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val isgtand = ObtainApplication. getApp()!!.packageManager.canRequestPackageInstalls()
                if (isgtand) {
                } else {
                    val packageURI =
                        Uri.parse("package:" + ObtainApplication. getApp()!!.packageName);
                    //注意这个是8.0新API
                    val intent = Intent(
                        Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                        packageURI
                    )
                    ObtainApplication. getApp()!!.startActivity(intent)
                }
            }
        }

        private fun installApk(file: File) {

            val intent = Intent(Intent.ACTION_VIEW)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val contentUri =
                    FileProvider.getUriForFile(
                        ObtainApplication. getApp()!!,
                        ObtainApplication. getApp()!!.packageName + ".fileprovider",
                        file
                    )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            ObtainApplication.getApp()?.startActivity(intent)
        }
    }
}