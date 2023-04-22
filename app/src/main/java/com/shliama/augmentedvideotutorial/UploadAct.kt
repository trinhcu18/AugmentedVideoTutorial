package com.shliama.augmentedvideotutorial

import android.content.Intent
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            setVisibilityUploadView()
            return
        }

        if (requestCode == PICK_VIDEO_REQUEST_CODE && resultCode == RESULT_OK) {
            val videoURL = data.data
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
}