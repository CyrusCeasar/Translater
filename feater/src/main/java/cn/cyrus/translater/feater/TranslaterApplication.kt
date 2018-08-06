package cn.cyrus.translater.feater

import android.app.Application
import com.youdao.sdk.app.YouDaoApplication

class TranslaterApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        YouDaoApplication.init(this, TranslateUtil.APP_KEY)
    }
}