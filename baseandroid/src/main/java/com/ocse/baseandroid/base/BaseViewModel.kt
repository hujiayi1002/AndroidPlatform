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
import com.ocse.baseandroid.utils.Logger
import com.ocse.baseandroid.utils.NetworkUtil
import com.ocse.baseandroid.utils.ObtainApplication
import com.ocse.baseandroid.utils.ToastUtil
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException


/**
 * 基础ViewModel类，管理LiveData
 * */
open class BaseViewModel : ViewModel() {

    open val compositeDisposable by lazy { CompositeDisposable() }

    //异常LiveData
    val errorLiveData = SingleLiveData<Throwable>()

    @DelicateCoroutinesApi
    fun launch(
        onNext: suspend () -> Unit,
        onError: suspend (Throwable) -> MutableLiveData<*>,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                onNext()
            } catch (e: Exception) {
                onError(e).postValue(null)
                //切换到主线程
                GlobalScope.launch(Dispatchers.Main) {
                    ToastUtil.show(getError(e))
                }

            }
        }
    }

    private fun getError(e: Exception): String {
        var reason = e.message
        //网络异常
        if (e is NetworkErrorException || !NetworkUtil.isConnected(ObtainApplication.getApp())) {
            reason = "网络异常，请检查网络"
            //账户异常
        } else if (e is AccountsException) {
            reason = "账户异常"
            //连接异常
        } else if (e is ConnectException) {
            reason = "连接异常,请稍后重新连接"
            //socket异常--继承于SocketException
        } else if (e is SocketException) {
            reason = "socket异常"
            // http异常
        } else if (e is HttpException) {
            reason = e.message
        } else if (e is JsonSyntaxException
            || e is JsonIOException
            || e is JsonParseException
        ) {
            //数据格式化错误
            reason = "数据格式化错误"
        } else if (e is ClassCastException || e is IllegalStateException) {
            reason = "类型转换错误"
        }
        Logger.e(e.localizedMessage)
        return reason.toString()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}