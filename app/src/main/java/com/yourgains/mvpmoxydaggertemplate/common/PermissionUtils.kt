package com.yourgains.mvpmoxydaggertemplate.common

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import com.karumi.dexter.listener.single.BasePermissionListener
import com.yourgains.mvpmoxydaggertemplate.R

class PermissionUtils private constructor(
    private val activity: Activity,
    private val dialogData: DialogData?,
    private val onPermissionGranted: (() -> Unit)?,
    permissions: Array<out String>?
) : BasePermissionListener() {

    init {
        Dexter.withActivity(activity)
            .withPermissions(permissions?.toMutableList())
            .withListener(object : BaseMultiplePermissionsListener() {
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    super.onPermissionRationaleShouldBeShown(permissions, token)
                    dialogData?.let { showDialog(it) }
                }

                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    super.onPermissionsChecked(report)
                    onPermissionGranted?.invoke()
                }
//                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//                    onPermissionGranted?.invoke()
//                }
//
//                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
//                    dialogData?.let { showDialog(it) }
//                }
            })
            .onSameThread()
            .check()
    }

    private fun showDialog(data: DialogData) {
        AlertDialog.Builder(activity)
            .setTitle(data.titleResId)
            .setMessage(data.messageResId)
            .setNegativeButton(data.negativeButtonTextId) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(data.positiveButtonTextId) { dialog, _ ->
                dialog.dismiss()
                val myAppSettings = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + activity.packageName)
                )
                myAppSettings.addCategory(Intent.CATEGORY_DEFAULT)
                myAppSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                activity.startActivity(myAppSettings)
            }
            .setIcon(data.iconId)
            .show()
    }

    class Builder(private val activity: Activity) {

        private var onPermissionGranted: (() -> Unit)? = null

        private var permissions: Array<out String>? = null
        private var dialogData: DialogData? = null

        fun permissionGrantedListener(body: () -> Unit) = apply { this.onPermissionGranted = body }

        fun withPermissions(vararg permission: String) = apply { this.permissions = permission }

        fun withDialog(dialogData: DialogData) = apply { this.dialogData = dialogData }

        fun build() = PermissionUtils(activity, dialogData, onPermissionGranted, permissions)
    }

    data class DialogData(
        val titleResId: Int,
        val messageResId: Int,
        val iconId: Int = 0,
        val negativeButtonTextId: Int = R.string.permission_cancel,
        val positiveButtonTextId: Int = R.string.permission_settings
    )
}