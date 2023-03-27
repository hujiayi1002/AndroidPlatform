package com.ocse.androidbaselib.model

import androidx.lifecycle.MutableLiveData
import com.ocse.androidbaselib.bean.UserBean
import com.ocse.androidbaselib.retrofit.ApiRetrofit.Companion.instance
import com.ocse.baseandroid.base.BaseViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

/**
 * @author hujiayi
 */
class BaseModel : BaseViewModel() {
    val userMutableLiveData by lazy { MutableLiveData<UserBean>() }
    val loginCn by lazy { MutableLiveData<UserBean>() }


    fun user() {
//       instance.login("admin", "123456")
//            .subscribe(object : BaseObserver<UserBean>(compositeDisposable) {
//                override fun onError(e: Throwable) {
//                    super.onError(e)
//                    userMutableLiveData.postValue(null)
//                }
//
//                override fun _onNext(entity: UserBean) {
//                    userMutableLiveData.postValue(entity)
//                }
//                    override fun _onNext(entity: UserBean) {
//                        userMutableLiveData.postValue(entity)
//                        saveString("token", entity.access_token)
//                        //getVersion();
//                    }
//            })
    }

    fun loginCn() {
        launch({
            val data=instance.loginCn("admin", "123456")
            userMutableLiveData.postValue(data)
        }, {
            userMutableLiveData
        })
    }
}