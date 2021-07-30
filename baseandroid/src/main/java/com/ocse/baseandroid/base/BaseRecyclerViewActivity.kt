package com.ocse.baseandroid.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ocse.baseandroid.R

class BaseRecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_recycler_view)
    }
}