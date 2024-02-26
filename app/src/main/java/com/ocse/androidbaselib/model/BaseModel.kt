package com.ocse.androidbaselib.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.ocse.androidbaselib.bean.UserBean
import com.ocse.androidbaselib.retrofit.ApiRetrofit.Companion.instance
import com.ocse.baseandroid.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * @author hujiayi
 */
class BaseModel : BaseViewModel() {
    val loginCn by lazy { MutableLiveData<UserBean>() }
    fun user() {
        launch({
            val data = instance.loginCn("test001", "hse@123456")
            loginCn.postValue(data)
        }, {
            loginCn.postValue(null)
        })
    }

    fun loginCn() = liveData {
        emitLiveData({
            emit(instance.loginCn("test001", "hse@123456"))
        }, {
            emit(null)
        })
    }

    fun loginCnFlow() = liveData {
        flow {
            emit(instance.loginCn("test001", "hse@123456"))
        }.flowOn(Dispatchers.IO).catch {
            Log.e("TAG", "loginCn1: ${it.message}, ")
            emit(null)
        }.collectLatest {
            Log.e("TAG", "loginCn2: ${Gson().toJson(it)},")
            emit(it)
        }
    }
}