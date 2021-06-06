package com.youngtr.kotlins.c8

fun lookForAlice(people: List<Person>) {
    people.forEach lable@{
        if (it.lastName == "Alice") return@lable
    }
    println("Alice might be somewhere!")
}

fun lookForAlice2(people: List<Person>) {
    people.forEach {
        if (it.lastName == "Alice") return@forEach
    }
    println("Alice might be somewhere!")
}