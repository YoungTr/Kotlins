package com.youngtr.kotlins.c3

fun String.lastChar(): Char = get(length - 1)

val String.lastChar: Char
    get() = get(length - 1)