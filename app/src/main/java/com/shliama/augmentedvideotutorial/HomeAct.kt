package com.shliama.augmentedvideotutorial

import android.bluetooth.BluetoothClass.Device
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shliama.augmentedvideotutorial.utils.DeviceIDUtil
import io.github.hyuwah.draggableviewlib.DraggableView

class HomeAct : AppCompatActivity() {
    @BindView(R.id.id_camera)
    lateinit var floatingBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)
        setDragDropFloatingBtn()

        initData()

    }

    private fun initData() {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        val userDeviceId = DeviceIDUtil.getDeviceID(this)
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
    }
}