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
                    .methodCount(5)         // (Optional) How many method line to show. Default 2
                    .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
                    .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                    .build()

            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
            Logger.addLogAdapter(object : AndroidLogAdapter() {
                override fun isLoggable(priority: Int, tag: String?): Boolean {
                    return BuildConfig.DEBUG
                }
            })
        }
        fun i(content: String,tag: String = TAG) {
            Logger.t(tag).i(content)
        }


        fun d( content: String,tag: String = TAG) {
            Logger.t(tag).d(content)
        }


        fun e( content: String,tag: String = TAG) {
            Logger.t(tag).e(content)
        }


        fun w( content: String,tag: String = TAG) {
            Logger.t(tag).w(content)
        }

        fun json( content: String,tag: String = TAG) {
            Logger.t(tag).json(content)
        }

        fun xml( content: String,tag: String = TAG) {
            Logger.t(tag).xml(content)
        }
    }


}

