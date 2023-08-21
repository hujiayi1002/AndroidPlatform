package com.ocse.baseandroid.utils

import com.google.gson.Gson
//import com.multilevel.treelist.Node
import com.ocse.baseandroid.view.tree.NodeBean
/**
 * 树状数据结构相互转换
 */
class TreeDataTransformUtils {
    companion object {
        /**
         * 平级转树
         * child:[{ id:0, name:"1",chilld:[] }]    转成->     {id:1,pid:0, name:"1"}
         */
        private val nodeList = ArrayList<NodeBean>()
        fun data2Tree(data: ArrayList<Nodes>): ArrayList<NodeBean> {
            sameLevelTree(data)
            return nodeList
        }

        private fun sameLevelTree(data: ArrayList<Nodes>) {
            for ((i, value) in data.withIndex()) {
                nodeList.add(NodeBean("", "", ""))
                if (!data[i].children.isNullOrEmpty()) {
                    this.sameLevelTree(data[i].children)
                }
            }
        }

        /**
         * 树转平级
         * {id:1,pid:0, name:"1"} ->child:{ id:0, name:"1",chilld:[] }
         */

        fun tree2Data(data:String): ArrayList<bean.dataClass>{
            val list = Gson().fromJson(data, bean::class.java).data
            val map = HashMap<Any, Any>()
            val tree = ArrayList<bean.dataClass>()
            list.forEach {
                it.children = ArrayList<bean.dataClass>()
                map["${it.id}"] = it
            }
            list.forEach {
                map["${it.pId}"]?.run {
                    (this as bean.dataClass).children.add(it)
                }?: run {
                    tree.add(it)
                }
            }
            return tree
        }
    }
    data class bean(val data: ArrayList<dataClass>) {
        data class dataClass(
            val id: Int,
            val pId: Int,
            val name: String,
            var children: ArrayList<dataClass>,
        )
    }
    data class Nodes(val id: String, val pid: String,val  name: String ,val children: ArrayList<Nodes>)

}