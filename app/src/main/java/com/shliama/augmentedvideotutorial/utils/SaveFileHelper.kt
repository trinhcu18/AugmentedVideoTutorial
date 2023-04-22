package com.shliama.augmentedvideotutorial.utils

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class SaveFileHelper(private  val context: Context) {
    suspend fun saveToLocalFile(body: ResponseBody?, folderId : String?, targetId : String?) : String? = withContext(Dispatchers.IO) {
        if (body == null || targetId == null || folderId == null) return@withContext null

        var input:InputStream? = null

        try {
            input = body.byteStream()

            val defaultDir = context.filesDir
            val folder = File(defaultDir, "$folderId")
            folder.mkdirs()
            val localFile = File(folder, "$targetId.mp4")
            val  fos = FileOutputStream(localFile)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return@withContext localFile.path
        }catch (e:Exception){
            Log.e("saveFile",e.toString());
            return@withContext  null

        }finally {
            input?.close()
            return@withContext  null
        }

    }
}