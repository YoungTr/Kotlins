package com.youngtr.kotlins.c5

fun sendEmail(email: String) {
    println(email)
}

fun main() {
    val email: String? = ""

//    sendEmail(email)

    email?.let {
        sendEmail(email)
    }
}
