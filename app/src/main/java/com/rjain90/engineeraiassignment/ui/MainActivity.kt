package com.rjain90.engineeraiassignment.ui

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rjain90.engineeraiassignment.model.Post
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private var loading = false
    private var page = 0
    private var mPosts: MutableList<Post> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.rjain90.engineeraiassignment.R.layout.activity_main)

        setSupportActionBar(toolbar)

        rv_posts.layoutManager = LinearLayoutManager(this)
        val adapter = PostAdapter(mPosts, this, this, this)
        rv_posts.adapter = adapter

        val model = ViewModelProviders.of(this).get(MyViewModel::class.java)
        model.getUsers().observe(this, Observer<List<Post>> { posts ->
            loading = false
            addData(posts)
            adapter.notifyDataSetChanged()

            srl_posts.isRefreshing = false
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

        srl_posts.setOnRefreshListener {
            loading = true
            page = 0
            model.loadUsers(page)
        }
    }

    private fun addData(posts: List<Post>) {
        if (page == 0)
            mPosts.clear()

        mPosts.addAll(posts)
    }

    private fun updateCountDisplay() {
        tv_count.text = mPosts.count { it.isSelected }.toString()
    }


    override fun onClick(p0: View?) {
        mPosts[p0!!.tag as Int].isSelected = !mPosts[p0!!.tag as Int].isSelected
        rv_posts.adapter!!.notifyItemChanged(p0!!.tag as Int)
        updateCountDisplay()
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        mPosts[p0!!.tag as Int].isSelected = p1
        updateCountDisplay()
    }
}
