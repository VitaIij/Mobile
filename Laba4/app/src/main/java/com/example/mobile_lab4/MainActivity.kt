package com.example.mobile_lab4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoBtn.setOnClickListener {
            val intent = Intent(this, ResourceListActivity::class.java)
            intent.putExtra(Constants.MEDIA_TYPE_EXTRA, Constants.VIDEO_TYPE)
            startActivity(intent)
        }

        musicBtn.setOnClickListener {
            val intent = Intent(this, ResourceListActivity::class.java)
            intent.putExtra(Constants.MEDIA_TYPE_EXTRA, Constants.MUSIC_TYPE)
            startActivity(intent)
        }
    }
}
