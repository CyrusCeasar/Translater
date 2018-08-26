package cn.cyrus.translater.base

import android.content.Context
import android.widget.Toast


fun showToast(ctx:Context,msg:String,len:Int = Toast.LENGTH_LONG){
    Toast.makeText(ctx,msg,len).show()
}