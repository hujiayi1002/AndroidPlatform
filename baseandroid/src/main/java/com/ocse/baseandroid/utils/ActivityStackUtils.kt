package com.ocse.baseandroid.utils

import android.app.Activity
import com.ocse.baseandroid.base.BaseApplication

class ActivityStackUtils {

    companion object {
        private val mActivityStack = BaseApplication.activities
        private val instance by lazy { ActivityStackUtils() }

        /**
         *获取当前activity
         */
        fun getCurrent(): Activity? {
            return if (mActivityStack.isNotEmpty()) {
                //val activity: Activity = mActivityStack.lastElement()
                mActivityStack.lastElement()
            } else {
                null
            }
        }

        /**
         * 关闭所有activity
         */
        fun finishAll(): ActivityStackUtils {
            for (act in mActivityStack) {
                act.finish()
            }
            return instance
        }


        fun addActivity(mActivity: Activity): ActivityStackUtils {
            if (mActivityStack.isNotEmpty()) {
                mActivityStack.add(mActivity)
            }
            return instance
        }

        fun removeActivity(mActivity: Activity): ActivityStackUtils {
            if (mActivityStack.isNotEmpty()) {
                mActivityStack.remove(mActivity)
            }
            return instance
        }

        fun backTo(cls: Class<Any>): Activity? {
            for (activity in mActivityStack) {
                if (activity.javaClass == cls) {
                    return activity
                }
            }
            return null
        }

    }
}