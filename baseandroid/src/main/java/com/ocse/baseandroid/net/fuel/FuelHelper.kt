package com.ocse.huabei.powermatrix.fuel

import android.util.Log
//import com.github.kittinunf.fuel.core.FuelManager
//import com.github.kittinunf.fuel.core.Request
//import com.ocse.huabei.powermatrix.MyApp
//import com.ocse.huabei.powermatrix.constant.Constant
//import com.ocse.huabei.powermatrix.retrofit.utils.SharePerferenceUtil
//import java.util.HashMap

//https://github.com/kittinunf/fuel/blob/7b56b22799905ea3c4b7b26addd84dc978983247/fuel/src/main/kotlin/com/github/kittinunf/fuel/core/interceptors/LoggingInterceptors.kt


object FuelHelper {

    fun initFuel() {
        //服务器接口地址
//        FuelManager.instance.basePath = "https://mini.hnocse.com:8443/PingAnHuaBei/config.json"
//        //超时时间20秒
//        FuelManager.instance.timeoutInMillisecond = 20000
//        //添加header拦截器
//        FuelManager.instance.addRequestInterceptor(tokenInterceptor())
//        //添加请求日志拦截器
//        FuelManager.instance.addRequestInterceptor(loggingRequestInterceptor())
        //foldRight 是 List 的一个扩展函数 从右往左，对列表中的每一个元素执行 operation 操作，
        // 每个操作的结果是下一次操作的入参，第一次 operation 的初始值是 initial。
        //requestInterceptors.foldRight({r: Request -> r}){f,acc-> f(acc)}
    }


    /**
     * @Description :日志拦截器
     * @Return :
     * @Params :
     */
    private fun <T> loggingRequestInterceptor() =
        { next: (T) -> T ->
            { t: T ->
                next(t)
            }
        }


    /**
     * @Description :添加header
     * @Return :
     * @Params :
     */
    //private fun tokenInterceptor() = { next: (Request) -> Request ->
    //    { req: Request ->
    //        val token = SharePerferenceUtil.getString(Constant.token)
    //        val params = HashMap<String, Any>()
    //        if (token.isEmpty()) {
    //            params["csrf-csrf"] = "csrf-csrf"
    //            params["Content-Type"] = "application/x-www-form-urlencoded"
    //        } else {
    //            params["csrf-csrf"] = "csrf-csrf"
    //            params["Authorization"] = "Bearer  $token"
    //        }
    //
    //        req.header(params)//变量替换
    //        next(req)
    //    }
    //}
}