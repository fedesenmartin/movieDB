package com.fedesen.prueba.api


import com.fedesen.prueba.App
import com.fedesen.prueba.Constants
import com.fedesen.prueba.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager() {
    internal var gson: Gson
    internal val client : OkHttpClient
    internal var retrofit: Retrofit

    init {
        client =  OkHttpClient.Builder().addInterceptor { chain ->
            var request = chain.request()
            val url = request
                    .url()
                    .newBuilder()
                    .addQueryParameter(
                            App.getContext().resources.getString(R.string.api_name),
                            App.getContext().resources.getString(R.string.api_key)
                    ).build()


            request = request.newBuilder().url(url).build();
            chain.proceed(request)
        }.build()

        gson = GsonBuilder()
                .setDateFormat(Constants.DATE_FORMAT)
                .create()

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
