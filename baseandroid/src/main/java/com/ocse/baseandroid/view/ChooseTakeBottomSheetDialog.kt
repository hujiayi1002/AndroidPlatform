package com.ocse.baseandroid.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ocse.baseandroid.R

open class ChooseTakeBottomSheetDialog(context: Context) : BottomSheetDialogFragment() {
    private lateinit var choosePop: ChooseTake
    private lateinit var dataBind: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBind =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.pop_choose, null, false)
        val parms = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        return dataBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBind.root.findViewById<TextView>(R.id.tv_choose_take).setOnClickListener {
            choosePop.take()
        }
        dataBind.root.findViewById<TextView>(R.id.tv_choose_album).setOnClickListener {
            choosePop.album()
        }
        dataBind.root.findViewById<TextView>(R.id.tv_choose_cancle)
            .setOnClickListener { choosePop.dismiss() }
    }

    open fun setTakePop(pop: ChooseTake) {
        this.choosePop = pop
    }

    interface ChooseTake {
        fun take()
        fun album()
        fun dismiss()
    }
}