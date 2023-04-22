package com.shliama.augmentedvideotutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.hyuwah.draggableviewlib.DraggableView

class HomeAct : AppCompatActivity() {
    @BindView(R.id.id_camera)
    lateinit var floatingBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)

        setDragDropFloatingBtn()
    }

    private fun setDragDropFloatingBtn() {
        var draggableView : DraggableView<FloatingActionButton> = DraggableView.Builder(floatingBtn)
            .setStickyMode(DraggableView.Mode.STICKY_X)
            .build()
    }

    @OnClick(R.id.id_camera)
    fun onClickOpenCamera() {
        // onClickOpenCamera
    }

    @OnClick(R.id.id_icon_add)
    fun onClickAddImageAndVideo() {
        // onClickAddImageAndVideo
    }
}