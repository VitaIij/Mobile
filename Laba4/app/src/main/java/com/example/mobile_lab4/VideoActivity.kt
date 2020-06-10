package com.example.mobile_lab4

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.SurfaceHolder
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_music.*
import kotlinx.android.synthetic.main.activity_music.music_position
import kotlinx.android.synthetic.main.activity_music.music_volume
import kotlinx.android.synthetic.main.activity_music.play_btn
import kotlinx.android.synthetic.main.activity_music.time_played
import kotlinx.android.synthetic.main.activity_music.time_total
import kotlinx.android.synthetic.main.activity_video.*
import java.lang.ref.WeakReference

class VideoActivity : AppCompatActivity(), SurfaceHolder.Callback {

    lateinit var surfaceHolder: SurfaceHolder
    var videoUri = 0
    lateinit var mediaPlayer: MediaPlayer
    @Volatile var shutDown = false
    val musicActivity = WeakReference<VideoActivity>(this)
    val handler = MessageHandler(musicActivity)
    var duration = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        videoUri = intent.getIntExtra(Constants.MEDIA_PATH_EXTRA,0)
        surfaceHolder = video_view.holder
        surfaceHolder.addCallback(this)
    }

    class MessageHandler(private val musicActivity: WeakReference<VideoActivity>) : Handler() {
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

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mediaPlayer = MediaPlayer.create(this, videoUri)
        mediaPlayer.isLooping = true
        mediaPlayer.setVolume(0.5f, 0.5f)
        duration = mediaPlayer.duration
        mediaPlayer.setDisplay(surfaceHolder)

        time_played.text = formatTime(0)
        time_total.text = formatTime(duration)
        music_position.max = duration

        music_volume.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
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
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder?) {}
}
