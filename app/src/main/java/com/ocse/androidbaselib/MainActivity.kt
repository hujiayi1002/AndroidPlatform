package com.ocse.androidbaselib

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.ocse.androidbaselib.databinding.ActivityMainBinding
import com.ocse.androidbaselib.model.BaseModel
import com.ocse.baseandroid.base.BaseActivity
import com.ocse.baseandroid.utils.DensityUtil
import com.ocse.baseandroid.utils.GlideEngine
import com.ocse.baseandroid.utils.ToastUtil


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var vm: BaseModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isNeedDoubleExit = true
        vm = get(BaseModel::class.java)
        vm.userMutableLiveData.observe(this, Observer {
            Log.e("TAG", "onCreate: ,${it == null} ")
            it?.let {
                dataBinding.user = it
            }
        })
        vm.ss.observe(this, Observer {

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
        DensityUtil.screenWidth
        GlideEngine.instance.loadPhotoCircle(
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2F1812.img.pp.sohu.com.cn%2Fimages%2Fblog%2F2009%2F11%2F18%2F18%2F8%2F125b6560a6ag214.jpg&refer=http%3A%2F%2F1812.img.pp.sohu.com.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1621760668&t=4a7becb01298bd57260b518901c65f32",
            0, dataBinding.imageView)
        val sd = ArrayList<String>()
        repeat(sd.size)
        {}
        dataBinding.textView.setOnClickListener {
//            val loadingView = LoadingView.Builder(mContext)
//            loadingView.setMessage("123")
//            val dialog = loadingView.create()
//            dialog.show()
            vm.user()
            vm.ss()
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

        setMainTextView("123")
        dataBinding. button.setOnClickListener {
            ToastUtil.show("1234")
            val intent = Intent(this, MainActivity2::class.java)
            // create the transition animation - the images in the layouts
            // of both activities are defined with android:transitionName="robot"
            val options = ActivityOptions
                .makeSceneTransitionAnimation(this, dataBinding.img, "robot")
            // start the new activity
            startActivity(intent, options.toBundle())
//            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            //Intent intent=new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
            //Intent intent=new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
//            startActivityForResult(intent, 0)
        }

    }

    fun haspre(x: Any) = when (x) {
        is String -> {
           x.subSequence(0,10)
        }
        else -> false

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
//        Logger.e("zpf" + results?.get(0).toString())
//        textView.text = results?.get(0).toString()
    }

    // 2.更改数据
    private fun getUser() {
//        val user = UserBean();
        //同步更改setValue  ;  异步更改postValue
//        get(BaseModel::class.java).userMutableLiveData.postValue(user)
    }

    override fun initView() {

    }

    override fun initData() {

    }
}