package com.youngtr.kotlins.c2

fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDig(c: Char) = c !in '0'..'9'

fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "It's is a digit!"
    in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
    else -> "I don't know..."
}