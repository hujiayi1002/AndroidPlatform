package com.ocse.androidbaselib.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.navigation.Navigation
import com.ocse.androidbaselib.R
import com.ocse.androidbaselib.databinding.FragmentOtherBinding
import com.ocse.baseandroid.base.BaseFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OtherFragment : BaseFragment<FragmentOtherBinding>() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OtherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View) {
        viewBinding.tvOther
        view.findViewById<TextView>(R.id.tvOther).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_otherFragment_to_homeFragment)
        }
    }
}