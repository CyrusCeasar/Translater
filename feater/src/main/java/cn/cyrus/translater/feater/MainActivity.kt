package cn.cyrus.translater.feater

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.youdao.sdk.ydtranslate.Translate
import com.youdao.sdk.ydtranslate.TranslateErrorCode
import com.youdao.sdk.ydtranslate.TranslateListener

class MainActivity : AppCompatActivity() {

    var metInput: EditText? = null
    var mlvResults: ListView? = null
    val results: ArrayList<String> = ArrayList()
    var adapter: ArrayAdapter<String>? = null
    var btnClear: Button? = null
    var btnTranslate: Button? = null
    var etInput: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        metInput = findViewById(R.id.et_input)
        mlvResults = findViewById(R.id.lv_results)
        btnClear = findViewById(R.id.btn_clear)
        btnTranslate = findViewById(R.id.btn_translate)
        etInput = findViewById(R.id.et_input)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, results)
        mlvResults!!.adapter = adapter

        btnClear!!.setOnClickListener{
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
                        notifyDataChange()
                    }

                    override fun onResult(p0: MutableList<Translate>?, p1: MutableList<String>?, p2: MutableList<TranslateErrorCode>?, p3: String?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                    }

                    override fun onError(p0: TranslateErrorCode?, p1: String?) {
                        results.clear()
                        results.add(p0.toString())
                        notifyDataChange()
                    }

                })
            }
        }


    }
    fun notifyDataChange(){
        runOnUiThread {
            adapter!!.notifyDataSetChanged()
        }
    }
}
