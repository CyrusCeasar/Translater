package cn.cyrus.translater.base

import cn.cyrus.translater.BuildConfig
import com.orhanobut.logger.Logger
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.PrettyFormatStrategy
import com.orhanobut.logger.FormatStrategy





/**
 * Created by ChenLei on 2018/8/20 0020.
 */


class LogUtil {
    companion object {
        val TAG = "LogUtil"
        init {
            val formatStrategy = PrettyFormatStrategy.newBuilder()
                    .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                    .methodCount(0)         // (Optional) How many method line to show. Default 2
                    .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                    .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                    .build()

            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
            Logger.addLogAdapter(object : AndroidLogAdapter() {
                override fun isLoggable(priority: Int, tag: String?): Boolean {
                    return BuildConfig.DEBUG
                }
            })
        }
        fun i(tag: String = TAG, content: String) {
            Logger.t(tag).i(content)
        }


        fun d(tag: String = TAG, content: String) {
            Logger.t(TAG).d(content)
        }


        fun e(tag: String = TAG, content: String) {
            Logger.t(TAG).e(content)
        }


        fun w(tag: String = TAG, content: String) {
            Logger.t(TAG).w(content)
        }

        fun json(tag: String = TAG, content: String) {
            Logger.t(TAG).json(content)
        }

        fun xml(tag: String = TAG, content: String) {
            Logger.t(TAG).xml(content)
        }
    }


}

