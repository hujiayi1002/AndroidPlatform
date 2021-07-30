package com.ocse.baseandroid.net.base

class ApiResponse<T>(
    var data: T?,
    var errorCode: Int,
    var errorMsg: String
)