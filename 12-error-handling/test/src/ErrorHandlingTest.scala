/**
 * 測試檔案：ErrorHandlingTest.scala
 *
 * 展示如何為使用 Either 和 Try 的程式編寫測試。
 * 使用 munit 測試框架。
 */

class ErrorHandlingTest extends munit.FunSuite:

  // 1. 測試 Either 的基本用法
  test("Either should correctly represent success and failure"):
    def divide(a: Int, b: Int): Either[String, Int] =
      if b == 0 then Left("除數不能為零")
      else Right(a / b)

    // 測試成功情況
    val success = divide(10, 2)
    assertEquals(success, Right(5))
    assert(success.isRight)

    // 測試失敗情況
    val failure = divide(10, 0)
    assertEquals(failure, Left("除數不能為零"))
    assert(failure.isLeft)

  // 2. 測試 Either 的 map 操作
  test("Either.map should transform Right value"):
    val right: Either[String, Int] = Right(10)
    val result = right.map(_ * 2)
    assertEquals(result, Right(20))

    val left: Either[String, Int] = Left("Error")
    val resultLeft = left.map(_ * 2)
    assertEquals(resultLeft, Left("Error"))

  // 3. 測試 Try 與 Either 的轉換
  test("Try can be converted to Either"):
    import scala.util.Try
    
    val successTry = Try("123".toInt).toEither
    assertEquals(successTry, Right(123))

    val failureTry = Try("abc".toInt).toEither
    assert(failureTry.isLeft)
    // 檢查錯誤類型
    failureTry match
      case Left(e) => assert(e.isInstanceOf[NumberFormatException])
      case _ => fail("Should be Left")
