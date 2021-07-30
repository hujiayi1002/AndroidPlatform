package com.ocse.baseandroid.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import java.math.BigDecimal

class CNTextWatcher  {

    companion object {
        fun init(inputEdit: EditText, cnOutTextView: TextView) {
            inputEdit.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                    //删除“.”后面超过2位后的数据
                    var s = p0
                    if (s.toString().contains(".")) {
                        if (s.length - 1 - s.toString().indexOf(".") > 2) {
                            s = s.toString().subSequence(
                                0,
                                s.toString().indexOf(".") + 2 + 1
                            )
                            inputEdit.setText(s)
                            inputEdit.setSelection(s.length) //光标移到最后
                        }
                    }
                    //如果"."在起始位置,则起始位置自动补0
                    if (s.toString().trim().substring(0) == ".") {
                        s = "0$s"
                        inputEdit.setText(s)
                        inputEdit.setSelection(2)
                    }

                    //如果起始位置为0,且第二位跟的不是".",则无法后续输入
                    if (s.toString().startsWith("0")
                        && s.toString().trim().length > 1
                    ) {
                        if (s.toString().substring(1, 2) != ".") {
                            inputEdit.setText(s.subSequence(0, 1))
                            inputEdit.setSelection(1)
                            return
                        }
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (inputEdit.text.toString().isNotEmpty()) {
                        var num = inputEdit.text.toString().toDouble()
                        var money = BigDecimal(num)
                        cnOutTextView.text = NumberToCN.toCN(money)
                    } else {
                        cnOutTextView.text = ""
                    }


                }

            })
        }

        fun init(inputEdit: EditText, cnOutTextView: TextView, editCall: EditCallBack) {
            inputEdit.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                    //删除“.”后面超过2位后的数据
                    var s = p0
                    if (s.toString().contains(".")) {
                        if (s.length - 1 - s.toString().indexOf(".") > 2) {
                            s = s.toString().subSequence(
                                0,
                                s.toString().indexOf(".") + 2 + 1
                            )
                            inputEdit.setText(s)
                            inputEdit.setSelection(s.length) //光标移到最后
                        }
                    }
                    //如果"."在起始位置,则起始位置自动补0
                    if (s.toString().trim().substring(0) == ".") {
                        s = "0$s"
                        inputEdit.setText(s)
                        inputEdit.setSelection(2)
                    }

                    //如果起始位置为0,且第二位跟的不是".",则无法后续输入
                    if (s.toString().startsWith("0")
                        && s.toString().trim().length > 1
                    ) {
                        if (s.toString().substring(1, 2) != ".") {
                            inputEdit.setText(s.subSequence(0, 1))
                            inputEdit.setSelection(1)
                            return
                        }
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (inputEdit.text.toString().isNotEmpty()) {
                        var num = inputEdit.text.toString().toDouble()
                        var money = BigDecimal(num)
                        cnOutTextView.text = NumberToCN.toCN(money)
                    } else {
                        cnOutTextView.text = ""
                    }
                    editCall.editCall()
                }

            })
        }
    }

    interface EditCallBack {
        fun editCall()
    }

}
