package com.ocse.baseandroid.utils

import android.app.Application
import android.util.Log

/**
 * @author hujiayi
 */
//Application application = null;
//Class<?> activityThreadClass;
//try {
//    activityThreadClass = Class.forName("android.app.ActivityThread");
//    final Method method2 = activityThreadClass.getMethod(
//            "currentActivityThread");
//    // 得到当前的ActivityThread对象
//    Object localObject = method2.invoke(null, (Object[]) null);
//
//    final Method method = activityThreadClass
//            .getMethod("getApplication");
//    application = (Application) method.invoke(localObject, (Object[]) null);
//
//    return application;
//} catch (Exception e) {
//    e.printStackTrace();
//}
object ObtainApplication {

    val app: Application?
        get() {
            var application: Application? = null
            try {
                val atClass = Class.forName("android.app.ActivityThread")
                val currentApplicationMethod = atClass.getDeclaredMethod("currentApplication")
                currentApplicationMethod.isAccessible = true
                application = currentApplicationMethod.invoke(null) as Application
               //MyLog("currentApplication :$application")
            } catch (e: Exception) {
               MyLog("currentApplication Exception:$e")
            }
            if (application != null) return application
            try {
                val atClass = Class.forName("android.app.AppGlobals")
                val currentApplicationMethod = atClass.getDeclaredMethod("getInitialApplication")
                currentApplicationMethod.isAccessible = true
                application = currentApplicationMethod.invoke(null) as Application
               MyLog("currentApplication2:$application")
            } catch (e: Exception) {
               MyLog("currentApplication Exception:$e")
            }
            return application
        }
}