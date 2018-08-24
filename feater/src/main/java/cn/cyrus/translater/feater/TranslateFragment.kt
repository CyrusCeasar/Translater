package cn.cyrus.translater.feater

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import cn.cyrus.translater.base.*
import com.youdao.sdk.ydtranslate.Translate
import com.youdao.sdk.ydtranslate.TranslateErrorCode
import com.youdao.sdk.ydtranslate.TranslateListener

class TranslateFragment : BaseLazyInitFragment() {


    val TAG = TranslateFragment::class.java.simpleName

    lateinit var metInput: EditText
    lateinit var mlvResults: ListView
    val results: ArrayList<String> = ArrayList()
    lateinit var adapter: ArrayAdapter<String>
    lateinit var btnClear: Button
    lateinit var btnTranslate: Button
    lateinit var etInput: EditText

    override fun initView(layoutInflater: LayoutInflater): View? {
        val view = layoutInflater.inflate(R.layout.fragment_translate, null)
        metInput = view.findViewById(R.id.et_input)
        mlvResults = view.findViewById(R.id.lv_results)
        btnClear = view.findViewById(R.id.btn_clear)
        btnTranslate = view.findViewById(R.id.btn_translate)
        etInput = view.findViewById(R.id.et_input)
        adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, results)
        mlvResults.adapter = adapter

        btnClear.setOnClickListener {
            etInput.setText("")
        }

        btnTranslate.setOnClickListener {
            val input: String = etInput.text.toString()
            if (TextUtils.isEmpty(input)) {
            } else {
                TranslateUtil.translate(input, object : TranslateListener {
                    override fun onResult(p0: Translate, p1: String?, p2: String?) {
                        results.clear()
                        val sb = StringBuilder()


                        if (p0.getExplains() != null && !p0.getExplains().isEmpty()) {
                            for (content in p0.getExplains()) {
                                results.add(content)
                                sb.append(content)
                            }
                        }

                        if (p0.getTranslations() != null && !p0.getTranslations().isEmpty()) {
                            for (content in p0.getTranslations()) {
                                results.add(content)
                                sb.append(content)
                            }
                        }

                        val trs = sb.toString()
                        val src = ""

                        val param = "words=$input&src_content=$src&display_content=$trs"

                        var trss: TranslateService = RetrofitManager.instance.create(TranslateService::class.java)
                        syncWrok(trss.query(words = input, src_content = src, display_content = trs), {
                            Log.d(TAG, "result ok" + it.isResultOk())
                        })
                        notifyDataChange()
                    }

                    override fun onResult(p0: MutableList<Translate>?, p1: MutableList<String>?, p2: MutableList<TranslateErrorCode>?, p3: String?) {
                        LogUtil.d(TAG,p1!!.toString())
                    }

                    override fun onError(p0: TranslateErrorCode?, p1: String?) {
                        results.clear()
                        results.add(p0.toString())
                        notifyDataChange()
                    }

                })
            }
        }
        return view
    }


    fun notifyDataChange() {
        activity!!.runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }
}
