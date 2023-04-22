package com.shliama.augmentedvideotutorial.utils

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager

object DeviceIDUtil {
    fun getDeviceID(context: Context): String? {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var deviceID: String? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For devices running Android Q or above, the device ID is not accessible anymore.
            // Instead, use the Android ID which is unique for each user.
            deviceID = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } else {
            if (telephonyManager.deviceId != null) {
                deviceID = telephonyManager.deviceId
            }
        }

        return deviceID
    }
}
