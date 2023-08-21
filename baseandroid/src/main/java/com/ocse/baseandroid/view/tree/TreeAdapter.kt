package com.ocse.baseandroid.view.tree

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.multilevel.treelist.Node
//import com.multilevel.treelist.TreeRecyclerAdapter
import com.ocse.baseandroid.R

/**
 * @author hujiayi
 */
open class TreeAdapter{}
//open class TreeAdapter : TreeRecyclerAdapter {
//    //如果只让子节点能点击 if (node.children.isNullOrEmpty()){
//    constructor(
//        mTree: RecyclerView,
//        context: Context,
//        dataList: List<Node<Any, Any>>,
//        defaultExpandLevel: Int
//    ) : super(mTree, context, dataList, defaultExpandLevel)
//
//    //直接调用
//    constructor(
//        mTree: RecyclerView,
//        context: Context,
//        dataList: List<Node<Any, Any>>,
//    ) : super(
//        mTree, context, dataList, 0, R.mipmap.tree_ex,
//        R.mipmap.tree_ec
//    ) {
//        mContext = context
//    }
//
//
//    override fun onBindViewHolder(
//        node: Node<Any, Any>,
//        holder: RecyclerView.ViewHolder,
//        position: Int
//    ) {
//        val viewHolder = holder as TreeAdapter.MyHolder
//        if (node.icon == -1) {
//            viewHolder.icon.visibility = View.INVISIBLE
//        } else {
//            viewHolder.icon.visibility = View.VISIBLE
//            viewHolder.icon.setImageResource(node.icon)
//        }
//        viewHolder.label.text = node.name
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
//        val view =
//            LayoutInflater.from(mContext).inflate(R.layout.list_item, viewGroup, false)
//        return MyHolder(view)
//    }
//
//    internal inner class MyHolder(itemView: View) :
//        RecyclerView.ViewHolder(itemView) {
//        var label: TextView = itemView
//            .findViewById(R.id.id_treenode_label)
//        var icon: ImageView = itemView.findViewById(R.id.icon)
//
//    }
//
//}