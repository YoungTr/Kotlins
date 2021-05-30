package com.youngtr.kotlins.c3

fun main() {
    println("Kotlin!".lastChar)

    val sb = StringBuilder("Kotlin?")
    println(sb.lastChar)
    sb.lastChar = '!'
    println(sb.lastChar)

    val filePath = "/User/yole/kotlin-book/chapter.doc"

    parsePath(filePath)
}

fun parsePath(path: String) {
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")

    println("fullName: $fullName")

    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")

    println("Dir: $directory, name: $fileName, ext $extension")
}
