/**
 * 章節 12: Either 與異常處理
 *
 * 學習目標：
 * 1. 掌握 Either[L, R] 的概念與用法
 * 2. 學習 Left（錯誤）和 Right（成功）
 * 3. 進行 Either 的常用操作（map、flatMap 等）
 * 4. 認識傳統的 try-catch-finally 異常處理
 * 5. 定義自定義異常類別
 * 6. 比較函數式與命令式的錯誤處理
 * 7. 實踐錯誤處理的最佳實踐
 */

@main def run(): Unit =
  println("=== 章節 12: Either 與異常處理 ===\n")

  example1()
  example2()
  example3()
  example4()
  example5()
  example6()
  example7()

  println("=== 章節完成 ===")

/**
 * 範例 1: Either[L, R] 的基礎
 *
 * Either 代表兩種可能的結果：Left（通常表示錯誤）或 Right（通常表示成功）。
 * 與 Option 不同，Either 可以在失敗時傳遞錯誤資訊。
 */
def example1(): Unit =
  println("--- 範例 1: Either[L, R] 的基礎 ---")

  // 定義一個回傳 Either 的函式
  def divide(a: Int, b: Int): Either[String, Int] =
    if b == 0 then
      Left("除數不能為零")
    else
      Right(a / b)

  val success = divide(10, 2)
  val failure = divide(10, 0)

  println(s"divide(10, 2) = $success")
  println(s"divide(10, 0) = $failure")

  // 透過模式匹配處理 Either
  success match
    case Right(result) => println(s"成功: $result")
    case Left(error) => println(s"失敗: $error")

  failure match
    case Right(result) => println(s"成功: $result")
    case Left(error) => println(s"失敗: $error")

  println()

/**
 * 範例 2: Either 的常用操作
 *
 * Either 支援 map、flatMap、getOrElse 等操作，類似於 Option。
 */
def example2(): Unit =
  println("--- 範例 2: Either 的常用操作 ---")

  def parseNumber(s: String): Either[String, Int] =
    try
      Right(s.toInt)
    catch
      case _: NumberFormatException => Left(s"無法解析 \"$s\" 為整數")

  // map：在 Right 上進行變換
  val mapped = parseNumber("42").map(_ * 2)
  println(s"parseNumber(\"42\").map(_ * 2) = $mapped")

  val errorMapped = parseNumber("invalid").map(_ * 2)
  println(s"parseNumber(\"invalid\").map(_ * 2) = $errorMapped")

  // flatMap：鏈式操作
  def safeDivide(a: Int, b: Int): Either[String, Int] =
    if b == 0 then Left("除數不能為零")
    else Right(a / b)

  val result = parseNumber("20")
    .flatMap(num => safeDivide(num, 2))
    .map(_ * 2)
  println(s"鏈式操作結果: $result")

  // getOrElse：提供預設值
  println(s"Success.getOrElse(0) = ${parseNumber(\"42\").getOrElse(0)}")
  println(s"Failure.getOrElse(0) = ${parseNumber(\"invalid\").getOrElse(0)}")

  // fold：將 Either 縮減為單一值
  val value = parseNumber("42").fold(
    error => s"錯誤: $error",
    number => s"成功: $number"
  )
  println(s"fold 結果: $value")

  println()

/**
 * 範例 3: 自定義異常類別
 *
 * 可以建立自定義的異常類別來表達不同的錯誤類型。
 */
def example3(): Unit =
  println("--- 範例 3: 自定義異常類別 ---")

  // 定義自定義異常
  case class ValidationException(message: String) extends Exception(message)
  case class DatabaseException(message: String) extends Exception(message)

  // 使用自定義異常
  def validateEmail(email: String): Either[ValidationException, String] =
    if email.contains("@") && email.contains(".") then
      Right(email)
    else
      Left(ValidationException(s"無效的電郵: $email"))

  def validateAge(age: String): Either[ValidationException, Int] =
    try
      val ageInt = age.toInt
      if ageInt >= 0 && ageInt <= 150 then
        Right(ageInt)
      else
        Left(ValidationException(s"年齡必須在 0-150 之間，收到: $ageInt"))
    catch
      case _: NumberFormatException =>
        Left(ValidationException(s"年齡必須是數字，收到: $age"))

  // 測試
  println(s"驗證電郵 \"alice@example.com\": ${validateEmail("alice@example.com")}")
  println(s"驗證電郵 \"invalid\": ${validateEmail("invalid")}")
  println(s"驗證年齡 \"25\": ${validateAge("25")}")
  println(s"驗證年齡 \"200\": ${validateAge("200")}")
  println(s"驗證年齡 \"abc\": ${validateAge("abc")}")

  println()

/**
 * 範例 4: try-catch-finally
 *
 * 傳統的 try-catch-finally 異常處理方式。
 */
def example4(): Unit =
  println("--- 範例 4: try-catch-finally ---")

  // 基本的 try-catch
  try
    val result = 10 / 0
    println(s"結果: $result")
  catch
    case e: ArithmeticException => println(s"捕獲異常: ${e.getMessage}")
    case e: Exception => println(s"捕獲其他異常: ${e.getMessage}")

  println()

  // try-catch-finally
  println("執行 try-catch-finally:")
  try
    println("  執行中...")
    val result = 20 / 2
    println(s"  成功: $result")
  catch
    case e: Exception => println(s"  發生異常: ${e.getMessage}")
  finally
    println("  finally 區塊（總是執行）")

  println()

  // catch 多個異常
  println("捕獲多個異常:")
  val data = "hello"
  try
    val num = data.toInt
    println(s"轉換成功: $num")
  catch
    case e: NumberFormatException =>
      println(s"數字轉換失敗: ${e.getMessage}")
    case e: NullPointerException =>
      println(s"空值錯誤: ${e.getMessage}")
    case e: Exception =>
      println(s"其他錯誤: ${e.getMessage}")

  println()

/**
 * 範例 5: 函數式 vs 命令式錯誤處理
 *
 * 比較不同的錯誤處理風格。
 */
def example5(): Unit =
  println("--- 範例 5: 函數式 vs 命令式 ---")

  def processDataCommandStyle(data: String): String =
    try
      val num = data.toInt
      if num > 0 then
        s"正數: $num"
      else
        "非正數"
    catch
      case e: NumberFormatException => "解析失敗"

  def processDataFunctional(data: String): Either[String, String] =
    for
      num <- Either.catchNonFatal(data.toInt)
        .left.map(_ => "解析失敗")
      message <- if num > 0 then Right(s"正數: $num")
                else Right("非正數")
    yield message

  // 測試
  println(s"命令式風格（\"42\"）: ${processDataCommandStyle("42")}")
  println(s"函數式風格（\"42\"）: ${processDataFunctional("42")}")

  println(s"命令式風格（\"invalid\"）: ${processDataCommandStyle("invalid")}")
  println(s"函數式風格（\"invalid\"）: ${processDataFunctional("invalid")}")

  println("\n函數式風格的優勢:")
  println("✓ 更易於測試（沒有異常的副作用）")
  println("✓ 更易於組合（可以用 map/flatMap 鏈接）")
  println("✓ 型別安全（編譯器知道可能的錯誤）")

  println()

/**
 * 範例 6: 實踐應用 - 用戶驗證
 *
 * 一個實際的例子：驗證和處理使用者資料。
 */
def example6(): Unit =
  println("--- 範例 6: 實踐應用 - 用戶驗證 ---")

  case class User(id: Int, name: String, email: String, age: Int)

  sealed trait ValidationError
  case class InvalidName(message: String) extends ValidationError
  case class InvalidEmail(message: String) extends ValidationError
  case class InvalidAge(message: String) extends ValidationError

  def validateName(name: String): Either[ValidationError, String] =
    if name.length >= 2 && name.length <= 50 then
      Right(name)
    else
      Left(InvalidName("名字長度必須在 2-50 之間"))

  def validateEmail(email: String): Either[ValidationError, String] =
    if email.contains("@") && email.contains(".") then
      Right(email)
    else
      Left(InvalidEmail("電郵格式無效"))

  def validateAge(age: Int): Either[ValidationError, Int] =
    if age >= 18 && age <= 120 then
      Right(age)
    else
      Left(InvalidAge("年齡必須在 18-120 之間"))

  def createUser(id: Int, name: String, email: String, age: Int): Either[ValidationError, User] =
    for
      validName <- validateName(name)
      validEmail <- validateEmail(email)
      validAge <- validateAge(age)
    yield User(id, validName, validEmail, validAge)

  // 測試
  println("建立有效使用者:")
  createUser(1, "Alice", "alice@example.com", 25) match
    case Right(user) => println(s"  成功: $user")
    case Left(error) => println(s"  失敗: $error")

  println("建立無效使用者（年齡太小）:")
  createUser(2, "Bob", "bob@example.com", 16) match
    case Right(user) => println(s"  成功: $user")
    case Left(error) => println(s"  失敗: $error")

  println()

/**
 * 範例 7: 錯誤處理的最佳實踐
 *
 * 總結錯誤處理的要點和建議。
 */
def example7(): Unit =
  println("--- 範例 7: 錯誤處理最佳實踐 ---")

  println("✓ 推薦使用 Option/Try/Either 而非 null/try-catch")
  println("✓ 在邊界處使用 Try，內部使用 Either 或 Option")
  println("✓ 提供有意義的錯誤訊息")
  println("✓ 使用 Sealed Trait 定義特定的錯誤型別")
  println("✓ 利用 map/flatMap 進行函數式的錯誤處理")
  println("✓ 在必要時使用 fold 轉換結果")

  println("\n錯誤處理工具的選擇:")
  println("• Option：值可能不存在，不需要錯誤詳情")
  println("• Try：可能拋出異常的操作")
  println("• Either：需要傳遞特定錯誤資訊")

  println("\n完整的程式應該:")
  println("1. 在使用者輸入邊界驗證")
  println("2. 將錯誤轉換為明確的型別")
  println("3. 使用函數式的方式進行錯誤傳播")
  println("4. 只在最頂層的 main 中進行 try-catch 作為最後防線")

  println()
