package cn.cyrus.translater.feater

import android.util.Log
import com.youdao.sdk.app.LanguageUtils
import com.youdao.sdk.ydonlinetranslate.Translator
import com.youdao.sdk.ydtranslate.Translate
import com.youdao.sdk.ydtranslate.TranslateErrorCode
import com.youdao.sdk.ydtranslate.TranslateListener
import com.youdao.sdk.ydtranslate.TranslateParameters

class TranslateUtil {
    companion object {
        val APP_KEY = "6167f7f681bc4ae7"
        val APP_SECRET = "rjpPbZkdjlChQB7qPJlVOrIEtxM4gGBa"
        val TAG = TranslateUtil::class.java.simpleName
        fun translate(content: String, callback: (Translate, String) -> Unit,onError:(TranslateErrorCode,String)->Unit) {
            Log.d(TAG, content)
            // source app
            val langFrom = LanguageUtils.getLangByName("英文")
            val langTo = LanguageUtils.getLangByName("中文")
            val tps = TranslateParameters.Builder()
                    .source("wrmz")
                    .from(langFrom).to(langTo).build()
            val translator = Translator.getInstance(tps)
            translator.lookup(content, "", object : TranslateListener {
                override fun onError(translateErrorCode: TranslateErrorCode, s: String) {
                    onError.invoke(translateErrorCode,s)
                }

                override fun onResult(translate: Translate, s: String, s1: String) {
                    callback.invoke(translate, content)
                }

                override fun onResult(list: List<Translate>, list1: List<String>, list2: List<TranslateErrorCode>, s: String) {

                }
            })
        }
    }


}