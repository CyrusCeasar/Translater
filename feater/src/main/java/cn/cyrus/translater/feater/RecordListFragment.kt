package cn.cyrus.translater.feater

import android.os.Bundle

import cn.cyrus.translater.base.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by ChenLei on 2018/8/21 0021.
 */
/**
 * A placeholder fragment containing a simple view.
 */
class RecordListFragment : DefaultLoadMoreFragment<TranslateRecord>() {
    override fun load(onUpdate: (List<TranslateRecord>) -> Unit) {
        mPageManager.reset()
        syncWrok(serv.recordList(1, mPageManager!!.pageIndicator!!), {
            if (it.isResultOk()) {
                onUpdateListener.invoke(it.data!!)
            } else {
                updateError()
            }
        })
    }

    var serv: TranslateRecordService = RetrofitManager.instance.create(TranslateRecordService::class.java)
    override fun loadMore(onLoadMore: (List<TranslateRecord>) -> Unit) {
        syncWrok(serv.recordList(1, mPageManager!!.pageIndicator!!), {
            if (it.isResultOk()) {
                onLoadMore.invoke(it.data!!)
            } else {
                loadMoreError()
            }
        })
    }

    override fun getAdapter(): BaseQuickAdapter<TranslateRecord, BaseViewHolder> {
        return RecordAdapter(mDatas)
    }






    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_SECTION_TYPE = "section_type"

        val TYPE_LIST = "list"
        val TYPE_REMBERED = "rembered"
        val TYPE_DELETED = "deleted_list"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: String): RecordListFragment {
            val fragment = RecordListFragment()
            val args = Bundle()
            args.putString(ARG_SECTION_TYPE, sectionNumber)
            fragment.arguments = args
            return fragment
        }

    }

    class RecordAdapter(datas: List<TranslateRecord>) : BaseQuickAdapter<TranslateRecord, BaseViewHolder>(R.layout.item_words, datas) {
        override fun convert(helper: BaseViewHolder?, item: TranslateRecord?) {
            helper!!.setText(R.id.tv_words, item!!.words_text)
            helper.setText(R.id.tv_display_content, item.display_content)
            helper.setText(R.id.tv_query_num, item.quest_num.toString())
        }

    }
}