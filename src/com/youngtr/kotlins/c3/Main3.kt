package com.youngtr.kotlins.c3

fun main() {
    println("Kotlin!".lastChar)

    val sb = StringBuilder("Kotlin?")
    println(sb.lastChar)
    sb.lastChar = '!'
    println(sb.lastChar)
}