package com.enigmaticdevs.wallinator.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enigmaticdevs.wallinator.R
import com.enigmaticdevs.wallinator.models.Collection
import org.w3c.dom.Text

class CategoryItemAdapter(
    private var list: MutableList<Collection>,
    private val context: Context) : RecyclerView.Adapter<CategoryItemAdapter.ViewHolder>()  {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userAvatar: ImageView = itemView.findViewById(R.id.collection_user_avatar)
        val username: TextView = itemView.findViewById(R.id.collection_username)
        val title: TextView = itemView.findViewById(R.id.item_collection_title)
        val photo: ImageView = itemView.findViewById(R.id.item_collection_photo)
        val totalPhotos : TextView = itemView.findViewById(R.id.item_collection_total_photos)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_collection,
            parent,
            false
        )
        val vh = ViewHolder(inflatedView)
        vh.photo.setOnLongClickListener {
             true
        }
        vh.photo.setOnClickListener {
        }
        vh.userAvatar.setOnClickListener {
        }
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.username.text = list[position].user.name
        holder.totalPhotos.text = list[position].totalPhotos.toString() + " Photos"
       // holder.photo.setAspectRatio(list[position].dimension_x, list[position].dimension_y)
        Glide.with(context)
            .load(list[position].coverPhoto.url.regular)
           .placeholder(ColorDrawable(Color.parseColor(list[position].coverPhoto.color)))
            .into(holder.photo)
        Glide.with(context)
            .load(list[position].user.profileImage.medium)
             //.placeholder(ColorDrawable(Color.parseColor(list[position].coverPhoto.color)))
            .into(holder.userAvatar)

    }

    override fun getItemCount(): Int {
        return list.size
    }
}