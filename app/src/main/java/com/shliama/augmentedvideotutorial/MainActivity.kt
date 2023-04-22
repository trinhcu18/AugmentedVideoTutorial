package com.shliama.augmentedvideotutorial

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import butterknife.ButterKnife
import butterknife.OnClick
import com.shliama.augmentedvideotutorial.model.ImageTarget
import com.shliama.augmentedvideotutorial.retrofit.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val openGlVersion by lazy {
        (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .deviceConfigurationInfo
            .glEsVersion
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        if (openGlVersion.toDouble() >= MIN_OPEN_GL_VERSION) {
            supportFragmentManager.inTransaction { replace(R.id.fragmentContainer, ArVideoFragment()) }
        } else {
            AlertDialog.Builder(this)
                .setTitle("Device is not supported")
                .setMessage("OpenGL ES 3.0 or higher is required. The device is running OpenGL ES $openGlVersion.")
                .setPositiveButton(android.R.string.ok) { _, _ -> finish() }
                .show()
        }
    }

    @OnClick(R.id.btnBack)
    fun onClickBack(){
        onBackPressed()
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    companion object {
        private const val MIN_OPEN_GL_VERSION = 3.0
        var mapImageVideo = hashMapOf<String, Bitmap?>()
        suspend fun loadImages(context: Context, image: List<ImageTarget>) = withContext(Dispatchers.IO){
            val map = hashMapOf<String, Bitmap?>()
            image.forEach {
                try {
                    val bitmap = BitmapFactory.decodeStream(URL("${ApiHelper.BASE_URL}${it.imageUrl!!.substring(it.imageUrl.indexOf("media"))}").openStream())
                    val file = File(context.filesDir, "${it.folderId}/${it.id}.mp4")
                    if(file.exists()){
                        map.put(file.path, bitmap)
                    } else {
                        map.put("${ApiHelper.BASE_URL}${it.videoUrl!!.substring(it.videoUrl.indexOf("media"))}", bitmap)
                    }
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
            mapImageVideo = map
        }
    }
}