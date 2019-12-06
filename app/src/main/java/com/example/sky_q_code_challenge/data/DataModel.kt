package com.example.sky_q_code_challenge.data

import com.squareup.moshi.Json

data class MovieResponse(
    @Json(name = "data") val data : List<Movie>
)

data class Movie (

    @Json(name = "id") val id : Int,
    @Json(name ="title") val title : String,
    @Json(name ="year") val year : Int,
    @Json(name ="genre") val genre : String,
    @Json(name ="poster") val poster : String
)
