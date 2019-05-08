package com.rjain90.engineeraiassignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rjain90.engineeraiassignment.R
import com.rjain90.engineeraiassignment.model.Post
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var loading = false
    private var page = 0
    private var mPosts: MutableList<Post> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_posts.layoutManager = LinearLayoutManager(this)
        val adapter = PostAdapter(mPosts, this)
        rv_posts.adapter = adapter

        val model = ViewModelProviders.of(this).get(MyViewModel::class.java)
        model.getUsers().observe(this, Observer<List<Post>> { posts ->
            loading = false
            mPosts.addAll(posts)
            adapter.notifyDataSetChanged()
        })

        rv_posts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView
                    .layoutManager as LinearLayoutManager?
                if (!loading && linearLayoutManager!!.itemCount <= linearLayoutManager.findLastVisibleItemPosition() + 2) {
                    loading = true
                    page++
                    model.loadUsers(page)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }
}
