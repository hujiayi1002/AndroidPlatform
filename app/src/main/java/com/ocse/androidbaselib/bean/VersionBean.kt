package com.ocse.androidbaselib.bean

class VersionBean {
    /**
     * status : true
     * message :
     * data : {"THR_APPVERSION_ID":105714,"VERSIONCODE":1,"VERSIONNAME":"1.0.0","LJ":"5f51e79a6f50be1ba4a4b5b4"}
     */
    var isStatus = false
    var message: String? = null
    var data: DataBean? = null

    class DataBean {
        /**
         * THR_APPVERSION_ID : 105714
         * VERSIONCODE : 1
         * VERSIONNAME : 1.0.0
         * LJ : 5f51e79a6f50be1ba4a4b5b4
         */
        var tHR_APPVERSION_ID = 0
        var vERSIONCODE = 0
        var vERSIONNAME: String? = null
        var lJ: String? = null

    }
}