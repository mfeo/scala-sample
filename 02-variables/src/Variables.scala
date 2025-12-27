/**
 * 章節 02: 變數與型別推斷
 *
 * 學習目標：
 * 1. 理解 val 與 var 的區別
 * 2. 掌握 Scala 的型別推斷機制
 * 3. 學習基本的資料型別
 * 4. 進行型別轉換
 * 5. 認識零值（默認值）的概念
 */

@main def run(): Unit =
  println("=== 章節 02: 變數與型別推斷 ===\n")

  example1()
  example2()
  example3()
  example4()
  example5()

  println("=== 章節完成 ===")

/**
 * 範例 1: val 與 var 的區別
 *
 * val: 不可變（immutable）的變數，一旦指派就不能改變
 * var: 可變（mutable）的變數，可以重新指派新的值
 *
 * Scala 推薦使用 val，因為不可變性可以避免許多 bug，
 * 讓代碼更容易理解和測試。
 */
def example1(): Unit =
  println("--- 範例 1: val 與 var 的區別 ---")

  // 使用 val 宣告一個不可變的變數
  val immutableName = "Scala"
  println(s"不可變變數 immutableName: $immutableName")

  // val 指派後不能改變
  // immutableName = "Java"  // 這會導致編譯錯誤

  // 使用 var 宣告一個可變的變數
  var mutableCount = 10
  println(s"可變變數 mutableCount: $mutableCount")

  // var 可以重新指派
  mutableCount = 20
  println(s"修改後的 mutableCount: $mutableCount")

  // 雖然 var 可以改變，但 Scala 推薦盡量使用 val
  // 只在真正需要改變值時才使用 var
  println()

/**
 * 範例 2: 型別推斷
 *
 * Scala 有強大的型別推斷能力。在大多數情況下，
 * 不需要明確指定變數的型別，編譯器會自動推斷。
 *
 * 有時為了可讀性，明確指定型別是好的做法。
 */
def example2(): Unit =
  println("--- 範例 2: 型別推斷 ---")

  // 型別推斷：編譯器自動推斷為 String 型別
  val message = "Hello, Scala!"
  println(s"推斷出型別為 String: $message")

  // 型別推斷：編譯器自動推斷為 Int 型別
  val count = 42
  println(s"推斷出型別為 Int: $count")

  // 型別推斷：編譯器自動推斷為 Double 型別
  val pi = 3.14159
  println(s"推斷出型別為 Double: $pi")

  // 明確指定型別（通常不必須，但有時有助於可讀性）
  val explicitInt: Int = 100
  val explicitString: String = "explicitly typed"
  println(s"明確指定型別: Int = $explicitInt, String = $explicitString")

  println()

/**
 * 範例 3: 基本資料型別
 *
 * Scala 提供的主要基本資料型別：
 * - 整數：Byte、Short、Int、Long
 * - 浮點數：Float、Double
 * - 布林：Boolean
 * - 字元：Char
 * - 字串：String
 */
def example3(): Unit =
  println("--- 範例 3: 基本資料型別 ---")

  // 整數型別
  val byteValue: Byte = 127        // Byte: 8 位元整數 (-128 ~ 127)
  val shortValue: Short = 32000    // Short: 16 位元整數 (-32768 ~ 32767)
  val intValue: Int = 2147483647   // Int: 32 位元整數（預設整數型別）
  val longValue: Long = 9223372036854775807L  // Long: 64 位元整數

  println(s"Byte: $byteValue, Short: $shortValue")
  println(s"Int: $intValue, Long: $longValue")

  // 浮點數型別
  val floatValue: Float = 3.14f    // Float: 32 位元浮點數
  val doubleValue: Double = 3.14159  // Double: 64 位元浮點數（預設浮點數型別）

  println(s"Float: $floatValue, Double: $doubleValue")

  // 布林型別
  val isActive: Boolean = true
  val isInactive: Boolean = false
  println(s"Boolean: isActive = $isActive, isInactive = $isInactive")

  // 字元型別
  val charValue: Char = 'A'
  println(s"Char: $charValue")

  // 字串型別
  val stringValue: String = "Scala Programming"
  println(s"String: $stringValue")

  println()

/**
 * 範例 4: 型別轉換
 *
 * 在某些情況下，需要將一個型別的值轉換為另一個型別。
 * Scala 提供了多種方式進行型別轉換。
 */
def example4(): Unit =
  println("--- 範例 4: 型別轉換 ---")

  // 從 Int 轉換到 String
  val number = 42
  val numberAsString = number.toString
  println(s"Int 轉 String: $number -> $numberAsString (型別: String)")

  // 從 String 轉換到 Int
  val stringNumber = "100"
  val intFromString = stringNumber.toInt
  println(s"String 轉 Int: $stringNumber -> $intFromString (型別: Int)")

  // 從 Double 轉換到 Int（會捨去小數部分）
  val doubleValue = 3.99
  val intFromDouble = doubleValue.toInt
  println(s"Double 轉 Int: $doubleValue -> $intFromDouble (會捨去小數部分)")

  // 從 Int 轉換到 Double
  val intValue = 42
  val doubleFromInt = intValue.toDouble
  println(s"Int 轉 Double: $intValue -> $doubleFromInt")

  // 從 Boolean 轉換到 String
  val boolValue = true
  val boolAsString = boolValue.toString
  println(s"Boolean 轉 String: $boolValue -> $boolAsString")

  println()

/**
 * 範例 5: 零值與多變數宣告
 *
 * 每種型別都有預設的零值：
 * - 數值型別的零值是 0（或 0.0）
 * - 布林的零值是 false
 * - 字串的零值是 null（但不推薦）
 */
def example5(): Unit =
  println("--- 範例 5: 零值與多變數宣告 ---")

  // 使用顯式初始化（推薦做法）
  val a = 0
  val b = 0.0
  val c = false
  println(s"顯式初始化: a = $a, b = $b, c = $c")

  // 在 Scala 中，可以在一行中宣告多個變數
  val (x, y, z) = (10, 20, 30)
  println(s"多變數宣告: x = $x, y = $y, z = $z")

  // 也可以宣告多個相同型別的變數
  val name = "Alice"
  val age = 25
  val score = 95.5
  println(s"多個變數: 名字 = $name, 年齡 = $age, 分數 = $score")

  println()
