package com.shliama.augmentedvideotutorial

import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.shliama.augmentedvideotutorial.retrofit.ApiHelper
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody


class UploadAct : AppCompatActivity() {
    private val PICK_VIDEO_REQUEST_CODE = 0
    private val PICK_IMAGE_REQUEST_CODE = 1

    @BindView(R.id.id_previewUploadImage)
    lateinit var imgUploadPreView: ImageView

    @BindView(R.id.id_previewUploadVideo)
    lateinit var imgVideoUploadPreView: ImageView

    @BindView(R.id.id_imgUploadImage)
    lateinit var btnUploadImage: ImageView

    @BindView(R.id.id_imgUploadVideo)
    lateinit var btnUploadVideo: ImageView

    @BindView(R.id.id_txtUploadImage)
    lateinit var txtUploadImage: TextView

    @BindView(R.id.id_txtUploadVideo)
    lateinit var txtUploadVideo: TextView

    @BindView(R.id.progress)
    lateinit var mProgressBar : View

    @BindView(R.id.id_uploadToServer)
    lateinit var mBtnUploadFile : AppCompatButton

    var mImageURL : Uri?= null

    var mVideoURL : Uri?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.id_imgUploadImage)
    fun onClickUploadImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult( intent, PICK_IMAGE_REQUEST_CODE)

    }

    @OnClick(R.id.id_imgUploadVideo)
    fun onClickUploadVideo() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult( intent, PICK_VIDEO_REQUEST_CODE)
    }

    @OnClick(R.id.id_uploadToServer)
    fun onClickUploadFileToServer() {
        if (mImageURL == null || mVideoURL == null ) {
            Toast.makeText(this@UploadAct, "Select Image and Video", Toast.LENGTH_SHORT).show()
            return
        }

        val fid = HomeAct.userFolderId ?: return
        val file = File(getRealPathFromURI(mImageURL))
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val filePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val fileAudio = File(getRealPathFromURI(mVideoURL))
        val requestFileAudio = RequestBody.create(MediaType.parse("multipart/form-data"), fileAudio)
        val filePartAudio = MultipartBody.Part.createFormData("video", fileAudio.name, requestFileAudio)

        val folderId: RequestBody = RequestBody.create(MediaType.parse("text/plain"), fid)

        lifecycleScope.launch(Dispatchers.Main){
            mBtnUploadFile.visibility = View.GONE
            mProgressBar.visibility = View.VISIBLE
            try {
                val uploadResult = withContext(Dispatchers.IO){
                    ApiHelper.myApiService.uploadIFileToServer(filePart, filePartAudio, folderId)
                }
                    Toast.makeText(this@UploadAct, "Upload Success", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: java.lang.Exception){
                e.printStackTrace()
                    Toast.makeText(this@UploadAct, "Upload Failed", Toast.LENGTH_SHORT).show()
            }finally {
                mProgressBar.visibility = View.GONE
                mBtnUploadFile.visibility = View.VISIBLE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            setVisibilityUploadView()
            return
        }

        if (requestCode == PICK_VIDEO_REQUEST_CODE && resultCode == RESULT_OK) {
            val videoURL = data.data
            mVideoURL = videoURL
            val retriever = MediaMetadataRetriever()
            videoURL?.let { uri ->
                contentResolver.openFileDescriptor(uri, "r")?.use { fileDescriptor ->
                    retriever.setDataSource(fileDescriptor.fileDescriptor)
                    val bitmap = retriever.frameAtTime
                    imgVideoUploadPreView.setImageBitmap(bitmap)
                }
            }
            retriever.release()
        }else{
            setVisibilityUploadView()
        }

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageURL = data.data
            mImageURL = imageURL
            imgUploadPreView.setImageURI(imageURL)
        }else{
            setVisibilityUploadView()
        }
    }

    private fun setVisibilityUploadView() {
        btnUploadImage.visibility = View.VISIBLE
        btnUploadVideo.visibility = View.VISIBLE
        txtUploadImage.visibility = View.VISIBLE
        txtUploadVideo.visibility = View.VISIBLE
    }

    private fun getRealPathFromURI(uri: Uri?): String {
        val cursor = contentResolver.query(uri!!, null, null, null, null)
        cursor!!.moveToFirst()
        val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        val path = cursor.getString(index)
        cursor.close()
        return path
    }
}