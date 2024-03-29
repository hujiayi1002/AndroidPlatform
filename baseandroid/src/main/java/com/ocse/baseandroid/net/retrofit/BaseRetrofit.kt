package com.ocse.baseandroid.net.retrofit

import com.ocse.baseandroid.impl.AppManagerImpl
import com.ocse.baseandroid.utils.MyLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author hujiayi
 */
open class BaseRetrofit {
    private var baseUrl: String? = null

    /**
     * 统一header
     */

    private val mHeaderMap: MutableMap<String, Any> = HashMap()

    private fun setRetrofit(): Retrofit? {
        baseUrl = AppManagerImpl.getBaseUrl()
        if (retrofit == null) {
            okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder!!.retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(mHeaderInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()
            retrofit = Retrofit.Builder() //基地址
                .baseUrl(baseUrl!!)
                .client(okHttpClientBuilder!!.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit
    }

    private fun getRetrofit(): Retrofit? {
        try {
            setRetrofit()
        } catch (e: Exception) {
            MyLog.e("|-- HJY --|  ${e.message}")
            throw e
        }
        return retrofit
    }

    /**
     * 添加统一的请求头
     *
     * @param map
     * @return
     */
    fun addHeader(map: MutableMap<String, Any>?): BaseRetrofit {
        if (!map.isNullOrEmpty()) {
            mHeaderMap.putAll(map)
        }
        return this
    }

    /**
     * 创建Service
     *
     * @param apiService
     * @param <T>
     * @return
    </T> */
    fun <T> createService(apiService: Class<T>): T {
        return getRetrofit()!!.create(apiService)
    }

    /**
     * header拦截器
     */
    //最大重试次数
    var maxRetry = 0

    //假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
    private val retryNum = 0

    private val mHeaderInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
        if (mHeaderMap.isNotEmpty()) {
            for ((key, value) in mHeaderMap) {
                request.addHeader(key, value.toString())
            }
        }
        chain.proceed(request.build())
    }
    private val loggingInterceptor =
        HttpLoggingInterceptor { message -> //打印retrofit日志
            //if (isJson(message))
            MyLog.e("|-- HJY --|  $message")
        }.setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun isJson(message: String): Boolean {
        var isJson = false
        val jsonSy = "{"
        if (message.contains(jsonSy)) {
            isJson = true
        }
        return isJson
    }

    companion object {
        private var baseRetrofit: BaseRetrofit? = null

        @Volatile
        private var retrofit: Retrofit? = null

        /**
         * 对外暴露 OkHttpClient,方便自定义
         *
         * @return
         */
        var okHttpClientBuilder: OkHttpClient.Builder? = null

        fun getInstance(): BaseRetrofit {
            if (baseRetrofit == null) {
                synchronized(BaseRetrofit::class.java) {
                    if (baseRetrofit == null) {
                        baseRetrofit =
                            BaseRetrofit()
                    }
                }
            }
            return baseRetrofit!!
        }

        /**
         * 线程切换
         *
         * @param observable
         * @param <T>
         * @return
        </T> */
        fun <T> switchSchedulers(observable: Observable<T>): Observable<T> {
            return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}