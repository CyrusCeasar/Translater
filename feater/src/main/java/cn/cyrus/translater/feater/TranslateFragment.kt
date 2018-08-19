package cn.cyrus.translater.feater

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import cn.cyrus.translater.base.RetrofitManager
import cn.cyrus.translater.base.TranslateService
import cn.cyrus.translater.base.syncWrok
import com.youdao.sdk.ydtranslate.Translate
import com.youdao.sdk.ydtranslate.TranslateErrorCode
import com.youdao.sdk.ydtranslate.TranslateListener

class TranslateFragment : Fragment() {
    val TAG = "TranslateFragment"

    var metInput: EditText? = null
    var mlvResults: ListView? = null
    val results: ArrayList<String> = ArrayList()
    var adapter: ArrayAdapter<String>? = null
    var btnClear: Button? = null
    var btnTranslate: Button? = null
    var etInput: EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_translate, null);


        metInput = view.findViewById(R.id.et_input)
        mlvResults = view.findViewById(R.id.lv_results)
        btnClear = view.findViewById(R.id.btn_clear)
        btnTranslate = view.findViewById(R.id.btn_translate)
        etInput = view.findViewById(R.id.et_input)
        adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, results)
        mlvResults!!.adapter = adapter

        btnClear!!.setOnClickListener {
            etInput!!.setText("")
        }

        btnTranslate!!.setOnClickListener {
            val input: String = etInput!!.text.toString()
            if (TextUtils.isEmpty(input)) {
            } else {
                TranslateUtil.translate(input, object : TranslateListener {
                    override fun onResult(p0: Translate?, p1: String?, p2: String?) {
                        results.clear()
                        val sb = StringBuilder()

                        if (p0!!.getExplains() != null && !p0!!.getExplains().isEmpty()) {
                            for (content in p0!!.getExplains()) {
                                results.add(content)
                                sb.append(content)
                            }
                        }


                        if (p0!!.getTranslations() != null && !p0!!.getTranslations().isEmpty()) {
                            for (content in p0!!.getTranslations()) {
                                results.add(content)
                                sb.append(content)
                            }
                        }

                        val trs = sb.toString()
                        val src = ""

                        val param = "words=$input&src_content=$src&display_content=$trs"

                        var trss: TranslateService = RetrofitManager.instance.create(TranslateService::class.java)
                        syncWrok( trss.query(words = input, src_content = src, display_content = trs), {
                            Log.d(TAG, "result ok"+it.isResultOk())
                        })
                        /* Thread {
                             kotlin.run {

                             }
                         }.start()*/
                        notifyDataChange()
                    }

                    override fun onResult(p0: MutableList<Translate>?, p1: MutableList<String>?, p2: MutableList<TranslateErrorCode>?, p3: String?) {

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
            adapter!!.notifyDataSetChanged()
        }
    }
}
