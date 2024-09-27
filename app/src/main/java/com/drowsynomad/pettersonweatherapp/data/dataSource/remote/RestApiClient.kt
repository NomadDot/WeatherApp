package com.drowsynomad.pettersonweatherapp.data.dataSource.remote

import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.Constants.ApiClientErrorMessage
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

interface ApiClient {
    fun init()
    fun <T> provideService(service: Class<T>): T
}

class RestApiClient : ApiClient {
    companion object {
        val instance: ApiClient = RestApiClient()
    }

    private var retrofit: Retrofit? = null
    private val apiServices = HashMap<Class<*>, Any?>()

    override fun init() {
        if (retrofit == null) {
            val okHttpClient = createHttpClient()
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> createService(service: Class<T>): T {
        if (retrofit == null)
            throw ExceptionInInitializerError(ApiClientErrorMessage)
        return if (apiServices.containsKey(service))
            apiServices[service] as T
        else {
            val apiService = retrofit?.create(service)
            apiServices[service] = apiService
            apiService as T
        }
    }

    override fun <T> provideService(service: Class<T>): T {
        return createService(service)
    }

    private fun createHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain.request()
                    .url.newBuilder()
                    .addQueryParameter(Constants.AppId, Constants.WEATHER_API_KEY)
                    .build()

                val finalRequest = chain
                    .request()
                    .newBuilder()
                    .url(url).build()

                chain.proceed(finalRequest)
            }.build()

}