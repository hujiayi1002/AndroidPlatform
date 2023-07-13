package com.ocse.baseandroid.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType


abstract class BaseVMActivity<V : ViewBinding, VM:ViewModel>() :
    BaseActivity<V>() {
    private lateinit var viewModelProvider: ViewModelProvider
    lateinit var viewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModelProvider = ViewModelProvider(this)
        val argument = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        viewModel = ViewModelProvider(this)[argument[1] as Class<VM>]
        super.onCreate(savedInstanceState)
    }
}