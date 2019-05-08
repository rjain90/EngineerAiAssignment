package com.rjain90.engineeraiassignment.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rjain90.engineeraiassignment.R
import com.rjain90.engineeraiassignment.model.Post
import kotlinx.android.synthetic.main.item_post.view.*
import java.text.SimpleDateFormat
import java.util.*

class PostAdapter(private var items: MutableList<Post>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemFromDateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault())
    private val itemToDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_post, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val date = itemFromDateFormat.parse(items[position].createdAt)
        (holder as ItemViewHolder).tvCreatedAt.text = itemToDateFormat.format(date)
        holder.tvTitle.text = items[position].title
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setPosts(items: MutableList<Post>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun addData(items: List<Post>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val tvTitle = view.tv_title
        val tvCreatedAt = view.tv_createdAt
        val switchPost = view.switch_post
    }
}