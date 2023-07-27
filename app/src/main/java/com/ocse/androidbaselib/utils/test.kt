package com.ocse.androidbaselib.utils

class test {

}
//class MySet<T> (private val myHashSet: HashSet<T>):Set<T>{
//    override val size: Int
//        get() =myHashSet.size
//
//    override fun isEmpty(): Boolean {
//      return  myHashSet.isEmpty()
//    }
//
//    override fun iterator(): Iterator<T> {
//        return  myHashSet.iterator()
//    }
//
//    override fun containsAll(elements: Collection<T>): Boolean {
//        return  myHashSet.containsAll(elements)
//    }
//
//    override fun contains(element: T): Boolean {
//        return  myHashSet.contains(element)
//    }
//
//}
class MySet<T>(private val helperSet: HashSet<T>) : Set<T> by helperSet {

    fun helloWorld() = println("Hello World")

    override fun isEmpty() = false
}


