package com.fedesen.prueba.data.api


import com.fedesen.prueba.App
import com.fedesen.prueba.Constants
import com.fedesen.prueba.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class RetrofitManager() {
    internal var gson: Gson
    internal val client : OkHttpClient
    internal var retrofit: Retrofit

    init {

        gson = GsonBuilder()
                .setDateFormat(Constants.DATE_FORMAT)
                .create()


        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(App.getContext().cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())


        client =  OkHttpClient.Builder().addInterceptor { chain ->
            var request = chain.request()

            val cacheControl = CacheControl.Builder()
                    .maxAge(1, TimeUnit.DAYS)
                    .build()

            val url = request
                    .url()
                    .newBuilder()
                    .addQueryParameter(
                            App.getContext().resources.getString(R.string.api_name),
                            App.getContext().resources.getString(R.string.api_key)
                    ).build()


            request = request.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .url(url)
                    .build()
            chain.proceed(request)
        }
                .cache(cache)
                .build()


        retrofit = Retrofit.Builder()
                .baseUrl(App.getContext().resources.getString(R.string.str_comm_base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                . addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    fun getInstance(): Retrofit {
        return retrofit
    }
}
