package com.ocse.baseandroid.utils;

import android.app.Application;

import java.lang.reflect.Method;

/**
 * @author hujiayi
 */
public class ObtainApplication {

    public static Application getApp() {
        Application application = null;
        Class<?> activityThreadClass;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            final Method method2 = activityThreadClass.getMethod(
                    "currentActivityThread");
            // 得到当前的ActivityThread对象
            Object localObject = method2.invoke(null, (Object[]) null);

            final Method method = activityThreadClass
                    .getMethod("getApplication");
            application = (Application) method.invoke(localObject, (Object[]) null);

            return application;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
