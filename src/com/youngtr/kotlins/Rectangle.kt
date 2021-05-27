package com.youngtr.kotlins

class Rectangle(private val height: Int, private val width: Int) {
    val isSquare: Boolean
        get() {
            return height == width
        }

    val isS: Boolean
        get() = height == width
}