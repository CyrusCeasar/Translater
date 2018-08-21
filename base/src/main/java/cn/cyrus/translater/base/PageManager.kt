package cn.cyrus.translater.base

/**
 * Created by ChenLei on 2018/8/21 0021.
 */
abstract class PageManager<T> {
    var pageIndicator: T? = null

    constructor(indicator: T) {
        pageIndicator = indicator
    }


    fun toNextPage(){
        pageIndicator = convertToNextPage(pageIndicator!!)
    }

    abstract fun reset()
    abstract fun convertToNextPage(indicator: T):T

}