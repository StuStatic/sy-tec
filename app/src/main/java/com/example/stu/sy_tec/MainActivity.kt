package com.example.stu.sy_tec

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

/**
 * Created by stu on 2020-11-16.
 *
 */

class MainActivity : AppCompatActivity() {

    private var dispose: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    @SuppressLint("CheckResult")
    private fun init() {
        if (FileUtil.isExternalStorageExist(this)) {
            Toast.makeText(this, "开始从文件中读取！",Toast.LENGTH_LONG)
            results.text = FileUtil.readStringFromFile(this)
        } else {
            dispose = Observable.interval(5, TimeUnit.SECONDS)
                .delay(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e("stu","计时1次")
                    api.getInfo()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            results.text = it.toString()
                            if (FileUtil.isExternalStorageWritable())
                                FileUtil.writeStringToFile(this, it.toString(),{
                                    dispose?.dispose()
                                })
                        }
                }
        }


    }
}