package com.youngtr.kotlins.c8

fun <T> Collection<T>.joinToString(separator: String = ", ",
                                   prefix: String = "",
                                   postfix: String = "",
                                   transform: (T) -> String = { it.toString() }): String { // 声明一个以 lambda 为默认值的函数类型的参数
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(transform(element))   // 调用作为实参传递给 "transform" 形参的函数
    }
    result.append(postfix)
    return result.toString()

}

fun <T> Collection<T>.joinToString2(separator: String = ", ",
                                    prefix: String = "",
                                    postfix: String = "",
                                    transform: ((T) -> String)? = null): String { // 声明一个函数类型的可空参数
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        val str = transform?.invoke(element)    // 调用 invoke 方法
                ?: element.toString()   // 使用 Elvis 运算符处理回调没有被指定的情况
        result.append(str)
    }
    result.append(postfix)
    return result.toString()

}

fun main() {
    val letters = listOf("Alpha", "Beta")
    println(letters.joinToString()) // 使用默认的转换函数
    // Alpha, Beta
    println(letters.joinToString { it.toLowerCase() }) // 传递一个 lambda 作为参数
    // alpha, beta
    println(letters.joinToString(separator = "! ", postfix = "! ") {
        it.toUpperCase() // 使用命名参数语法传递几个参数，包括一个 lambda
    })
    //
}