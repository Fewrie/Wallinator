package com.enigmaticdevs.wallinator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enigmaticdevs.wallinator.fragments.Category
import com.enigmaticdevs.wallinator.fragments.Home
import com.enigmaticdevs.wallinator.adapters.NavItemAdapter
import com.enigmaticdevs.wallinator.adapters.ViewPagerFragmentAdapter
import com.enigmaticdevs.wallinator.databinding.ActivityMainBinding
import com.enigmaticdevs.wallinator.models.NavItem
import com.google.android.material.tabs.TabLayoutMediator
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var slidingRootNav: SlidingRootNav
    private var navItem : MutableList<NavItem> =  ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        slidingRootNav = SlidingRootNavBuilder(this)
                .withMenuLayout(R.layout.menu_left_drawer)
                .withToolbarMenuToggle(binding.toolbar)
                .inject()
        binding.tabLayoutId.tabRippleColor = null
        val adapter = ViewPagerFragmentAdapter(this)
        binding.viewpagerId.offscreenPageLimit = 2
        val tabNames = arrayOf("Home","Category")
        adapter.addFragment(Home(), tabNames[0])
        adapter.addFragment(Category(), tabNames[1])
        binding.viewpagerId.adapter = adapter
        TabLayoutMediator(binding.tabLayoutId, binding.viewpagerId) { tab, position ->
            tab.text = tabNames[position]
            binding.viewpagerId.setCurrentItem(tab.position, true)
        }.attach()
        initRecyclerview()
    }
    private fun initRecyclerview() {
        navItem.add(NavItem("Settings", R.drawable.ic_settings))
        navItem.add(NavItem("Auto Wallpaper", R.drawable.ic_auto_wallpaper))
        navItem.add(NavItem("Upgrade", R.drawable.ic_upgrade))
        navItem.add(NavItem("About Us", R.drawable.ic_about_me))
        val recyclerView : RecyclerView = findViewById(R.id.nav_recyclerview)
        val adapter = NavItemAdapter(navItem, this)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }
}