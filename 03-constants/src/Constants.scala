/**
 * 章節 03: 常數與不可變性
 *
 * 學習目標：
 * 1. 理解 val 作為常數的含義
 * 2. 認識 lazy val 延遲初始化
 * 3. 學習常數的命名慣例
 * 4. 在物件層級定義常數
 * 5. 理解編譯時常數與執行時常數的區別
 */

@main def run(): Unit =
  println("=== 章節 03: 常數與不可變性 ===\n")

  example1()
  example2()
  example3()
  example4()
  example5()

  println("=== 章節完成 ===")

/**
 * 範例 1: val 作為常數的含義
 *
 * 在 Scala 中，val 用於宣告不可變的變數（常數）。
 * 一旦指派，就不能改變其值。
 * 這使得代碼更易於理解和測試。
 */
def example1(): Unit =
  println("--- 範例 1: val 作為常數的含義 ---")

  // 定義局部常數
  val MAX_USERS = 100
  val API_KEY = "sk-1234567890abcdef"
  val MIN_PASSWORD_LENGTH = 8

  println(s"MAX_USERS = $MAX_USERS")
  println(s"API_KEY = $API_KEY")
  println(s"MIN_PASSWORD_LENGTH = $MIN_PASSWORD_LENGTH")

  // val 一旦指派就不能改變
  // MAX_USERS = 200  // 編譯錯誤！
  // println("無法改變 val 的值")

  // val 可以進行複雜的計算，但計算只會執行一次
  val calculatedValue = {
    println("  計算值中...")
    10 + 20 + 30
  }
  println(s"calculatedValue = $calculatedValue")

  println()

/**
 * 範例 2: lazy val 延遲初始化
 *
 * lazy val 在第一次被訪問時才進行初始化（而不是宣告時）。
 * 這對於初始化成本高昂的值很有用。
 */
def example2(): Unit =
  println("--- 範例 2: lazy val 延遲初始化 ---")

  // 非 lazy val：宣告時就會計算
  println("宣告 normalVal...")
  val normalVal = {
    println("  normalVal 正在計算...")
    1 + 2 + 3
  }

  println("\n宣告 lazyValue...")
  lazy val lazyValue = {
    println("  lazyValue 正在計算（延遲初始化）...")
    1 + 2 + 3
  }

  println("lazy val 已宣告，但還未計算")

  println("\n首次訪問 lazyValue...")
  val result = lazyValue
  println(s"lazyValue = $result")

  println("再次訪問 lazyValue...")
  val result2 = lazyValue
  println(s"lazyValue = $result2（不會再計算）")

  println()

/**
 * 範例 3: 常數的命名慣例
 *
 * Scala 中常數有不同的命名慣例：
 * - 大寫蛇形（UPPER_CASE）: 用於全局或重要的常數
 * - PascalCase: 用於物件層級的常數或特殊值
 * - camelCase: 用於局部變數（即使是 val）
 */
def example3(): Unit =
  println("--- 範例 3: 常數的命名慣例 ---")

  // 全局重要的常數：使用 UPPER_CASE
  val MAX_RETRIES = 3
  val TIMEOUT_MS = 5000
  val DATABASE_URL = "jdbc:mysql://localhost:3306/mydb"

  println(s"全局常數: MAX_RETRIES = $MAX_RETRIES")
  println(s"全局常數: TIMEOUT_MS = $TIMEOUT_MS")
  println(s"全局常數: DATABASE_URL = $DATABASE_URL")

  // 局部常數：可以使用 camelCase
  val defaultPort = 8080
  val defaultHost = "localhost"
  val maxConnections = 100

  println(s"局部常數: defaultPort = $defaultPort")
  println(s"局部常數: defaultHost = $defaultHost")
  println(s"局部常數: maxConnections = $maxConnections")

  println()

/**
 * 範例 4: 物件層級常數
 *
 * 使用 object 來定義全局常數。
 * 這些常數在整個應用程式中都可以訪問。
 */
def example4(): Unit =
  println("--- 範例 4: 物件層級常數 ---")

  // 訪問在 ConfigConstants 物件中定義的常數
  println(s"應用版本: ${ConfigConstants.VERSION}")
  println(s"最大用戶數: ${ConfigConstants.MAX_USERS}")
  println(s"數據庫主機: ${ConfigConstants.DB_HOST}")
  println(s"數據庫端口: ${ConfigConstants.DB_PORT}")

  println()

/**
 * 範例 5: 編譯時常數與執行時常數
 *
 * 編譯時常數：值在編譯時就已確定，可以用於註解等編譯級特性
 * 執行時常數：值在執行時確定，大多數常數都是這種
 */
def example5(): Unit =
  println("--- 範例 5: 編譯時常數與執行時常數 ---")

  // 編譯時常數：必須是字面值（literal）
  val COMPILE_TIME_CONSTANT = "hello"  // 字面字串
  val NUMBER_CONSTANT = 42              // 字面數字

  println(s"編譯時常數（字面值）: COMPILE_TIME_CONSTANT = $COMPILE_TIME_CONSTANT")
  println(s"編譯時常數（字面值）: NUMBER_CONSTANT = $NUMBER_CONSTANT")

  // 執行時常數：是計算的結果
  val RUNTIME_CONSTANT = "hello".toUpperCase  // 計算結果
  val CALCULATED_VALUE = 10 + 20 + 30         // 計算結果

  println(s"執行時常數（計算結果）: RUNTIME_CONSTANT = $RUNTIME_CONSTANT")
  println(s"執行時常數（計算結果）: CALCULATED_VALUE = $CALCULATED_VALUE")

  // 執行時常數也是不可變的，但值在執行時才確定
  val timestamp = System.currentTimeMillis()  // 每次執行都不同
  println(s"執行時常數（系統值）: timestamp = $timestamp")

  println()

/**
 * 物件層級常數定義
 *
 * 這是定義全局常數的標準做法。
 * 在 Scala 中，object 相當於 Java 中的靜態常數類別。
 */
object ConfigConstants:
  // 應用配置常數
  val VERSION = "1.0.0"
  val APP_NAME = "Scala Learning"

  // 用戶管理相關常數
  val MAX_USERS = 10000
  val MIN_PASSWORD_LENGTH = 8

  // 數據庫相關常數
  val DB_HOST = "localhost"
  val DB_PORT = 3306
  val DB_NAME = "scala_db"
  val DB_URL = s"jdbc:mysql://$DB_HOST:$DB_PORT/$DB_NAME"

  // 時間相關常數（秒）
  val SESSION_TIMEOUT = 3600
  val CACHE_EXPIRY = 1800
