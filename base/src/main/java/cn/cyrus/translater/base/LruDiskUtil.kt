package cn.cyrus.translater.base

import com.jakewharton.disklrucache.DiskLruCache
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.OutputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by ChenLei on 2018/8/23 0023.
 */
class LruDiskUtil {

    companion object {
        const val MAX_SIZE = 1024 * 1024 * 1024L  //1G

        lateinit var CACHE: DiskLruCache

        init {
            val file: File = getDiskCacheDir(BaseApplication.INSANCE, "LruDisk")
            if (!file.exists()) {
                file.mkdirs()
            }
            CACHE = DiskLruCache.open(file, 1, 1, MAX_SIZE)
        }


        fun save(key: String, datas: ByteArray, callBack: (() -> Unit)? = null) {
            if (datas.isEmpty())
                return
            Observable.create<Any> {
                val editor: DiskLruCache.Editor = LruDiskUtil.CACHE.edit(key)
                val ops: OutputStream = editor.newOutputStream(0)
                ops.write(datas)
                editor.commit()
                CACHE.flush()
            }.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe {
                callBack?.invoke()
            }
        }

        fun get(key:String,callBack:(ByteArray?)->Unit){
            Observable.create<ByteArray> {
                val snapshot = CACHE.get(key)
            }.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe {
                callBack?.invoke()
            }
        }


        fun hashKeyForDisk(key: String): String {
            var cacheKey: String? = null
            cacheKey = try {
                val mDigest: MessageDigest = MessageDigest.getInstance("MD5")
                mDigest.update(key.toByteArray())
                bytesToHexString(mDigest.digest())
            } catch (e: NoSuchAlgorithmException) {
                key.hashCode().toString()
            }
            return cacheKey
        }

        private fun bytesToHexString(bytes: ByteArray): String {
            // http://stackoverflow.com/questions/332079
            val sb = StringBuilder()
            bytes.forEach { i ->
                val hex: String = Integer.toHexString(0xFF and i.toInt())
                if (hex.length == 1) {
                    sb.append('0')
                }
                sb.append(hex)
            }
            return sb.toString()
        }


    }


}