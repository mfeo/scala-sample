/**
 * 測試檔案：OptionTry.scala
 *
 * 展示如何測試使用 Option 和 Try 的程式碼。
 */

import scala.util.{Try, Success, Failure}

class OptionTryTest extends munit.FunSuite:

  // 測試 Option
  test("Option.map should transform value"):
    val opt = Some(5)
    val mapped = opt.map(_ * 2)
    assertEquals(mapped, Some(10))

  test("Option.map on None should return None"):
    val opt: Option[Int] = None
    val mapped = opt.map(_ * 2)
    assertEquals(mapped, None)

  test("Option.flatMap should flatten nested Options"):
    val opt = Some(5)
    val flatMapped = opt.flatMap(n => Some(n * 2))
    assertEquals(flatMapped, Some(10))

  test("Option.getOrElse should return value or default"):
    assertEquals(Some(5).getOrElse(0), 5)
    assertEquals(None.asInstanceOf[Option[Int]].getOrElse(0), 0)

  test("Option.exists should check condition"):
    assert(Some(5).exists(_ > 3))
    assert(!Some(5).exists(_ > 10))
    assert(!None.asInstanceOf[Option[Int]].exists(_ > 3))

  // 測試 Try
  test("Try.Success should hold value"):
    val result = Success(42)
    assertEquals(result.getOrElse(0), 42)

  test("Try.Failure should hold exception"):
    val exception = new Exception("test error")
    val result = Failure(exception)
    assert(result.isFailure)
    assert(result.failed.get == exception)

  test("Try.map should transform success"):
    val result = Success(10)
    val mapped = result.map(_ * 2)
    assertEquals(mapped, Success(20))

  test("Try.map on Failure should return Failure"):
    val result: Try[Int] = Failure(new Exception("error"))
    val mapped = result.map(_ * 2)
    assert(mapped.isFailure)

  test("Try.flatMap should flatten nested Trys"):
    val result = Success(10)
    val flatMapped = result.flatMap(n => Success(n / 2))
    assertEquals(flatMapped, Success(5))

  test("Try.toOption should convert to Option"):
    assertEquals(Success(42).toOption, Some(42))
    assert(Failure(new Exception("error")).asInstanceOf[Try[Int]].toOption == None)
