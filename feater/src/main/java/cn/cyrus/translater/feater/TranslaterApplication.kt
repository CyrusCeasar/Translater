package cn.cyrus.translater.feater

import cn.cyrus.translater.base.BaseApplication
import com.youdao.sdk.app.YouDaoApplication

class TranslaterApplication: BaseApplication() {


    override fun onCreate() {
        super.onCreate()

        YouDaoApplication.init(this, TranslateUtil.APP_KEY)
    }
}