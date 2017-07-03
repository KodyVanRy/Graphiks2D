package com.desitum.library.view

import com.badlogic.gdx.files.FileHandle
import com.google.gson.GsonBuilder

/**
 * Created by kodyvanry on 7/1/17.
 */
class LayoutInflater {

    companion object {
        fun inflate(file : FileHandle, rootView : ViewGroup?) : View {
            val jsonString = file.readString()
            var gson = GsonBuilder().registerTypeAdapter(View::class.java, GsonViewAdapter()).create()
            val view = gson.fromJson(jsonString, View::class.java)
            rootView?.addView(view)
            return view
        }
    }

}