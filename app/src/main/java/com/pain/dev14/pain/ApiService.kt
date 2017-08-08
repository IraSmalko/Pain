package com.pain.dev14.pain

import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by dev14 on 04.08.17.
 */
interface ApiService {

    @GET
    fun getWeather(@Url url: String): Flowable<Weather>

    companion object Factory {
        fun create(okHttpClient: OkHttpClient): ApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://query.yahooapis.com")
                    .client(okHttpClient)
                    .build()

            return retrofit.create(ApiService::class.java);
        }
    }
}
