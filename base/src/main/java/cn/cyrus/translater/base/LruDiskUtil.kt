package cn.cyrus.translater.base

import com.jakewharton.disklrucache.DiskLruCache
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.InputStream
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
            LogUtil.d(String(datas))
            Observable.create<Any> {
                val editor: DiskLruCache.Editor = LruDiskUtil.CACHE.edit(hashKeyForDisk(key))
                val ops: OutputStream = editor.newOutputStream(0)
                ops.write(datas,0,datas.size)
                editor.commit()
                ops.close()
                CACHE.flush()
            }.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe {
                callBack?.invoke()
            }
        }

        fun get(key:String,callBack:(ByteArray?)->Unit){
            Observable.create<ByteArray?> {
                var snapshot:DiskLruCache.Snapshot? = CACHE.get(hashKeyForDisk(key))
                if(snapshot != null){
                    val inp:InputStream = snapshot.getInputStream(0)
                    if(inp.available() == 0){
                        it.onNext(ByteArray(0))
                    }else {
                        val datas = inp.readBytes(inp.available())
                        LogUtil.d(String(datas,0,datas.size))
                        it.onNext(datas)
                    }
                    inp.close()
                }else{
                    it.onNext(ByteArray(0))
                }

            }.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe {
                callBack.invoke(it)
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