package com.ocse.androidbaselib.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ocse.androidbaselib.ui.ui.theme.AndroidBaseLibTheme
import com.ocse.baseandroid.utils.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CoroutinesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidBaseLibTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            ToastUtil.show("123")
                            runBlockingLaunch()
                            withContextAsync()
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    /**
     * runBlocking 会阻塞
     */
    private fun runBlockingLaunch() {
        runBlocking {
            delay(500)
            Log.e(
                "TAG",
                "runBlocking onCreate: ${Thread.currentThread().name} "
            )

        }
        Log.e("TAG", " runBlocking onCreate2: ${Thread.currentThread().name} ")

        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            Log.e(
                "TAG",
                "CoroutineScope onCreate: ${Thread.currentThread().name} "
            )
        }
        Log.e(
            "TAG",
            " CoroutineScope onCreate2: ${Thread.currentThread().name} "
        )
    }

    /**
     *   可返回结果的协程：withContext 与 async
     *   withContext串行
     *   async 并行 但是前提是前面没调用await
     */
    private fun withContextAsync() {
        CoroutineScope(Dispatchers.Main).launch {
            var time1 = System.currentTimeMillis()
            val task1 = withContext(Dispatchers.IO) {
                delay(2000)
                Log.e("TAG", "1.执行task1.... [当前线程为：${Thread.currentThread().name}]")
                "one"  //返回结果赋值给task1
            }
            val task2 = withContext(Dispatchers.IO) {
                delay(1000)
                Log.e("TAG", "2.执行task2.... [当前线程为：${Thread.currentThread().name}]")
                "two"  //返回结果赋值给task2
            }
            Log.e(
                "TAG",
                "task1 = $task1  , task2 = $task2 , 耗时 ${System.currentTimeMillis() - time1} ms  [当前线程为：${Thread.currentThread().name}]"
            )
            time1 = System.currentTimeMillis()
            val task3 = async(Dispatchers.IO) {
                delay(2000)
                Log.e("TAG", "1.执行task3.... [当前线程为：${Thread.currentThread().name}]")
                "one"  //返回结果赋值给task1
            }

            val task4 = async(Dispatchers.IO) {
                delay(1000)
                Log.e("TAG", "2.执行task4.... [当前线程为：${Thread.currentThread().name}]")
                "two"  //返回结果赋值给task2
            }

            Log.e(
                "TAG",
                "task3 = ${task3.await()}  , task4 = ${task4.await()} , 耗时 ${System.currentTimeMillis() - time1} ms  [当前线程为：${Thread.currentThread().name}]"
            )

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidBaseLibTheme {
        Greeting("Android")
    }


}