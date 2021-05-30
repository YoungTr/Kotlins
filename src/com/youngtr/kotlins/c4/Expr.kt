package com.youngtr.kotlins.c4

sealed class Expr {
    class Num(val value: Int) : Expr()
    class Sum(val left: Expr, val right: Expr) : Expr()
}

fun eval(expr: Expr): Int =
        when (expr) {
            is Expr.Num -> expr.value
            is Expr.Sum -> eval(expr.right) + eval(expr.left)
        }