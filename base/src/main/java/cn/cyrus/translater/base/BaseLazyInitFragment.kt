package cn.cyrus.translater.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView


/**
 * Created by ChenLei on 2018/8/20 0020.
 */
abstract class BaseLazyInitFragment : Fragment() {
    protected var isPrepared = false
    protected var isVesible = false

    protected var mRootView: ViewGroup? = null
     var onInitFinished: (() -> Unit)? =null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = InitingView(context!!)
            if (isVesible) {
                init()
            }
        }
        val parentGroup = mRootView!!.getParent() as ViewGroup
        if (parentGroup != null) {
            parentGroup!!.removeAllViews()
        }
        return mRootView
    }


    override fun onResume() {
        super.onResume()
        isVesible = isVisible && isPrepared
    }


    override fun onPause() {
        super.onPause()
        isVesible = false

    }

    /**.
     * Description
     * @param isVisibleToUser
     * @see
     */

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isVesible = isVisibleToUser
        if (mRootView != null) {
            if (isVisibleToUser && !isPrepared) {
                init()
            } else if (isVisibleToUser && isPrepared) {
                onInitFinished?.invoke()
            }
        }
    }

    private fun init() {

        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        val view = initView(LayoutInflater.from(context))
        if (view != null) {
            mRootView!!.removeAllViews()
            mRootView!!.addView(view, layoutParams)
            isPrepared = true
            onInitFinished?.invoke()
        }

    }

    abstract fun initView(layoutInflater: LayoutInflater): View?


    inner class InitingView : LinearLayout {

        constructor(context: Context) : super(context) {
            init()
        }

        constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
            init()
        }

        private fun init() {
            initParams()
            initPromptContent()
        }

        private fun initParams() {
            setOrientation(LinearLayout.VERTICAL)
            setGravity(Gravity.CENTER)
        }

        private fun initPromptContent() {
            val progressBar = ProgressBar(context)
            addView(progressBar)
            val textView = TextView(context)
            textView.setText("初始化中....")
            textView.setGravity(Gravity.CENTER)
            textView.setTextColor(Color.BLACK)
            addView(textView)

        }


        inner class InitingView : LinearLayout {
            constructor(context: Context) : super(context) {
                init()
            }

            constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
                init()
            }

            private fun init() {
                initParams()
                initPromptContent()
            }

            private fun initParams() {
                setOrientation(LinearLayout.VERTICAL)
                setGravity(Gravity.CENTER)
            }

            private fun initPromptContent() {
                val progressBar = ProgressBar(context)
                addView(progressBar)
                val textView = TextView(context)
                textView.setText("loading....")
                textView.setGravity(Gravity.CENTER)
                textView.setTextColor(Color.BLACK)
                addView(textView)

            }

        }
    }
}