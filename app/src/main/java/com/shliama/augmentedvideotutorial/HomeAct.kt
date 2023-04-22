package com.shliama.augmentedvideotutorial

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shliama.augmentedvideotutorial.retrofit.ApiHelper
import com.shliama.augmentedvideotutorial.utils.DeviceIDUtil
import com.shliama.augmentedvideotutorial.utils.DownloadHelper
import io.github.hyuwah.draggableviewlib.DraggableView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class HomeAct : AppCompatActivity() {
    @BindView(R.id.id_camera)
    lateinit var floatingBtn: FloatingActionButton

    lateinit var homeAdapter : HomeAdapter

    @BindView(R.id.id_rec_list)
    lateinit var mRecList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)

        setDragDropFloatingBtn()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initData() {
        getDataOwner()
    }

    private fun setDragDropFloatingBtn() {
        var draggableView : DraggableView<FloatingActionButton> = DraggableView.Builder(floatingBtn)
            .setStickyMode(DraggableView.Mode.STICKY_X)
            .build()
    }

    @OnClick(R.id.id_camera)
    fun onClickOpenCamera() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    @OnClick(R.id.id_icon_add)
    fun onClickAddImageAndVideo() {
        // onClickAddImageAndVideo
         val intent = Intent(this, UploadAct::class.java)
        startActivity(intent)
    }

    private fun getDataOwner() {
        val userDeviceId = DeviceIDUtil.getDeviceID(this).toString()

        if (userDeviceId.isNotEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val dataOwnerRemote = ApiHelper.myApiService.getDataOwner(userDeviceId)
                    val data = dataOwnerRemote.imageTargets ?: emptyList()
                    homeAdapter = HomeAdapter(data)
                    withContext(Dispatchers.Main){
                        mRecList.adapter = homeAdapter
                    }
                    userFolderId = dataOwnerRemote.id
                    for (imageTarget in data){
                        if (!isVideoExistInLocal(imageTarget.folderId, imageTarget.id)){
                            val videoURL = imageTarget.videoUrl
                            if (videoURL != null)
                            DownloadHelper(this@HomeAct).
                            downloadVideoByURL(videoURL,imageTarget.folderId, imageTarget.id)
                        }
                    }

                } catch (e: java.lang.Exception){
                    e.printStackTrace()

                }
            }
        }

    }

    companion object {
        var userFolderId: String? = null
    }

   fun isVideoExistInLocal( folderId: String?, targetId:String?) : Boolean{
       val defaultDir = this.filesDir
       val localVideoFile = File(defaultDir.path,"$folderId/$targetId.mp4");
       return localVideoFile.exists()
   }
}