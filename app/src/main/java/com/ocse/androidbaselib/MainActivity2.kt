package com.ocse.androidbaselib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ocse.androidbaselib.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }

    private fun initView() {
    }

}