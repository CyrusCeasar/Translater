package cn.cyrus.translater.feater;


import android.util.Log;

import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;


public class TranslateUtil {
    public static final String APP_KEY = "6167f7f681bc4ae7";
    public static final String APP_SECRET = "rjpPbZkdjlChQB7qPJlVOrIEtxM4gGBa";


    public static final String TAG = TranslateUtil.class.getSimpleName();

    public static void translate(String content,TranslateListener translateListener) {
        Log.d(TAG,content);
        // source app
        Language langFrom = LanguageUtils.getLangByName("英文");
        Language langTo = LanguageUtils.getLangByName("中文");
        TranslateParameters tps = new TranslateParameters.Builder()
                .source("wrmz")
                .from(langFrom).to(langTo).build();
        Translator translator = Translator.getInstance(tps);
        translator.lookup(content, "",translateListener);
    }

}
