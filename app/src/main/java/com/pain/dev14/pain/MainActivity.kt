package com.pain.dev14.pain

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.github.nitrico.lastadapter.LastAdapter
import com.github.simonpercic.oklog3.OkLogInterceptor
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient


class MainActivity : AppCompatActivity() {

    //val adapter = WeatherRecyclerAdapter()
    val stopwatch = Stopwatch()
    var i = 0
    var f = false
    val list = mutableListOf<Flowable<Weather>>()
    val listWeather = mutableListOf<Weather>()
    var concatTime = 0.0
    var mergeTime = 0.0
    lateinit var lastAdapter: LastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  rv.adapter = adapter

  //      val list = mutableListOf<Flowable<Weather>>()

        lastAdapter = LastAdapter(listWeather, BR.item)
                .map<Weather>(R.layout.rv_item)
                .into(rv)

        x()

//        val okLogInterceptor = OkLogInterceptor.builder().build()
//
//        val okHttpBuilder = OkHttpClient.Builder()
//        okHttpBuilder.addInterceptor(okLogInterceptor)
//        val okHttpClient = okHttpBuilder.build()
//
//        stopwatch.start()
//
//        for (i in 0..7) {
//            val result = ApiService.create(okHttpClient).getWeather("/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid=" + (2460286 - i + 4).toString() + "&format=json")
//                    .subscribeOn(Schedulers.io())
//                    .map {
//                        it.temp = (it.temp - 32) * 5 / 9
//                        it
//                    }
//
//            list += result
//        }
//
//        Flowable.concat(list)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ r -> displayResult(r) },
//                        { error -> Log.e("TAG", "{$error.message}") })

//        Flowable.merge(list)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ r -> displayResult(r) },
//                        { error -> Log.e("TAG", "{$error.message}") })


    }

    private fun x(): Unit {

        val okLogInterceptor = OkLogInterceptor.builder().build()

        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(okLogInterceptor)
        val okHttpClient = okHttpBuilder.build()

        stopwatch.start()

        for (i in 0..7) {
            val result = ApiService.create(okHttpClient).getWeather("/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid=" + (2460286 - i + 4).toString() + "&format=json")
                    .subscribeOn(Schedulers.io())
                    .map {
                        it.temp = (it.temp - 32) * 5 / 9
                        it
                    }

            list += result
        }
        if (!f) y() else z()
    }

    private fun y(): Unit {
        Flowable.concat(list)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ r -> displayResult(r) },
                        { error -> Log.e("TAG", "{$error.message}") })

    }

    private fun z(): Unit {
        Flowable.merge(list)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ r -> displayResult(r) },
                        { error -> Log.e("TAG", "{$error.message}") })

    }


    private fun displayResult(result: Weather) {
        ++i
       listWeather.add(result)
        lastAdapter.notifyDataSetChanged()
     //   adapter.updateAdapter(result)
        if (i == 8 && !f) {
            stopwatch.stop()
            concatTime = stopwatch.elapsedTime.toDouble()
            Toast.makeText(this, "concat " + concatTime.toString(), Toast.LENGTH_LONG).show()
       //     adapter.items.clear()
            listWeather.clear()
            list.clear()
            i = 0
            f = true
            x()
        }else if (i == 8 && f) {
            stopwatch.stop()
            mergeTime = stopwatch.elapsedTime.toDouble()
            Toast.makeText(this, "merge " + mergeTime.toString(), Toast.LENGTH_LONG).show()
            if (concatTime > mergeTime){
                Toast.makeText(this, "merge winner! with the score: " + (concatTime - mergeTime).toString() + "\n" +
                       "%.2f".format(concatTime / mergeTime) + " times faster!", Toast.LENGTH_LONG).show()
            }else  Toast.makeText(this, "concat winner! with the score: " + (mergeTime - concatTime).toString() + "\n" +
                    "%.2f".format(mergeTime / concatTime) + " times faster!", Toast.LENGTH_LONG).show()
        }
    }
}
