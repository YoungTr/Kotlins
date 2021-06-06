package com.youngtr.kotlins.c8

enum class Delivery {
    STANDARD, EXPEDITED
}

class Order(val itemCount: Int)

fun getShippingCostCalculator(delivery: Delivery): (Order) -> Double {
    if (delivery == Delivery.EXPEDITED) {
        return { order: Order -> 6 + 2.1 * order.itemCount }
    }
    return { order: Order -> 1.2 * order.itemCount }
}

fun main() {
    val calculator = getShippingCostCalculator(Delivery.EXPEDITED)
    println("Shipping costs ${calculator(Order(3))}")
    // Shipping costs 12.3
}