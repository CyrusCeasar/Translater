package cn.cyrus.translater.base

import android.app.Application

open class BaseApplication: Application() {

    companion object {
        lateinit var INSANCE: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSANCE = this
    }
}