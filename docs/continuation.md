# 协程

### 挂起函数

```kotlin
suspend fun foo(){} // suspend()->Unit
```

=>

```kotlin
fun foo(continuation:Continuation<Unit>):Any{}
```

![](./pics/1.png)

将回调转写成挂起函数

![](./pics/2.png)

