package cn.cyrus.translater.base

/**
 * Created by ChenLei on 2018/8/18 0018.
 */


class Result<T>{

    var result_code:Int =0
    var result_msg:String? = null
    var data:T? = null

    fun isResultOk():Boolean{
        return result_code== 0
    }
}