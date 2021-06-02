# Kotlins

## 基础

### 自定义访问器

```kotlin
class Rectangle(private val height: Int, private val width: Int) {
    val isSquare: Boolean	// 声明属性的 getter
        get() {
            return height == width
        }
  ---->
    val isSquare: Boolean
  			get() = height == width
}
```

### when

when 是一个有返回值的表达式

```kotlin
enum class Color {
    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET
}

fun getMnemonic(color: Color) =
        when (color) {
            Color.RED -> "Richard"
            Color.ORANGE -> "Of"
            Color.YELLOW -> "York"
            Color.GREEN -> "Gave"
            Color.BLUE -> "Battle"
            Color.INDIGO -> "In"
            Color.VIOLET -> "Vain"
        }
```

也可以把多个值合并到同一个分支，只需要用逗号隔开这些值

```kotlin
fun getWarmth(color: Color) =
        when (color) {
            Color.RED, Color.ORANGE, Color.YELLOW -> "warn"
            Color.GREEN -> "neutral"
            Color.BLUE, Color.INDIGO, Color.VIOLET -> "cold"
        }
```

when 允许使用任何对象

```kotlin
fun mix(c1: Color, c2: Color) =
        when (setOf(c1, c2)) {
            setOf(Color.RED, Color.YELLOW) -> Color.ORANGE
            setOf(Color.YELLOW, Color.BLUE) -> Color.GREEN
            setOf(Color.BLUE, Color.VIOLET) -> Color.INDIGO
            else -> throw Exception("Dirty color")
        }
```

使用不带参数的 "when"

```kotlin
fun mixOptimized(c1: Color, c2: Color) =
        when {
            (c1 == Color.RED && c2 == Color.YELLOW)
                    || (c1 == Color.YELLOW && c2 == Color.RED) -> Color.ORANGE
            (c1 == Color.YELLOW && c2 == Color.BLUE)
                    || (c1 == Color.BLUE && c2 == Color.YELLOW) -> Color.GREEN
            (c1 == Color.BLUE && c2 == Color.VIOLET)
                    || (c1 == Color.VIOLET && c2 == Color.BLUE) -> Color.INDIGO
            else -> throw Exception("Dirty color")
        }
```

如果没有给 when 表达式提供参数，分支条件就是任意的布尔表达式。

### 迭代数字：区间和数列

for...in

```kotlin
fun fizzBuzz(i: Int) =
        when {
            i % 15 == 0 -> "FizzBuzz"
            i % 3 == 0 -> "Fizz"
            i % 5 == 0 -> "Buzz"
            else -> "$i "
        }

fun main() {
    for (i in 1..100) {
        println(fizzBuzz(i))
    }

    for (i in 100 downTo 1 step 2) {
        println(fizzBuzz(i))
    }
}
```

### 迭代 map

```kotlin
fun formap() {
    for (c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.toInt())
        binaryReps[c] = binary
    }

    for ((letter, binary) in binaryReps) {
        println("$letter = $binary")
    }
}
```

迭代集合使用下标

```kotlin
fun forlist() {
    val list = arrayListOf("10", "11", "1001")
    for ((index, element) in list.withIndex()) {
        println("$index = $element")
    }
}
```

### 使用 "in" 检查集合和区间的成员

```kotlin
fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDig(c: Char) = c !in '0'..'9'

fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "It's is a digit!"
    in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
    else -> "I don't know..."
}
```

### 扩展函数

```kotlin
fun <T> Collection<T>.joinToString(separator: String = ", ", prefix: String = "", postfix: String = ""): String {
    val result = StringBuffer(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }

    return result.toString()
}
```

### 扩展属性

```kotlin
var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        setCharAt(length - 1, value)
    }

val String.lastChar: Char
    get() = get(length - 1)
```

Java 中调用

```java
StringUtilKt.getLastChar("Kotlin?");
```

### 密封类

sealed 类，所有的嵌套子类必须嵌套在父类中。sealed 修饰符隐含的这个类是 open 类，不需要显示添加 open 修饰符。

```kotlin
sealed class Expr {
    class Num(val value: Int) : Expr()
    class Sum(val left: Expr, val right: Expr) : Expr()
}

fun eval(expr: Expr): Int =
				// “when” 表达式涵盖了所有的可能的情况，
				// 
        when (expr) {
            is Expr.Num -> expr.value
            is Expr.Sum -> eval(expr.right) + eval(expr.left)
        }
```

### 类委托：使用 “by” 关键字

可以使用 by 关键字将接口的实现委托到另一个对象

```kotlin
class CountingSet<T>(val innerSet: MutableCollection<T> = HashSet())
    : MutableCollection<T> by innerSet {	// 将 MutableCollection 的实现委托给 innerSet

    var objectsAdd = 0

    // 不使用委托，提供一个不同的实现
    override fun add(element: T): Boolean {
        objectsAdd++;
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectsAdd += elements.size
        return innerSet.addAll(elements)
    }
}
```

### “object” 关键字

**1、对象声明是定义单例的一种方式。**

```kotlin
object Payroll {
    val allEmployees = arrayListOf<Any>()

    fun calculateSalary() {
        for (person in allEmployees) {
            //...
        }
    }
}

Payroll.allEmployees.add("Tom")
Payroll.calculateSalary()
```

使用对象来实现 Comparator

```kotlin
object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(o1: File, o2: File): Int {
        return o1.path.compareTo(o2.path, ignoreCase = true)
    }
}

println(CaseInsensitiveFileComparator.compare(File("User"), File("user")))
```

同样可以在类中声明对象，这样的对象同样只有一个单一实例：他们在每个容器类的实例中并不具有相同的实例。

```kotlin
data class Person(val name: String) {
    object NameComparator : Comparator<Person> {
        override fun compare(o1: Person, o2: Person): Int {
            return o1.name.compareTo(o2.name)
        }
    }
}
```

```kotlin
data class Person(val name: String) {
    object NameComparator : Comparator<Person> {
        override fun compare(o1: Person, o2: Person): Int {
            return o1.name.compareTo(o2.name)
        }
    }
}
```

**2、伴生对象可以持有工厂方法和其他与这个类相关，但是在调用时并不依赖类实例的方法，它们的成员可以通过类名来访问。**

工厂方法：

```kotlin
class User private constructor(val nickname: String) {
    companion object {
        fun newSubscribingUser(email: String) =
                User(email.substringBefore("@"))

        fun newFacebookUser(accountId: Int) =
                User("id= $accountId")
    }
}
```

```kotlin
val subscribingUser = User.newSubscribingUser("bob@kotlin.org")
println(subscribingUser.nickname)
```

声明一个命名伴生对象

```kotlin
class Person(val name: String) {
    companion object Loader{
        fun fromJson(jsonText:String):Person = ...
    }
}
```

在伴生对象中实现接口

```kotlin
interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T
}
```

```kotlin
class Person(val name: String) {
    companion object : JSONFactory<Person> {
        override fun fromJSON(jsonText: String): Person = Person("Bob")
    }
}
```

```kotlin
fun <T> loadFromJSON(factory: JSONFactory<T>): T {
    return factory.fromJSON("")
}
```

```kotlin
loadFromJSON(Person) // 将伴生对象实例传入函数中，Person 类的名字被当做 JSONFactory 的实例
```

**3、对象表达式用来代替 Java 的匿名内部类**

```kotlin
    Thread(object : Runnable {
        override fun run() {
            
        }
    })
```

### "with" 函数

可以用它对同一个对象执行多次操作，而不需要反复把对象的名称写出来

```kotlin
fun alphabet(): String {
    val stringBuilder = StringBuilder()
    return with(stringBuilder) {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet!")
        toString() 	// 从 lambda 返回值
    }
}
```

with 返回的值是执行 lambda 代码的结果，该结果就是 lambda 中最后一个表达式（的值）。

### ”apply“ 函数

apply 始终会返回作为实参传递给它的对象。

```kotlin
fun alphabet() = StringBuilder().apply {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
}.toString()
```

### "let" 函数

可以把调用 let 函数的可空对象，转变为非空类型

```kotlin
fun sendEmail(email: String) {
    println(email)
}

fun main() {
    val email: String? = ""

  	// type mismatch
    sendEmail(email)

    email?.let {
        sendEmail(email)
    }
}
```

### 重载二元算术运算

`operator`

```kotlin
data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    override fun toString(): String {
        return "Point(x=$x, y=$y)"
    }

}
```

```kotlin
    val p1 = Point(10, 20)
    val p2 = Point(30, 40)
    println(p1 + p2)
```

还可以定义为扩展函数

```kotlin
operator fun Point.plus(other: Point): Point {
    return Point(x + other.x, y + other.y)
}
```

### 等号运算符：”equals“

```kotlin
···
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }
···
```
