/**
 * 章節 05: 函式與高階函式
 *
 * 學習目標：
 * 1. 掌握函式定義與呼叫
 * 2. 學習預設參數與具名參數
 * 3. 理解可變參數（varargs）
 * 4. 認識高階函式（函式作為參數或回傳值）
 * 5. 掌握匿名函式（Lambda）的使用
 * 6. 學習柯里化基礎
 * 7. 理解遞迴與尾部遞迴最佳化
 */

import scala.math.sqrt

@main def run(): Unit =
  println("=== 章節 05: 函式與高階函式 ===\n")

  example1()
  example2()
  example3()
  example4()
  example5()
  example6()
  example7()

  println("=== 章節完成 ===")

/**
 * 範例 1: 基本函式定義與呼叫
 *
 * 在 Scala 中，函式用 def 關鍵字定義。
 * 函式可以有參數、回傳型別，以及多行函式體。
 */
def example1(): Unit =
  println("--- 範例 1: 基本函式定義與呼叫 ---")

  // 定義一個簡單的函式
  def greet(name: String): String =
    s"你好，$name！"

  // 呼叫函式
  val message = greet("Alice")
  println(message)

  // 沒有參數的函式
  def getCurrentTime(): Long =
    System.currentTimeMillis()

  println(s"當前時間戳: ${getCurrentTime()}")

  // 多行函式（使用大括號）
  def calculateSum(a: Int, b: Int): Int =
    val sum = a + b
    println(s"  計算: $a + $b = $sum")
    sum

  val result = calculateSum(10, 20)
  println(s"結果: $result")

  println()

/**
 * 範例 2: 預設參數與具名參數
 *
 * 預設參數：函式參數可以有預設值
 * 具名參數：呼叫時可以按名字指定參數，順序可以不同
 */
def example2(): Unit =
  println("--- 範例 2: 預設參數與具名參數 ---")

  // 定義有預設參數的函式
  def createUser(name: String, age: Int = 18, city: String = "台北"): String =
    s"使用者: $name, 年齡: $age, 城市: $city"

  // 使用全部參數
  println(createUser("Alice", 25, "台北"))

  // 使用部分預設參數
  println(createUser("Bob", 30))

  // 使用全部預設參數
  println(createUser("Charlie"))

  // 使用具名參數（可以不按順序）
  println(createUser(city = "台中", name = "Diana", age = 22))
  println(createUser(name = "Eve", city = "台南"))

  println()

/**
 * 範例 3: 可變參數（varargs）
 *
 * 使用 paramName: Type* 來接受可變數量的參數。
 * 在函式內部，它被當作一個 Seq。
 */
def example3(): Unit =
  println("--- 範例 3: 可變參數 ---")

  // 定義接受可變參數的函式
  def sum(numbers: Int*): Int =
    var total = 0
    for n <- numbers do
      total += n
    total

  // 呼叫可變參數函式
  println(s"sum(1, 2, 3) = ${sum(1, 2, 3)}")
  println(s"sum(1, 2, 3, 4, 5) = ${sum(1, 2, 3, 4, 5)}")
  println(s"sum() = ${sum()}")  // 空參數

  // 可變參數函式也可以有其他參數
  def printWithPrefix(prefix: String, items: String*): Unit =
    println(s"$prefix: ${items.mkString(", ")}")

  printWithPrefix("顏色", "紅", "綠", "藍")
  printWithPrefix("數字", "一", "二", "三", "四", "五")

  println()

/**
 * 範例 4: 高階函式（將函式作為參數）
 *
 * 高階函式是接受函式作為參數或回傳函式的函式。
 * 這是函數式程式設計的核心概念。
 */
def example4(): Unit =
  println("--- 範例 4: 高階函式 ---")

  // 定義一個接受函式作為參數的函式
  def applyTwice(f: Int => Int, x: Int): Int =
    f(f(x))

  // 定義一些函式
  def double(x: Int): Int = x * 2
  def square(x: Int): Int = x * x

  // 傳遞函式作為參數
  println(s"applyTwice(double, 3) = ${applyTwice(double, 3)}")
  println(s"applyTwice(square, 3) = ${applyTwice(square, 3)}")

  // 定義一個回傳函式的函式
  def makeMultiplier(factor: Int): Int => Int =
    (x: Int) => x * factor

  val multiplyBy5 = makeMultiplier(5)
  println(s"multiplyBy5(7) = ${multiplyBy5(7)}")

  println()

/**
 * 範例 5: 匿名函式（Lambda）
 *
 * 匿名函式是沒有名字的函式，用於簡短的操作。
 * Scala 提供簡潔的 Lambda 語法。
 */
def example5(): Unit =
  println("--- 範例 5: 匿名函式（Lambda） ---")

  // 完整的匿名函式語法
  val add = (a: Int, b: Int) => a + b
  println(s"add(3, 4) = ${add(3, 4)}")

  // 簡化的匿名函式（使用下底線表示參數）
  val numbers = List(1, 2, 3, 4, 5)

  // 使用匿名函式進行映射
  val doubled = numbers.map((x: Int) => x * 2)
  println(s"numbers = $numbers")
  println(s"doubled = $doubled")

  // 進一步簡化：編譯器可以推斷型別
  val tripled = numbers.map(_ * 3)
  println(s"tripled = $tripled")

  // 使用匿名函式進行篩選
  val evens = numbers.filter(_ % 2 == 0)
  println(s"evens = $evens")

  println()

/**
 * 範例 6: 柯里化（Currying）
 *
 * 柯里化是將一個接受多個參數的函式轉換為一系列接受單一參數的函式。
 */
def example6(): Unit =
  println("--- 範例 6: 柯里化 ---")

  // 普通函式：接受兩個參數
  def multiply(a: Int, b: Int): Int = a * b

  // 柯里化版本：第一個函式接受 a，回傳一個接受 b 的函式
  def curriedMultiply(a: Int)(b: Int): Int = a * b

  // 使用柯里化函式
  println(s"multiply(3, 4) = ${multiply(3, 4)}")
  println(s"curriedMultiply(3)(4) = ${curriedMultiply(3)(4)}")

  // 柯里化的強大用處：可以部分應用
  val multiplyBy3 = curriedMultiply(3)
  println(s"multiplyBy3(4) = ${multiplyBy3(4)}")
  println(s"multiplyBy3(5) = ${multiplyBy3(5)}")

  println()

/**
 * 範例 7: 遞迴
 *
 * 遞迴是函式呼叫自己的技術。
 * 在 Scala 中要提供回傳型別才能正確識別遞迴函式。
 */
def example7(): Unit =
  println("--- 範例 7: 遞迴 ---")

  // 遞迴計算階乘
  def factorial(n: Int): Int =
    if n <= 1 then 1
    else n * factorial(n - 1)

  println(s"5! = ${factorial(5)}")
  println(s"10! = ${factorial(10)}")

  // 遞迴計算費波那契數列
  def fibonacci(n: Int): Int =
    if n <= 1 then n
    else fibonacci(n - 1) + fibonacci(n - 2)

  println(s"fibonacci(10) = ${fibonacci(10)}")

  // 遞迴計算列表長度
  def listLength(list: List[Int]): Int =
    if list.isEmpty then 0
    else 1 + listLength(list.tail)

  println(s"listLength(List(1, 2, 3, 4, 5)) = ${listLength(List(1, 2, 3, 4, 5))}")

  println()
