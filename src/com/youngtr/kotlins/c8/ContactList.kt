package com.youngtr.kotlins.c8

data class Person(val firstName: String,
                  val lastName: String,
                  val phoneNumber: String?)

class ContactListFilters {
    var prefix: String = ""
    var onlyWithPhoneNumber: Boolean = false

    fun getPredicate(): (Person) -> Boolean {
        val startsWithPrefix = { p: Person ->
            p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix)
        }
        if (!onlyWithPhoneNumber) {
            return startsWithPrefix
        }
        return {
            startsWithPrefix(it) && it.phoneNumber != null
        }
    }
}

fun main() {
    val contacts = listOf<Person>(Person("Dmitry", "Jemerov", "123-456"),
            Person("Svetlana", "Isakova", null))
    val contactListFilters = ContactListFilters()
    with(contactListFilters) {
        prefix = "Dm"
        onlyWithPhoneNumber = true
    }
    println(contacts.filter(contactListFilters.getPredicate()))
    // [Person(firstName=Dmitry, lastName=Jemerov, phoneNumber=123-456)]
}