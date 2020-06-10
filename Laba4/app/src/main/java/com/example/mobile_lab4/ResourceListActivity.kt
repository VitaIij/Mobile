package com.example.mobile_lab4

import android.content.ContentUris
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_resource_list.*


class ResourceListActivity : AppCompatActivity() {

    lateinit var mediaType: String;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource_list)

        mediaType = intent.getStringExtra(Constants.MEDIA_TYPE_EXTRA)

        if (mediaType == Constants.MUSIC_TYPE) {
            val list = getMusicResourceList()
            recycler_view.layoutManager = LinearLayoutManager(this)
            val listAdapter = ResourceRecyclerAdapter(list) {
                val intent = Intent(this, MusicActivity::class.java)
                intent.putExtra(Constants.MEDIA_PATH_EXTRA, it.uri)
                startActivity(intent)
            }
            recycler_view.adapter = listAdapter
        } else {
            val list = getVideoResourceList()
            recycler_view.layoutManager = LinearLayoutManager(this)
            val listAdapter = ResourceRecyclerAdapter(list) {
                val intent = Intent(this, VideoActivity::class.java)
                intent.putExtra(Constants.MEDIA_PATH_EXTRA, it.uri)
                startActivity(intent)
            }
            recycler_view.adapter = listAdapter
        }


    }

    fun getMusicResourceList(): MutableList<MediaResource> {
        val list = mutableListOf<MediaResource>()
        list.add(MediaResource(R.raw.music1,resources.getResourceEntryName(R.raw.music1)))
        list.add(MediaResource(R.raw.music2,resources.getResourceEntryName(R.raw.music2)))
        return list
    }



    fun getVideoResourceList(): MutableList<MediaResource> {
        val list = mutableListOf<MediaResource>()
        list.add(MediaResource(R.raw.video1,resources.getResourceEntryName(R.raw.video1)))
        list.add(MediaResource(R.raw.video2,resources.getResourceEntryName(R.raw.video2)))
        return list
    }
}
