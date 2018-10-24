package cn.cyrus.translater.feater

import android.media.AudioManager
import android.media.MediaPlayer
import cn.cyrus.translater.base.LogUtil
import com.youdao.sdk.common.YouDaoLog

/**
 * Created by ChenLei on 2018/10/23 0023.
 */
class AudioManager {
    val mMediaPlayer:MediaPlayer

    init {
        mMediaPlayer = MediaPlayer()
    }

    fun play(path:String){
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mMediaPlayer.setOnErrorListener(MediaPlayer.OnErrorListener { mp, what, extra ->
            LogUtil.e(what.toString()+"   "+extra.toString())
            true
        })
        mMediaPlayer.setOnPreparedListener {
            it.start()
        }
        try {
            mMediaPlayer.reset()
            mMediaPlayer.setDataSource(path)
            mMediaPlayer.prepareAsync()// 进行缓冲\
        } catch (e: Exception) {
            e.printStackTrace()
            YouDaoLog.e( "AudioMgr playTask prepare error = " + e.toString())
        }

    }
}