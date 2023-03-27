package com.ocse.baseandroid.net.base

import android.accounts.AccountsException
import android.accounts.NetworkErrorException
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.ocse.baseandroid.utils.MyLog
import com.ocse.baseandroid.utils.NetworkUtil
import com.ocse.baseandroid.utils.ObtainApplication
import com.ocse.baseandroid.utils.ToastUtil.Companion.show
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException

/**
 * @author
 */
abstract class BaseObserver<T>(compositeDisposable: CompositeDisposable) :
    DisposableObserver<T>() {

    init {
        compositeDisposable.add(this)
    }

    override fun onNext(t: T) {
        _onNext(t)
        MyLog.e( "onNext = " + Gson().toJson(t))
    }

    override fun onError(e: Throwable) {
        var reason = e.message
        //网络异常
        if (!NetworkUtil.isConnected(ObtainApplication.app)) {
            reason = "暂无网络，请检查网络"
        } else if (e is NetworkErrorException) {
            reason = "网络异常，请检查网络"
            //账户异常
        } else if (e is AccountsException) {
            reason = "账户异常"
            //连接异常--继承于SocketException
        } else if (e is ConnectException) {
            reason = "连接异常"
            //socket异常
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
        MyLog.e(e.localizedMessage)
        _onError(e)
        show(reason)
    }

    override fun onComplete() {
//        loading.dismiss()
    }

    /**
     * 获取成功后数据展示
     *
     * @param entity
     * @return
     */
    abstract fun _onNext(entity: T)
    fun _onError(e: Throwable) {}


}