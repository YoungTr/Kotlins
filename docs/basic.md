### 1、自定义访问器

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

### 2、when

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

















