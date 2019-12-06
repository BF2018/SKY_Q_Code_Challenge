package com.example.sky_q_code_challenge.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.sky_q_code_challenge.R
import com.example.sky_q_code_challenge.data.Data
import com.example.sky_q_code_challenge.data.Movies
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*


class MoviesAdapter (private val movies: Movies) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() ,Filterable {

    var filterdList  : Movies? = null
    var newFilteredList : Movies? = null

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

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filterdList = movies
                } else {
                    for (movie in movies.data) {
                        if (movie.title.toLowerCase().contains(charString) ||
                            movie.genre.toLowerCase().contains(charString) ||
                            movie.year.toString().toLowerCase().contains(charString)
                        ) {
                            var filteredList = mutableListOf<Data>()
                            filteredList.add(movie)
                        }
                    }
                    newFilteredList = filterdList
                }
                val filterResults = FilterResults()
                filterResults.values = newFilteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                newFilteredList = filterResults.values as Movies
                notifyDataSetChanged()
            }
        }
    }
}