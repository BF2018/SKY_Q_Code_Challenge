package com.example.sky_q_code_challenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sky_q_code_challenge.R
import com.example.sky_q_code_challenge.data.Movies
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*

class MoviesAdapter (private val movies: Movies) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = movies.data[position].title
        holder.genre.text = movies.data[position].genre
        holder.year.text = movies.data[position].year.toString()
        Picasso.get().load(movies.data[position].poster).into(holder.imageView)
    }



    inner class ViewHolder(item : View) : RecyclerView.ViewHolder(item) {
       internal var title = item.title
       internal var genre = item.genre
        internal var year = item.year
       internal var imageView = item.imageView

    }
}