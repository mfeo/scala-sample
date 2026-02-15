/**
 * 章節 14: 上下文抽象 (Contextual Abstractions)
 *
 * 學習目標：
 * 1. 理解 Scala 3 的 Given Instances (`given`) 與 Scala 2 `implicit val` 的對照
 * 2. 掌握 Using Clauses (`using`) 與 Scala 2 `implicit parameter` 的對照
 * 3. 學習擴充方法 (Extension Methods) 與 Scala 2 `implicit class` 的對照
 */

@main def run(): Unit =
  println("=== 章節 14: 上下文抽象 (Contextual Abstractions) ===\n")

  example1()
  example2()
  example3()

  println("=== 章節完成 ===")

/**
 * 範例 1: 上下文參數 (Context Parameters)
 *
 * 這是依賴注入與型別類 (Type Classes) 的基礎。
 */
def example1(): Unit =
  println("--- 範例 1: 上下文參數 (Given/Using vs Implicits) ---")

  // 定義一個行為特徵 (Type Class)
  trait Ord[T]:
    def compare(x: T, y: T): Int

  // ==========================================
  // Scala 2 風格 (Implicits)
  // ==========================================
  object Scala2Style:
    // 定義隱式值
    implicit val intOrd: Ord[Int] = new Ord[Int] {
      def compare(x: Int, y: Int): Int =
        if (x < y) -1 else if (x > y) 1 else 0
    }

    // 定義隱式參數
    def max[T](x: T, y: T)(implicit ord: Ord[T]): T =
      if (ord.compare(x, y) >= 0) x else y

  // ==========================================
  // Scala 3 風格 (Given/Using)
  // ==========================================
  object Scala3Style:
    // 定義 Given Instance (提供者)
    given intOrd: Ord[Int] with
      def compare(x: Int, y: Int): Int =
        if x < y then -1 else if x > y then 1 else 0

    // 定義 Using Clause (消費者)
    def max[T](x: T, y: T)(using ord: Ord[T]): T =
      if ord.compare(x, y) >= 0 then x else y

  // 使用 (兩者呼叫方式在客戶端看起來很像，都是自動推斷)
  println(s"Scala 2 max: ${Scala2Style.max(10, 20)}")
  println(s"Scala 3 max: ${Scala3Style.max(10, 20)}")

  println()

/**
 * 範例 2: 擴充方法 (Extension Methods)
 *
 * 允許為現有型別增加新方法，而無需修改原始碼。
 */
def example2(): Unit =
  println("--- 範例 2: 擴充方法 (Extension vs Implicit Class) ---")

  case class Circle(radius: Double)

  // ==========================================
  // Scala 2 風格 (Implicit Class)
  // ==========================================
  object Scala2Extensions:
    implicit class CircleOps(c: Circle) {
      def area: Double = Math.PI * c.radius * c.radius
    }

  // ==========================================
  // Scala 3 風格 (Extension Methods)
  // ==========================================
  object Scala3Extensions:
    extension (c: Circle)
      def circumference: Double = 2 * Math.PI * c.radius

  val c = Circle(5.0)

  // 使用 Scala 2 風格擴充
  import Scala2Extensions._
  println(s"Area (Scala 2 style): ${c.area}")

  // 使用 Scala 3 風格擴充
  import Scala3Extensions._
  println(s"Circumference (Scala 3 style): ${c.circumference}")

  println()

/**
 * 範例 3: 隱式轉換 (Implicit Conversions)
 *
 * 警告：隱式轉換雖然強大但危險，應謹慎使用。
 * Scala 3 改變了定義方式以提高安全性。
 */
def example3(): Unit =
  println("--- 範例 3: 隱式轉換 ---")

  case class Email(addr: String)

  // ==========================================
  // Scala 2 風格 (Implicit Def)
  // ==========================================
  object Scala2Conversions:
    implicit def stringToEmail(s: String): Email = Email(s)

  // ==========================================
  // Scala 3 風格 (Conversion Type Class)
  // ==========================================
  object Scala3Conversions:
    // 必須明確給定 Conversion[From, To] 實例
    given Conversion[String, Email] with
      def apply(s: String): Email = Email(s)

  // 使用 Scala 2 風格
  import Scala2Conversions._
  val email1: Email = "alice@example.com" // 自動轉換
  println(s"Scala 2 auto-convert: $email1")

  // 使用 Scala 3 風格
  // 注意：Scala 3 預設需要 import scala.language.implicitConversions
  // 這裡僅展示定義方式
  import Scala3Conversions.given
  import scala.language.implicitConversions
  val email2: Email = "bob@example.com"
  println(s"Scala 3 auto-convert: $email2")
