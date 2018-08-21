package cn.cyrus.translater.base

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import cn.cyrus.translater.R

/**
 * Created by ChenLei on 2018/8/21 0021.
 */


fun getEmptyView(msg: String, layoutInflater: LayoutInflater): View {
    var tv: TextView = layoutInflater.inflate(R.layout.empty_tv, null) as TextView
    tv.text = msg
    return tv
}