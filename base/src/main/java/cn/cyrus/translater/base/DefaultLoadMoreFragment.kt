package cn.cyrus.translater.base

/**
 * Created by ChenLei on 2018/8/21 0021.
 */
abstract class DefaultLoadMoreFragment<T> : BaseLoadFragment<T, Int>() {

    override fun getPageManager(): PageManager<Int> {
        return NumberPageManager(0)
    }


    class NumberPageManager(page: Int) : PageManager<Int>(page) {
        override fun reset() {
            pageIndicator = 1
        }

        override fun convertToNextPage(indicator: Int): Int {
            return indicator.plus(1)
        }
    }

}