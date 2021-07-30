package com.ocse.androidbaselib.retrofit

import android.util.Log
import com.ocse.androidbaselib.bean.UserBean
import com.ocse.androidbaselib.bean.VersionBean
import com.ocse.baseandroid.net.retrofit.BaseRetrofit
import com.ocse.baseandroid.utils.SharePreferenceUtil
import io.reactivex.Observable
import kotlin.collections.HashMap

class ApiRetrofit : BaseRetrofit() {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ApiRetrofit()
        }
    }

    private fun getApiService(): ApiService {
        val token = SharePreferenceUtil.getString("token")
            Log.e("hu--token", token)
        val params: HashMap<String, Any> = HashMap()
        if (token.isNullOrEmpty()) {
            params["csrf-csrf"] = "csrf-csrf"
            params["Content-Type"] = "application/x-www-form-urlencoded"

        } else {
            params["csrf-csrf"] = "csrf-csrf"
            params["Authorization"] = "Bearer  $token"
        }
        instance.addHeader(params)
        return instance.createService(ApiService::class.java)
    }

    fun login(
        user: String,
        password: String
    ) : Observable<UserBean> {
        val params: MutableMap<String, Any> = HashMap()
        params["username"] = user
        params["grant_type"] = "password"
        params["scope"] = "read"
        params["password"] = password
        params["client_id"] = "my-client"
        params["openid"] = "123456789"
       return switchSchedulers(getApiService().login(params))
    }
   suspend fun loginCn(
        user: String,
        password: String
    ) : UserBean {
        val params: MutableMap<String, Any> = HashMap()
        params["username"] = user
        params["grant_type"] = "password"
        params["scope"] = "read"
        params["password"] = password
        params["client_id"] = "my-client"
        params["openid"] = "123456789"
        return getApiService().loginCn(params)
    }
    fun getversion(
    ) : Observable<VersionBean> {
        val params: MutableMap<String, Any> = HashMap()
        return switchSchedulers(getApiService().getAppVersion(params))
    }
}