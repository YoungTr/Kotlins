package com.youngtr.kotlins.c3

fun <T> Collection<T>.joinToString(separator: String = ", ", prefix: String = "", postfix: String = ""): String {
    val result = StringBuffer(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }

    return result.toString()
}