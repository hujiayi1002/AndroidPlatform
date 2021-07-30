package com.ocse.androidbaselib.retrofit

import com.ocse.androidbaselib.bean.UserBean
import com.ocse.androidbaselib.bean.VersionBean
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author hujiayi
 */
interface ApiService {
    /**
     * 登录
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("oauth/token")
    fun login(@FieldMap map: Map<String, @JvmSuppressWildcards Any>): Observable<UserBean>

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun loginCn(@FieldMap map: Map<String, @JvmSuppressWildcards Any>): UserBean

    /**
     * 登录
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/getAppVersion")
    fun getAppVersion(@FieldMap map: Map<String, @JvmSuppressWildcards Any>): Observable<VersionBean>
}