package cn.cyrus.translater.feater

import android.graphics.Rect
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import cn.cyrus.translater.base.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    val fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }



        fragments.add(TranslateFragment())
        fragments.add(PlaceholderFragment.newInstance(PlaceholderFragment.TYPE_DELETED))
        mSectionsPagerAdapter!!.notifyDataSetChanged()

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragments[position]
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return fragments.size
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {
        var datas: ArrayList<TranslateRecord> = ArrayList()
        val adapter: RecordAdapter = RecordAdapter(datas)
        var page:Int = 1

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_translate_records, container, false) as SwipeRefreshLayout

            val rc:RecyclerView = rootView.findViewById(R.id.rc)
            val type = arguments?.getString(ARG_SECTION_TYPE)


            var serv: TranslateRecordService = RetrofitManager.instance.create(TranslateRecordService::class.java)
            rc.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            rc.adapter = adapter
            adapter.setOnLoadMoreListener({
                syncWrok(serv.recordList(1, page), {
                    if (it.isResultOk()) {
                        datas.addAll(it.data!!.asIterable())
                        adapter.notifyDataSetChanged()
                        page++
                        adapter.loadMoreComplete()
                    }else{
                        adapter.loadMoreFail()
                    }
                })

            },rc)

            rootView.setOnRefreshListener {
                page=0
                syncWrok(serv.recordList(1, page), {
                    if (it.isResultOk()) {
                        datas.clear()
                        datas.addAll(it.data!!.asIterable())
                        adapter.notifyDataSetChanged()
                        page++
                    }else{
                        adapter.loadMoreFail()
                    }
                    if(rootView.isRefreshing)
                        rootView.isRefreshing = false
                })
            }

            syncWrok(serv.recordList(1, page), {
                if (it.isResultOk()) {
                    datas.addAll(it.data!!.asIterable())
                    adapter.notifyDataSetChanged()
                    page++
                    adapter.loadMoreComplete()
                }else{
                    adapter.loadMoreFail()
                }
            })


            /*  Thread{
                  kotlin.run {
                      val result = HttpUtil.get(url)
                      activity!!.runOnUiThread {
                      }
                  }
              }.start()*/


            return rootView
        }


        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_TYPE = "section_type"

            public val TYPE_LIST = "list"
            public val TYPE_REMBERED = "rembered"
            public val TYPE_DELETED = "deleted_list"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: String): PlaceholderFragment {
                val fragment = PlaceholderFragment()
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
}
