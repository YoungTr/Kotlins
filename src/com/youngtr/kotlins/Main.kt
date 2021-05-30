package com.youngtr.kotlins


fun main(args: Array<String>) {

    val name = if (args.size > 0) args[0] else "kotlin"

    println("Hello $name")

    val lest = listOf("1", "2", "3")
    println(lest.max())
    println(lest.last())
}