package com.example.stu.sy_tec

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by stu on 2020-11-16.
 *
 */
interface Api {
    @GET("https://api.github.com")
    fun getInfo(): Observable<Any>
}