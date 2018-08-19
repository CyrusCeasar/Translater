package cn.cyrus.translater.base

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ChenLei on 2018/8/18 0018.
 */

fun <T> syncWrok (task: Observable<Result<T>>, callBack:(Result<T>)->Unit){
    task.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{callBack(it)}
}