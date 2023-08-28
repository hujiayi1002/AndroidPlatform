package com.ocse.androidbaselib

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.lifecycle.Observer
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ocse.androidbaselib.databinding.ActivityMainBinding
import com.ocse.androidbaselib.databinding.LayoutShowSendBinding
import com.ocse.androidbaselib.model.BaseModel
import com.ocse.androidbaselib.utils.MySet
import com.ocse.androidbaselib.utils.MyWorker
import com.ocse.baseandroid.base.BaseVMActivity
import com.ocse.baseandroid.utils.*
import com.ocse.baseandroid.view.LoadingView
import com.ocse.baseandroid.view.ui.ShowAboveInputPopWindow
import com.ocse.baseandroid.view.ui.ShowVideoActivity
import java.util.concurrent.TimeUnit


class MainActivity : BaseVMActivity<ActivityMainBinding, BaseModel>() {
    override fun setTitleText(): String? {
        return "消息"
    }

    override fun initView() {

        viewModel.userMutableLiveData.observe(this, Observer {
            Log.e("TAG", "onCreate: ,${it == null} ")
            it?.let {
                dataBinding.user = it

            }
        })
        viewModel.loginCn.observe(this, Observer {
            it?.let {
                dataBinding.user = it
            }
        })
        dataBinding.text2.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                ToastUtil.show("开始搜索")
            }
            false
        }
        var a = 1
        val result = with(a) {
            a = 2
            a + 3
        }
        Log.e("TAG", "initView: $result ")//5
        var b = ""
        val res2 = b.run {
            b = "222"
            "哈哈"
        } ?: {
            b = "333"
            "666"
        }
        Log.e("TAG", "initView: $res2 ")//5

        Log.e("TAG", "screenWidth: ${DensityUtil.screenWidth} ")
        GlideEngine.instance.loadPhotoCircle(
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2F1812.img.pp.sohu.com.cn%2Fimages%2Fblog%2F2009%2F11%2F18%2F18%2F8%2F125b6560a6ag214.jpg&refer=http%3A%2F%2F1812.img.pp.sohu.com.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1621760668&t=4a7becb01298bd57260b518901c65f32",
            0, dataBinding.imageView
        )
        val sd = ArrayList<String>()
        repeat(sd.size)
        {}

        //DataStoreUtils.setString("hu","hujiayi")
        viewModel.loginCn()
        dataBinding.textView.setOnClickListener {
            LoadingView.Builder(this).setCanceledOnTouchOutside(true).create().show()
ShowVideoActivity.start(this,"123","https://media.w3.org/2010/05/sintel/trailer.mp4")

//            val loadingView = LoadingView.Builder(mContext)
//            loadingView.setMessage("123")
//            val dialog = loadingView.create()
//            dialog.show()
//            viewModel.user()
//            viewModel.loginCn()
//            val bottomSheetDialog = ChooseTakeBottomSheetDialog(this@MainActivity)
//            bottomSheetDialog.show(supportFragmentManager,"")
//            bottomSheetDialog.setTakePop(object :ChooseTakeBottomSheetDialog.ChooseTake{
//                override fun take() {
//                    ToastUtil.show("123")
//                }
//
//                override fun album() {
//                    ToastUtil.show("album")
//
//                }
//
//                override fun dismiss() {
//                    ToastUtil.show("dismiss")
//                    bottomSheetDialog.dismiss()
//                }
//
//            })


        }

        dataBinding.button.setOnClickListener {
            ToastUtil.show("1234")

            Log.e("TAG", "getString: ${DataStoreUtils.getString("hu")}")
            val intent = Intent(this, MainActivity2::class.java)
            // create the transition animation - the images in the layouts
            // of both activities are defined with android:transitionName="robot"
            val options = ActivityOptions
                .makeSceneTransitionAnimation(this, dataBinding.imageView, "robot")
            // start the new activity
            //startActivity(intent, options.toBundle())
//            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            //Intent intent=new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
//            startActivityForResult(intent, 0)
        }

    }

    override fun onBackPressed() {
        onDoubleBackPressed()
    }

    fun haspre(x: Any) = when (x) {
        is String -> {
            x.subSequence(0, 10)
        }
        else -> false

    }


    // 2.更改数据
    private fun getUser() {
//        val user = UserBean();
        //同步更改setValue  ;  异步更改postValue
//        get(BaseModel::class.java).userMutableLiveData.postValue(user)
    }

    override fun initData() {
        val hashMap = HashSet<String>()
        val mySet = MySet(hashMap)
        mySet.helloWorld()
        val keyBordStateUtil = KeyBordStateUtil(this)
        keyBordStateUtil.addOnKeyBordStateListener(object :
            KeyBordStateUtil.OnKeyBordStateListener {
            override fun onSoftKeyBoardShow(keyboardHeight: Int) {
            }

            override fun onSoftKeyBoardHide() {
            }

        })
        dataBinding.button2.setOnClickListener {
            KeyBordStateUtil.hideKeyBord()
        }
    }

    /**
     * 学习Worker
     */
    private fun workerLearning() {
        //val oneWork = OneTimeWorkRequest.from(MyIntentSer::class.java)//一次
        val work = PeriodicWorkRequestBuilder<MyWorker>(1, TimeUnit.SECONDS).build()
        WorkManager.getInstance(this).enqueue(work)
    }


}