package com.shliama.augmentedvideotutorial.utils

import android.content.Context
import android.util.Log
import com.shliama.augmentedvideotutorial.retrofit.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DownloadHelper(private  val context :Context) {
    suspend fun downloadVideoByURL(videoURL: String, folderId:String?, targetId: String?) = withContext(Dispatchers.IO)
    {
        try {
            val responseBody = ApiHelper.downloadVideoService.getVideoData(videoURL.substring(videoURL.indexOf("/media")))
            Log.e("!!!!!","Download Crash")
            SaveFileHelper(context).saveToLocalFile(responseBody,folderId, targetId )
            Log.e("!!!!!","Save Crash")


        }catch (e:Exception){
            e.printStackTrace()
        }

    }
}