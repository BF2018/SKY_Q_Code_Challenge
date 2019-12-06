package com.example.sky_q_code_challenge.misc

import android.content.Context
import android.content.SharedPreferences
import com.example.sky_q_code_challenge.data.MovieResponse
import com.google.gson.Gson
import java.util.concurrent.TimeUnit


class Preferences(context: Context?) {

    private val preferences: SharedPreferences =
        context?.applicationContext!!.getSharedPreferences("SKYQ", Context.MODE_PRIVATE)

    fun get(key: String, defaultValue: String?): String? {
        return preferences.getString(key, defaultValue)
    }

    fun set(key: String, value: String?): Boolean {
        val editor = preferences.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    private fun <T> getSavedApiData(key: String, classOfT: Class<T>): T? {
        val string = get(key, null)
        return if (string == null) {
            null
        } else {
            Gson().fromJson(string, classOfT)
        }
    }

    private fun saveApiData(jsonObject: Any?): Boolean {
        val string = if (jsonObject == null) {
            null
        } else {
            Gson().toJson(jsonObject).toString()
        }
        return set("savedMovieList", string)
    }

    fun getSavedMoviesData(): MovieResponse? {
        return getSavedApiData("savedMovieList", MovieResponse::class.java)
    }

    fun setMoviesData(movieResponse: MovieResponse): Boolean {
        return saveApiData(movieResponse)
    }

    fun clearPref() {
        preferences.edit().apply {
            clear()
            apply()
        }
    }

    fun setTimerForCache() {
        val editor = preferences.edit()
        editor.putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10))
        editor.apply()
    }

    fun getExpiredTime(): Long = preferences.getLong("ExpiredDate", -1)

}