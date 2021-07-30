package com.ocse.baseandroid.net.base

import androidx.lifecycle.LiveData
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
    class LiveDataCallAdapter<T>(private val responseType: Type) : CallAdapter<T, LiveData<T>> {
        override fun adapt(call: Call<T>): LiveData<T> {
            return object : LiveData<T>() {
                private val started = AtomicBoolean(false)
                override fun onActive() {
                    super.onActive()
                    if (started.compareAndSet(false, true)) {//确保执行一次
                        call.enqueue(object : Callback<T> {
                            override fun onFailure(call: Call<T>, t: Throwable) {
                                val value = ApiResponse<T>(null, -1, t.message ?: "") as T
                                postValue(value)
                            }

                            override fun onResponse(call: Call<T>, response: Response<T>) {
                                postValue(response.body())
                            }
                        })
                    }
                }
            }
        }

        override fun responseType() = responseType
    }

    interface  LiveCallback{
        fun onResponse(string: String)
        fun onFailure(string: String)
    }
}

