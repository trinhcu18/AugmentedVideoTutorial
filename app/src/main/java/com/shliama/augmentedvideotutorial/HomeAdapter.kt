package com.shliama.augmentedvideotutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shliama.augmentedvideotutorial.model.ImageTarget

class HomeAdapter(var imageTargetList: List<ImageTarget>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.id_image)
//        val imageView: ImageView
//        init {
//             imageView = itemView.findViewById<ImageView>(R.id.id_image)
//        }
//
//        fun onBind(dataItem: OwnerResponse){
//            Glide.with(itemView.context).load(dataItem.imageTargets?.get(adapterPosition)?.imageUrl).into(imageView)
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_list_image, parent, false))
    }

    override fun getItemCount(): Int = imageTargetList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(imageTargetList[position].imageUrl)
            .into(holder.imageView)
    }
}