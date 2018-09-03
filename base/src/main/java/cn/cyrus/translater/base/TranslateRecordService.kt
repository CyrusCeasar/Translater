package cn.cyrus.translater.base

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ChenLei on 2018/8/18 0018.
 */
interface TranslateRecordService {

    @GET("translate_record/list")
    fun recordList(@Query("type") type:Int, @Query("page") page:Int):Observable<Result<ArrayList<TranslateRecord>>>

    @GET("translate_record/delete")
    fun delete(@Query("words") words:String):Observable<Result<Int>>


}