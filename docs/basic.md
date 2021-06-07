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

### 定义一个简单高阶函数

```kotlin
fun twoAndThree(operate: (Int, Int) -> Int) {
    val result = operate(2, 3)
    println("The result is $result")
}

fun main() {
    twoAndThree { a, b -> a + b }
    twoAndThree { a, b -> a * b }
}
```

### 简单的 “filter” 函数

```kotlin
fun String.filter(predicate: (Char) -> Boolean): String {
    val sb = StringBuilder()
    for (index in 0 until length) {
        val element = get(index)
        if (predicate(element)) {
            sb.append(element)
        }
    }
    return sb.toString()
}

fun main() {
    println("ab1c".filter { it in 'a'..'z' })
}
```

### 在 java 中使用函数类

函数类型被声明为普通接口：一个函数类型的变量是 FunctionN 接口的一个现实。

Java 8 的 lambda 会被自动转换为函数类型的值

```kotlin
UnitKt.twoAndThree((a, b) -> a + b);
```

在旧版的 Java 中，可以传递一个实现了函数接口中的 invoke 方法的匿名类的实例

```kotlin
    UnitKt.twoAndThree(
        new Function2<Integer, Integer, Integer>() {
          @Override
          public Integer invoke(Integer a, Integer b) {
            return a * b;
          }
        });
```

### 给函数类型的参数指定默认值

```kotlin
fun <T> Collection<T>.joinToString(separator: String = ", ",
                                   prefix: String = "",
                                   postfix: String = "",
                                   transform: (T) -> String = { it.toString() }): String { // 声明一个以 lambda 为默认值的函数类型的参数
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(transform(element))   // 调用作为实参传递给 "transform" 形参的函数
    }
    result.append(postfix)
    return result.toString()

}

fun main() {
    val letters = listOf("Alpha", "Beta")
    println(letters.joinToString()) // 使用默认的转换函数
    // Alpha, Beta
    println(letters.joinToString { it.toLowerCase() }) // 传递一个 lambda 作为参数
    // alpha, beta
    println(letters.joinToString(separator = "! ", postfix = "! ") {
        it.toUpperCase() // 使用命名参数语法传递几个参数，包括一个 lambda
    })
    //
}
```

### 使用函数类型的可空参数

```kotlin
fun <T> Collection<T>.joinToString(separator: String = ", ",
                                    prefix: String = "",
                                    postfix: String = "",
                                    transform: ((T) -> String)? = null): String { // 声明一个函数类型的可空参数
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        val str = transform?.invoke(element)    // 调用 invoke 方法
                ?: element.toString()   // 使用 Elvis 运算符处理回调没有被指定的情况
        result.append(str)
    }
    result.append(postfix)
    return result.toString()

}
```

### 返回函数的函数

```kotlin
enum class Delivery {
    STANDARD, EXPEDITED
}

class Order(val itemCount: Int)

fun getShippingCostCalculator(delivery: Delivery): (Order) -> Double {	// 声明一个返回函数的函数
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
```

声明一个返回另一个函数的函数，需要指定一个函数类型作为返回类型。要返回一个函数，需要写一个 return 表达式，跟上一个 lambda、一个成员引用，或者其他的函数类型的表达式，比如一个（函数类型的）局部变量。

```kotlin
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
            return startsWithPrefix		// 返回一个函数类型的变量
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
   // 将 getPredicate 返回的函数作为参数传递给 "filter" 函数
   println(contacts.filter(contactListFilters.getPredicate()))
   // [Person(firstName=Dmitry, lastName=Jemerov, phoneNumber=123-456)]
}
```

### 通过 lambda 去除重复代码

```kotlin
enum class OS {
    WINDOWS, LINUX, MAC, IOS, ANDROID
}

fun main() {
    val log = listOf(
            SiteVisit("/", 34.0, OS.WINDOWS),
            SiteVisit("/", 22.0, OS.MAC),
            SiteVisit("/login", 12.0, OS.WINDOWS),
            SiteVisit("/signup", 8.0, OS.IOS),
            SiteVisit("/", 16.3, OS.ANDROID)
    )
    val averageWindowsDuration = log
            .filter { it.os == OS.WINDOWS }
            .map { it.duration }
            .average()
    println(averageWindowsDuration)
    // 23.0
}
```

使用普通方法去除重复代码，可以将平台类型抽象为一个参数

```kotlin
fun List<SiteVisit>.averageDuration(os: OS) =
        filter { it.os == os }.map(SiteVisit::duration).average()


println(log.averageDurationFor(OS.WINDOWS))
// 23.0
println(log.averageDurationFor(OS.MAC))
// 22.0
```

但是与移动平台(iOS 和 Android)的访问，无法用一个简单的参数表示不同的平台了

可以用函数类型将需要的条件抽取到一个参数中

```kotlin
fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
        filter(predicate).map(SiteVisit::duration).average()


println(log.averageDurationFor { it.os in setOf(OS.ANDROID, OS.IOS) })
// 12.15
println(log.averageDurationFor { it.os == OS.IOS && it.path == "/signup" })
// 8.0
```

### 内联函数：消除 lambda 带来的运行时开销

使用 `inline` 修饰符标记一个函数，在函数被使用的时候编译器并不会生成函数调用的代码，而是使用函数实现的真实代码替换每一次的函数调用。

```kotlin
inline fun <T> synchronized(lock: Lock, action: () -> T): T {
  lock.lock()
  try {
    return action()
  } finally {
    lock.unlock()
  }
}
```

```kotlin
fun foo(l: Lock) {
  println("Before sync")
  synchronized(l) {
    println("Action")
  }
  println("After sync")
}
```

将会编译成同样的字节码

```kotlin
fun __foo__(l: Lock) {
  println("Before sync")	// 调用者 foo 的函数代码
  lock.lock()							// 被内联的 synchronized 函数代码
  try {
    println("Action")			// 被内联的 lambda 体代码
  } finally {
    lock.unlock()
  }
  println("After sync")
}
```

lambda 表达式和 synchronized 函数的实现都被内联了。

在调用内联函数的时候也可以传递函数类型的变量作为参数：

```kotlin
class LockOwner(val lock: Lock) {
  fun runUnderLock(body: () -> Unit) {
    synchronized(lock, body)	// 传递一个函数类型的变量作为参数，而不是一个 lambda
  }
}
```

在这种情况下，lambda 的代码在内联函数被调用点是不可用的，因此并不会被内联。

```kotlin
class LockOwner(val lock: Lock) {
  fun __runUnderLock__(body: () -> Unit) {
    lock.lock()
    try {
      body()	// body 没有被内联，因为在调用的地方还没有 lambda
    } finally {
      lock.unlock()
    }
  }
}
```

### 从 lambda 返回：使用标签返回

lambda 中的局部返回跟 for 循环中的 break 表达式相似，他会终止 lambda 的执行，并接着从调用 lambda 的代码处执行。

```kotlin
fun lookForAlice(people: List<Person>) {
    people.forEach lable@{
        if (it.lastName == "Alice") return@lable	// return@label 引用了这个标签
    }
    println("Alice might be somewhere!")	// 这一行总是被打印出来
}
```

```kotlin
fun lookForAlice(people: List<Person>) {
    people.forEach {
        if (it.lastName == "Alice") return@forEach	// returen@forEach 从 lambda 表达式返回
    }
    println("Alice might be somewhere!")
}
```

### 匿名函数：默认使用局部返回

```kotlin
people.filter(fun (person): Boolean {
  return person.age < 30
})
```

在匿名函数中，不带标签的 return 表达式会从匿名函数返回，而不是从包含匿名函数的函数返回。

`return` 从最近的使用 fun 关键字声明的函数返回。lambda 表达式没有使用 fun 关键字，所有 lambda 中的 `return` 从最外层的函数返回。匿名函数使用了 fun，`return` 表达式从匿名函数返回，而不是从最外层的函数返回。

### 泛型函数和属性

```kotlin
fun <T> List<T>.slice(indicaes: IntRange): List<T>
```

























