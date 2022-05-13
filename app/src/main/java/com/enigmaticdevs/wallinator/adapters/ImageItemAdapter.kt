package com.enigmaticdevs.wallinator.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enigmaticdevs.wallinator.R
import com.enigmaticdevs.wallinator.models.Photo

class ImageItemAdapter(private var photos: MutableList<Photo>, private val context: Context) : RecyclerView.Adapter<ImageItemAdapter.ViewHolder>()  {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userAvatar: ImageView = itemView.findViewById(R.id.user_profile_image)
        val username: TextView = itemView.findViewById(R.id.user_name)
        val sponsorship: TextView = itemView.findViewById(R.id.sponsorship)
        val photo: ImageView = itemView.findViewById(R.id.photo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_photo,
            parent,
            false
        )
        val vh = ViewHolder(inflatedView)
        vh.photo.setOnLongClickListener {
            true
        }
        vh.photo.setOnClickListener {
        }
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.text = photos[position].user.name
        // holder.photo.setAspectRatio(list[position].dimension_x, list[position].dimension_y)
        Glide.with(context)
                .load(photos[position].url.regular)
                .placeholder(ColorDrawable(Color.parseColor(photos[position].color)))
                .into(holder.photo)
        Glide.with(context)
                .load(photos[position].user.profileImage.medium)
                //.placeholder(ColorDrawable(Color.parseColor(list[position].coverPhoto.color)))
                .into(holder.userAvatar)

    }

    override fun getItemCount(): Int {
        return photos.size
    }
}