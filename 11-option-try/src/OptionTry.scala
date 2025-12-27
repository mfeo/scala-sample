/**
 * 章節 11: Option 與 Try
 *
 * 學習目標：
 * 1. 認識 Option[T]（Some 和 None）
 * 2. 掌握 Option 的常用操作（map、flatMap、getOrElse、fold）
 * 3. 學習 Try[T]（Success 和 Failure）
 * 4. 進行 Try 的鏈式操作
 * 5. 對比 Option 和 Try 與傳統 null/try-catch 的區別
 * 6. 在 for comprehension 中使用 Option 和 Try
 */

@main def run(): Unit =
  println("=== 章節 11: Option 與 Try ===\n")

  example1()
  example2()
  example3()
  example4()
  example5()
  example6()
  example7()

  println("=== 章節完成 ===")

/**
 * 範例 1: Option[T] 的基礎
 *
 * Option 代表一個可能存在或不存在的值。
 * Some(value) 表示值存在，None 表示值不存在。
 */
def example1(): Unit =
  println("--- 範例 1: Option[T] 的基礎 ---")

  // 建立 Option
  val someValue: Option[Int] = Some(42)
  val noneValue: Option[Int] = None

  println(s"someValue = $someValue")
  println(s"noneValue = $noneValue")

  // 透過模式匹配處理 Option
  someValue match
    case Some(value) => println(s"有值: $value")
    case None => println("沒有值")

  noneValue match
    case Some(value) => println(s"有值: $value")
    case None => println("沒有值")

  // Option.empty 建立空的 Option
  val empty: Option[String] = Option.empty
  println(s"empty = $empty")

  // Option() 可以將可能為 null 的值轉換為 Option
  val nullableValue: String = null
  val optionFromNullable = Option(nullableValue)
  println(s"Option(null) = $optionFromNullable")

  println()

/**
 * 範例 2: Option 的常用操作
 *
 * map、flatMap、getOrElse 等是常用的 Option 操作。
 */
def example2(): Unit =
  println("--- 範例 2: Option 的常用操作 ---")

  val number: Option[Int] = Some(10)

  // map：變換 Option 中的值
  val doubled = number.map(_ * 2)
  println(s"Some(10).map(_ * 2) = $doubled")

  // 如果是 None，map 不會執行
  val noneValue: Option[Int] = None
  val result = noneValue.map(_ * 2)
  println(s"None.map(_ * 2) = $result")

  // getOrElse：提供預設值
  val value1 = number.getOrElse(0)
  val value2 = noneValue.getOrElse(0)
  println(s"Some(10).getOrElse(0) = $value1")
  println(s"None.getOrElse(0) = $value2")

  // exists：檢查是否存在滿足條件的值
  println(s"Some(10).exists(_ > 5) = ${number.exists(_ > 5)}")
  println(s"Some(10).exists(_ > 20) = ${number.exists(_ > 20)}")
  println(s"None.exists(_ > 5) = ${noneValue.exists(_ > 5)}")

  // fold：將 Option 縮減為單一值
  val folded = number.fold(0)(_ * 2)
  println(s"Some(10).fold(0)(_ * 2) = $folded")

  val foldedNone = noneValue.fold(0)(_ * 2)
  println(s"None.fold(0)(_ * 2) = $foldedNone")

  println()

/**
 * 範例 3: flatMap（處理嵌套的 Option）
 *
 * flatMap 用於處理回傳 Option 的函式。
 * 它可以扁平化嵌套的 Option。
 */
def example3(): Unit =
  println("--- 範例 3: flatMap ---")

  def safeDivide(a: Int, b: Int): Option[Int] =
    if b == 0 then None
    else Some(a / b)

  val num = Some(10)

  // 不使用 flatMap（會產生嵌套的 Option）
  val nestedOption = num.map(n => safeDivide(n, 2))
  println(s"使用 map: $nestedOption")  // Some(Some(5))

  // 使用 flatMap（自動扁平化）
  val flatOption = num.flatMap(n => safeDivide(n, 2))
  println(s"使用 flatMap: $flatOption")  // Some(5)

  // 錯誤的情況
  val errorCase = num.flatMap(n => safeDivide(n, 0))
  println(s"safeDivide(10, 0) = $errorCase")  // None

  println()

/**
 * 範例 4: Try[T] 的基礎與操作
 *
 * Try 用於処理可能拋出異常的操作。
 * Success(value) 表示成功，Failure(exception) 表示失敗。
 */
def example4(): Unit =
  println("--- 範例 4: Try[T] 的基礎 ---")

  import scala.util.Try

  // 建立 Try
  val success: Try[Int] = Try(10 / 2)
  val failure: Try[Int] = Try(10 / 0)

  println(s"Try(10 / 2) = $success")
  println(s"Try(10 / 0) = $failure")

  // 透過模式匹配處理 Try
  success match
    case scala.util.Success(value) => println(s"成功: $value")
    case scala.util.Failure(e) => println(s"失敗: ${e.getMessage}")

  failure match
    case scala.util.Success(value) => println(s"成功: $value")
    case scala.util.Failure(e) => println(s"失敗: ${e.getMessage}")

  // Try 的操作
  val mapped = success.map(_ * 2)
  println(s"\nsuccess.map(_ * 2) = $mapped")

  val flatMapped = success.flatMap(n => Try(100 / n))
  println(s"success.flatMap(n => Try(100 / n)) = $flatMapped")

  // getOrElse
  val value1 = success.getOrElse(0)
  val value2 = failure.getOrElse(0)
  println(s"success.getOrElse(0) = $value1")
  println(s"failure.getOrElse(0) = $value2")

  println()

/**
 * 範例 5: Try 的鏈式操作
 *
 * Try 支援多步驟的鏈式操作，類似於 Option。
 */
def example5(): Unit =
  println("--- 範例 5: Try 的鏈式操作 ---")

  import scala.util.Try

  def parseString(s: String): Try[Int] =
    Try(s.toInt)

  def safeDivide(a: Int, b: Int): Try[Int] =
    if b == 0 then
      scala.util.Failure(new ArithmeticException("除數不能為零"))
    else
      scala.util.Success(a / b)

  // 鏈式操作
  val result = for
    num <- parseString("20")
    divided <- safeDivide(num, 2)
  yield divided * 2

  println(s"for comprehension: $result")

  // 錯誤情況
  val errorResult = for
    num <- parseString("twenty")  // 會失敗
    divided <- safeDivide(num, 2)
  yield divided * 2

  println(s"錯誤的 for comprehension: $errorResult")

  println()

/**
 * 範例 6: Option 和 Try 的結合使用
 *
 * 在現實中，經常需要組合 Option 和 Try。
 */
def example6(): Unit =
  println("--- 範例 6: Option 和 Try 的結合 ---")

  import scala.util.Try

  case class User(id: Int, name: String, email: String)

  // 模擬資料庫
  val userDatabase = Map(
    1 -> User(1, "Alice", "alice@example.com"),
    2 -> User(2, "Bob", "bob@example.com")
  )

  // 查詢使用者，使用 Option
  def findUser(id: Int): Option[User] =
    userDatabase.get(id)

  // 解析 ID，使用 Try
  def parseUserId(idStr: String): Try[Int] =
    Try(idStr.toInt)

  // 結合使用
  def getUserByString(idStr: String): Option[User] =
    parseUserId(idStr).toOption.flatMap(findUser)

  println(s"查詢 ID \"1\": ${getUserByString("1")}")
  println(s"查詢 ID \"3\": ${getUserByString("3")}")
  println(s"查詢 ID \"invalid\": ${getUserByString("invalid")}")

  println()

/**
 * 範例 7: 對比不同的錯誤處理方式
 *
 * 比較傳統方式（null/try-catch）與 Scala 的方式（Option/Try）。
 */
def example7(): Unit =
  println("--- 範例 7: 錯誤處理方式對比 ---")

  import scala.util.Try

  // 傳統的 Java 風格（使用 null）
  def parseIntOldStyle(s: String): Integer =
    try
      Integer.parseInt(s)
    catch
      case _: NumberFormatException => null

  // Scala 的方式（使用 Try）
  def parseIntScalaWay(s: String): Try[Int] =
    Try(s.toInt)

  // Scala 的另一種方式（使用 Option）
  def parseIntWithOption(s: String): Option[Int] =
    Try(s.toInt).toOption

  println("解析 \"42\":")
  println(s"  傳統方式: ${parseIntOldStyle("42")}")
  println(s"  Try: ${parseIntScalaWay("42")}")
  println(s"  Option: ${parseIntWithOption("42")}")

  println("\n解析 \"invalid\":")
  println(s"  傳統方式: ${parseIntOldStyle("invalid")}")
  println(s"  Try: ${parseIntScalaWay("invalid")}")
  println(s"  Option: ${parseIntWithOption("invalid")}")

  println("\n優勢:")
  println("✓ 型別安全：無法忽略可能的失敗情況")
  println("✓ 函式式：支援 map、flatMap 等操作")
  println("✓ 可讀性：代碼意圖明確")
  println("✓ 組合性：容易組合多個操作")

  println()
