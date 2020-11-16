package com.example.stu.sy_tec

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.*

/**
 * Created by stu on 2020/11/16.
 *
 */
object FileUtil {

    //已存在文件判断
    fun isExternalStorageExist(context: Context): Boolean {
        val dir: File = context!!.getExternalFilesDir("github")
        val file = File(dir, "github.txt")
        if (!dir.exists() || !file.exists()) {
            return false
        }
        return true
    }

    //可写判断
    fun isExternalStorageWritable(): Boolean {
        val state: String = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true
        }
        Log.e("state=", "" + state)
        return false
    }

    //写入string
    fun writeStringToFile(context: Context, str: String, finish: (() -> Unit)?) {
        if (!isExternalStorageWritable()) {
            return
        }
        val dir: File = context!!.getExternalFilesDir("github")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = File(dir, "github.txt")
        if (file.exists()) {
            file.delete()
        }
        var fos: FileOutputStream? = null
        var bos: BufferedOutputStream? = null
        try {
            file.createNewFile()
            fos = FileOutputStream(file)
            bos = BufferedOutputStream(fos)
            bos.write(str.toByteArray())
            bos.close()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (bos != null) {
                    bos.close()
                }
                if (fos != null) {
                    fos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        finish?.invoke()
    }

    //读出string
    fun readStringFromFile(context: Context): String? {
        val dir: File = context.getExternalFilesDir("github")
        val file = File(dir, "github.txt")
        var fis: FileInputStream? = null
        var isr: InputStreamReader? = null
        var br: BufferedReader? = null
        val sb = StringBuffer()
        try {
            fis = FileInputStream(file)
            isr = InputStreamReader(fis)
            br = BufferedReader(isr)
            sb.append(br.readLine())
            br.close()
            isr.close()
            fis.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (br != null) {
                    br.close()
                }
                if (isr != null) {
                    isr.close()
                }
                if (fis != null) {
                    fis.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        Log.e("stu", sb.toString())
        return sb.toString()
    }
}
