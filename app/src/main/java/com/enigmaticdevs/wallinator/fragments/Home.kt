package com.enigmaticdevs.wallinator.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.enigmaticdevs.wallinator.R
import com.enigmaticdevs.wallinator.adapters.CategoryItemAdapter
import com.enigmaticdevs.wallinator.adapters.ImageItemAdapter
import com.enigmaticdevs.wallinator.databinding.FragmentPopularBinding
import com.enigmaticdevs.wallinator.models.Collection
import com.enigmaticdevs.wallinator.models.Photo
import com.enigmaticdevs.wallinator.webServices.RetrofitInstance
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class Home : Fragment(){
    private lateinit var binding: FragmentPopularBinding
    private lateinit var adapter: ImageItemAdapter
    private var page: Int = 1
    private var  photos : MutableList<Photo> = ArrayList()
    private var selectedItemIndex = 0
    private var sort : String = "popular"
    private lateinit var preferences: SharedPreferences
    private val items = arrayOf(
        "Popular",
        "Recent",
        "Oldest",
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_popular, container, false)
        binding = FragmentPopularBinding.bind(view)
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        sort = preferences.getString("sorting", "popular").toString()
        setIndexOfSelectedItem()
        binding.refreshLayout.startRefresh()
        initRecyclerView()
        getImages()
        binding.sorting.setOnClickListener{
            var selectedFilter = items[selectedItemIndex]
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Select Filter")
                .setSingleChoiceItems(items, selectedItemIndex){ _, which ->
                    selectedItemIndex = which
                    selectedFilter = items[which]
                }
                .setPositiveButton("Ok"){ _, _ ->
                    preferences.edit().putString("sorting",selectedFilter).apply()
                    sort = selectedFilter.toLowerCase(Locale.ROOT)
                    binding.refreshLayout.startRefresh()
                    getImages()
                }
                .setNeutralButton("Cancel"){ _, _ ->
                }
                .show()
        }
        return view
    }
    private fun setIndexOfSelectedItem() {
        when(sort)
        {
            "popular" -> {
                selectedItemIndex = 0
            }
            "recent" -> {
                selectedItemIndex = 1
            }
            "oldest" -> {
                selectedItemIndex = 2
            }
        }
    }
    private fun initRecyclerView() {
        binding.popularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ImageItemAdapter(photos,requireContext())
        binding.popularRecyclerView.adapter = adapter
        val progressLayout = ProgressLayout(context)
        binding.refreshLayout.setHeaderView(progressLayout)
        binding.refreshLayout.setAutoLoadMore(true)

    }

    private fun getImages() {
        page = 1
        val getPost = RetrofitInstance.api.getRecentPhotos(1,30,sort)
        getPost.enqueue(object : Callback<MutableList<Photo>> {
            override fun onResponse(
                    call: Call<MutableList<Photo>>,
                    response: Response<MutableList<Photo>>
            ) {
                if(response.isSuccessful)
                {
                    photos.clear()
                    response.body()?.let { photos.addAll(it) }
                    adapter.notifyDataSetChanged()
                }
                binding.refreshLayout.finishRefreshing()
            }

            override fun onFailure(call: Call<MutableList<Photo>>, t: Throwable) {
                binding.refreshLayout.finishRefreshing()
            }
        })
        initRefreshLayout()
    }

    private fun initRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                super.onRefresh(refreshLayout)
                getImages()
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                super.onLoadMore(refreshLayout)
                page++
                performPagination(page)
            }
        })
    }
    private fun performPagination(page : Int) {
        val getPost = RetrofitInstance.api.getRecentPhotos(page,30,sort)
        getPost.enqueue(object : Callback<MutableList<Photo>> {
            override fun onResponse(
                    call: Call<MutableList<Photo>>,
                    response: Response<MutableList<Photo>>
            ) {
                if(response.isSuccessful)
                {
                    response.body()?.let { photos.addAll(it) }
                    adapter.notifyDataSetChanged()
                }
                binding.refreshLayout.finishLoadmore()
            }

            override fun onFailure(call: Call<MutableList<Photo>>, t: Throwable) {
                binding.refreshLayout.finishLoadmore()
            }
        })
    }
}