package com.youngtr.kotlins.c3

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        setCharAt(length - 1, value)
    }