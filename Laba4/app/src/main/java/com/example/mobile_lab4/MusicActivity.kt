package com.example.mobile_lab4

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_music.*
import java.lang.ref.WeakReference

class MusicActivity : AppCompatActivity() {

    lateinit var mediaPlayer: MediaPlayer
    var shutDown = false
    val musicActivity = WeakReference<MusicActivity>(this)
    val handler = MessageHandler(musicActivity)
    var duration = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        val musicUri = intent.getIntExtra(Constants.MEDIA_PATH_EXTRA, 0)

        mediaPlayer = MediaPlayer.create(this, musicUri)
        mediaPlayer.isLooping = true
        mediaPlayer.setVolume(0.5f, 0.5f)
        duration = mediaPlayer.duration

        music_volume.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        val volume = progress / 100.0f
                        mediaPlayer.setVolume(volume, volume)
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            }
        )

        time_played.text = formatTime(0)
        time_total.text = formatTime(duration)
        music_position.max = duration
        music_position.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress)
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            }
        )

        Thread(Runnable {
            while (true) {
                if (shutDown)
                    return@Runnable
                try {
                    var msg = Message()
                    msg.what = mediaPlayer.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {}
            }
        }).start()
    }

    class MessageHandler(private val musicActivity: WeakReference<MusicActivity>) : Handler() {
        override fun handleMessage(msg: Message) {
            var progress = msg.what
            musicActivity.get()?.music_position?.progress = progress

            musicActivity.get()?.time_played?.text = musicActivity.get()?.formatTime(progress)
        }
    }

    fun formatTime(progress: Int): String {
        val min = progress / 1000 / 60
        val sec = progress / 1000 % 60
        var timeLabel = "$min:"
        if (sec < 10)
            timeLabel += "0"
        timeLabel += sec
        return timeLabel
    }

    fun playBtnClick(view: View) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            play_btn.text = getText(R.string.player_play)
        } else {
            mediaPlayer.start()
            play_btn.text = getText(R.string.player_pause)
        }
    }

    fun stopBtnClick(view: View) {
        finish()
    }

    fun stopPlayer() {
        mediaPlayer.stop()
        mediaPlayer.release()
        shutDown = true
    }

    override fun onDestroy() {
        stopPlayer()
        super.onDestroy()
    }
}
