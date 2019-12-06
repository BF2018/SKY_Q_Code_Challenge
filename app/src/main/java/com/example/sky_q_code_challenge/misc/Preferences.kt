package com.example.sky_q_code_challenge.misc

import android.content.Context
import android.content.SharedPreferences
import com.example.sky_q_code_challenge.data.Movies
import com.google.gson.Gson
import java.util.concurrent.TimeUnit


class Preferences (context: Context?){

    val preferences: SharedPreferences = context?.applicationContext!!.getSharedPreferences("SKYQ", Context.MODE_PRIVATE)

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

    private fun saveApiData(key: String, jsonObject: Any?): Boolean {
        val string = if (jsonObject == null) {
            null
        } else {
            Gson().toJson(jsonObject).toString()
        }
        return set(key, string)
    }

    fun getSavedMoviesData(key : String) : Movies?{
        return getSavedApiData(key, Movies::class.java)
    }

    fun setMoviesData(key: String, movies: Movies) : Boolean
    {
        return saveApiData(key , movies)
    }

    fun clearPref(){

    }

    fun setTimerForCache(){
        val editor = preferences.edit()
        editor.putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10))
        editor.apply()
    }

}