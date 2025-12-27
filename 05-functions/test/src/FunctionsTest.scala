/**
 * 測試檔案：Functions.scala
 *
 * 展示如何為 Scala 程式編寫簡單的測試。
 * 使用 munit 測試框架。
 */

class FunctionsTest extends munit.FunSuite:

  // 定義被測試的函式
  def add(a: Int, b: Int): Int = a + b

  def multiply(a: Int, b: Int): Int = a * b

  def isEven(n: Int): Boolean = n % 2 == 0

  def factorial(n: Int): Int =
    if n <= 1 then 1
    else n * factorial(n - 1)

  // 測試用例 1: 簡單的加法
  test("add should correctly add two numbers"):
    assertEquals(add(2, 3), 5)
    assertEquals(add(0, 0), 0)
    assertEquals(add(-1, 1), 0)

  // 測試用例 2: 乘法
  test("multiply should correctly multiply two numbers"):
    assertEquals(multiply(3, 4), 12)
    assertEquals(multiply(0, 100), 0)
    assertEquals(multiply(-2, 5), -10)

  // 測試用例 3: 偶數檢查
  test("isEven should correctly identify even numbers"):
    assert(isEven(2))
    assert(isEven(4))
    assert(!isEven(3))
    assert(!isEven(5))

  // 測試用例 4: 階乘
  test("factorial should correctly calculate factorial"):
    assertEquals(factorial(0), 1)
    assertEquals(factorial(1), 1)
    assertEquals(factorial(5), 120)
    assertEquals(factorial(10), 3628800)
