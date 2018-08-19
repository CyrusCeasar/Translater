package cn.cyrus.translater.base

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ChenLei on 2018/8/18 0018.
 */
interface TranslateService {

    @GET("translate_record/query")
    fun  query(@Query("words") words: String, @Query("src_content") src_content: String, @Query("display_content") display_content: String): Observable<Result<Any>>

}