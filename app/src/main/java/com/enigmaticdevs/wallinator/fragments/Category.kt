package com.enigmaticdevs.wallinator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.enigmaticdevs.wallinator.R
import com.enigmaticdevs.wallinator.adapters.CategoryItemAdapter
import com.enigmaticdevs.wallinator.databinding.FragmentCategoryBinding
import com.enigmaticdevs.wallinator.models.Collection
import com.enigmaticdevs.wallinator.webServices.RetrofitInstance
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Category : Fragment(){
    private lateinit var binding: FragmentCategoryBinding
    private var collection : MutableList<Collection> = ArrayList()
    private var page: Int = 1
    private lateinit var adapter: CategoryItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        binding = FragmentCategoryBinding.bind(view)
        binding.refreshLayout.startRefresh()
        initRecyclerView()
        getCollections()
        return view
    }

    private fun initRecyclerView() {
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CategoryItemAdapter(collection,requireContext())
        binding.categoryRecyclerView.adapter = adapter
        val progressLayout = ProgressLayout(context)
        binding.refreshLayout.setHeaderView(progressLayout)
        binding.refreshLayout.setAutoLoadMore(true)
    }

    private fun getCollections(){
        val getPost = RetrofitInstance.api.getCollections(30,1)
        getPost.enqueue(object : Callback<MutableList<Collection>>{
            override fun onResponse(
                call: Call<MutableList<Collection>>,
                response: Response<MutableList<Collection>>
            ) {
                if(response.isSuccessful)
                {
                    collection.clear()
                    response.body()?.let { collection.addAll(it) }
                    adapter.notifyDataSetChanged()
                }
                binding.refreshLayout.finishRefreshing()
            }

            override fun onFailure(call: Call<MutableList<Collection>>, t: Throwable) {
                binding.refreshLayout.finishRefreshing()
            }
        })
        initRefreshLayout()
    }
    private fun initRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                super.onRefresh(refreshLayout)
                getCollections()
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                super.onLoadMore(refreshLayout)
                page++
                performPagination(page)
            }
        })
    }

    private fun performPagination(page: Int) {
        val getPost = RetrofitInstance.api.getCollections(30,page)
        getPost.enqueue(object : Callback<MutableList<Collection>> {
            override fun onResponse(
                call: Call<MutableList<Collection>>,
                response: Response<MutableList<Collection>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { collection.addAll(it) }
                    adapter.notifyDataSetChanged()
                }
                binding.refreshLayout.finishLoadmore()

            }
            override fun onFailure(call: Call<MutableList<Collection>>, t: Throwable) {
               binding.refreshLayout.finishLoadmore()
            }
        })
    }

}