package com.ocse.baseandroid.base


import android.accounts.AccountsException
import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.ocse.baseandroid.net.coroutine.SingleLiveData
import com.ocse.baseandroid.utils.MyLog
import com.ocse.baseandroid.utils.NetworkUtil
import com.ocse.baseandroid.utils.ObtainApplication
import com.ocse.baseandroid.utils.ToastUtil
import com.ocse.baseandroid.view.LoadingView
import kotlinx.coroutines.*
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException


/**
 * 基础ViewModel类，管理LiveData
 * */
open class BaseViewModel : ViewModel() {

    //异常LiveData
    val errorLiveData = SingleLiveData<Throwable>()

    /**
     *onNext  处理数据;
     *onError 异常;
     */
    fun launch(
        onNext: suspend () -> Unit,
        onError: suspend () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                onNext()
            } catch (e: Exception) {
                onError()
            }
        }
    }

    /**
     *onNext  处理数据;
     *isShowError 是否显示异常toast;
     */
    fun launch(
        onNext: suspend () -> Unit,
        isShowError: Boolean,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                onNext()
            } catch (e: Exception) {
                if (isShowError) {
                    withContext(Dispatchers.Main) {
                        //切换到主线程
                        ToastUtil.show(getError(e))
                    }
                }
            }
        }
    }

    /**
     *onNext  处理数据;
     *onError 异常;
     *isShowError 是否显示异常toast;
     */
    fun launch(
        onNext: suspend () -> Unit,
        onError: suspend (e: Exception) -> Unit,
        isShowError: Boolean,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                onNext()
            } catch (e: Exception) {
                onError(e)
                if (isShowError) {
                    withContext(Dispatchers.Main) {
                        //切换到主线程
                        ToastUtil.show(getError(e))
                    }
                }

            }
        }
    }

    open fun getError(e: Exception): String {
        var reason = e.message
        //网络异常
        if (e is NetworkErrorException || !NetworkUtil.getActiveNetworkInfo(ObtainApplication.app)) {
            reason = "网络异常，请检查网络后重试"
            //账户异常
        } else if (e is AccountsException) {
            reason = "账户异常"
            //socket异常--继承于SocketException
        } else if (e is SocketException) {
            reason = "连接异常,请稍后重新连接"
            // http异常
//        } else if (e is HttpException) {
//            reason = e.message
        } else if (e is JsonSyntaxException
            || e is JsonIOException
            || e is JsonParseException
        ) {
            //数据格式化错误
            reason = "Json数据格式化错误"
        } else if (e is ClassCastException) {
            reason = "类型转换错误"
        } else if (e is TimeoutException || e is SocketTimeoutException) {
            reason = "请求超时,请稍后重试"
        }
        return reason.toString()
    }

}