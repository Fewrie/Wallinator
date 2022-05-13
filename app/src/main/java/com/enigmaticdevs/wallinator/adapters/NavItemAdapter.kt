package com.enigmaticdevs.wallinator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.enigmaticdevs.wallinator.R
import com.enigmaticdevs.wallinator.models.NavItem

class NavItemAdapter(

    private val navItem: MutableList<NavItem>,
    private val context: Context
) : RecyclerView.Adapter<NavItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon : ImageView = itemView.findViewById(R.id.menu_item_image)
        val title : TextView = itemView.findViewById(R.id.menu_item_name)
        val itemLayout : ConstraintLayout = itemView.findViewById(R.id.nav_item_layout)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(
            R.layout.menu_list_item,
            parent,
            false
        )
        val vh = ViewHolder(inflatedView)
        vh.itemLayout.setOnClickListener{
           /* when(navItem[vh.adapterPosition].name){
                "Settings" -> {
                    context.startActivity(Intent(context, Settings::class.java))
                }
                "Auto Wallpaper" ->{
                    context.startActivity(Intent(context, AutoWallpaperSettings::class.java))
                }
                "Upgrade" ->{
                    context.startActivity(Intent(context, Upgrade::class.java))
                }
                "About Us" -> {
                    context.startActivity(Intent(context, About::class.java))
                }
            }*/


        }
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = navItem[position].name
        holder.icon.setBackgroundResource(navItem[position].icon)
    }
    override fun getItemCount(): Int {
        return navItem.size
    }
}