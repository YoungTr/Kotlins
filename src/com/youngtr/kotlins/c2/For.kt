package com.youngtr.kotlins.c2

import java.util.*

fun fizzBuzz(i: Int) =
        when {
            i % 15 == 0 -> "FizzBuzz"
            i % 3 == 0 -> "Fizz"
            i % 5 == 0 -> "Buzz"
            else -> "$i "
        }

val binaryReps = TreeMap<Char, String>()

fun formap() {
    for (c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.toInt())
        binaryReps[c] = binary
    }

    for ((letter, binary) in binaryReps) {
        println("$letter = $binary")
    }
}

fun forlist() {
    val list = arrayListOf("10", "11", "1001")
    for ((index, element) in list.withIndex()) {
        println("$index = $element")
    }
}

fun main() {

//    formap()

    forlist()
//    for (i in 1..100) {
//        println(fizzBuzz(i))
//    }
//
//    for (i in 100 downTo 1 step 2) {
//        println(fizzBuzz(i))
//    }
}