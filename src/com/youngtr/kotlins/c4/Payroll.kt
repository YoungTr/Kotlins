package com.youngtr.kotlins.c4

import java.io.File

object Payroll {
    val allEmployees = arrayListOf<Any>()

    fun calculateSalary() {
        for (person in allEmployees) {
            //...
        }
    }
}

fun test() {
    Payroll.allEmployees.add("Tom")
    Payroll.calculateSalary()

}

object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(o1: File, o2: File): Int {
        return o1.path.compareTo(o2.path, ignoreCase = true)
    }
}

fun main() {
    println(CaseInsensitiveFileComparator.compare(File("User"), File("user")))

    val persons = listOf(Person("Bob"), Person("Alice"))
    persons.sortedWith(Person.NameComparator)

    val subscribingUser = User.newSubscribingUser("bob@kotlin.org")
    println(subscribingUser.nickname)

    loadFromJSON(Person)

    Thread(object : Runnable {
        override fun run() {

        }
    })
}

data class Person(val name: String) {
    object NameComparator : Comparator<Person> {
        override fun compare(o1: Person, o2: Person): Int {
            return o1.name.compareTo(o2.name)
        }
    }

//    companion object Loader {
//        fun fromJson(jsonText: String): Person = Person("Bob")
//    }

    companion object : JSONFactory<Person> {
        override fun fromJSON(jsonText: String): Person = Person("Bob")
    }
}

fun <T> loadFromJSON(factory: JSONFactory<T>): T {
    return factory.fromJSON("")
}

class User private constructor(val nickname: String) {
    companion object {
        fun newSubscribingUser(email: String) =
                User(email.substringBefore("@"))

        fun newFacebookUser(accountId: Int) =
                User("id= $accountId")
    }
}

interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T
}