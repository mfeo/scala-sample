/**
 * 章節 15: 進階型別系統 (Advanced Types)
 *
 * 學習目標：
 * 1. 比較 Scala 3 Enum 與 Scala 2 Sealed Trait
 * 2. 理解 Union Types (`|`) 與 Intersection Types (`&`)
 * 3. 認識 Opaque Types (不透明型別) 與 Value Classes
 */

@main def run(): Unit =
  println("=== 章節 15: 進階型別系統 (Advanced Types) ===\n")

  example1()
  example2()
  example3()
  example4()

  println("=== 章節完成 ===")

/**
 * 範例 1: 列舉 (Enum)
 *
 * 用於定義有限的選項集合或代數資料型別 (ADT)。
 */
def example1(): Unit =
  println("--- 範例 1: 列舉 (Enum vs Sealed Trait) ---")

  // ==========================================
  // Scala 2 風格 (Sealed Trait + Case Objects)
  // ==========================================
  sealed trait Color2
  object Color2 {
    case object Red extends Color2
    case object Green extends Color2
    case object Blue extends Color2
  }

  val c2: Color2 = Color2.Red
  println(s"Scala 2 Color: $c2")

  // ==========================================
  // Scala 3 風格 (Enum)
  // ==========================================
  enum Color3:
    case Red, Green, Blue

  val c3: Color3 = Color3.Red
  println(s"Scala 3 Enum: $c3")

  // 帶參數的 Enum (ADT)
  enum Message:
    case Quit
    case Move(x: Int, y: Int)
    case Write(content: String)

  val msg = Message.Move(10, 20)
  println(s"Scala 3 ADT: $msg")
  
  println()

/**
 * 範例 2: 聯集型別 (Union Types)
 *
 * 表示一個值可以是 A "或" B 型別。
 */
def example2(): Unit =
  println("--- 範例 2: 聯集型別 (Union Types) ---")

  // ==========================================
  // Scala 2 風格 (使用 Either 或共同父類)
  // ==========================================
  // 通常使用 Either[A, B] 來表達 A 或 B
  def parseSc2(s: String): Either[String, Int] =
    if (s.matches("\\d+")) Right(s.toInt) else Left(s"Invalid number: $s")

  parseSc2("123") match {
    case Right(i) => println(s"Scala 2 Right: $i")
    case Left(err) => println(s"Scala 2 Left: $err")
  }

  // ==========================================
  // Scala 3 風格 (Union Types A | B)
  // ==========================================
  // 這裡回傳型別是 String | Int
  def parseSc3(s: String): String | Int =
    if s.matches("\\d+") then s.toInt else s"Invalid number: $s"

  val res3 = parseSc3("ABC")
  // 處理時使用模式匹配
  res3 match
    case i: Int => println(s"Scala 3 Int: $i")
    case s: String => println(s"Scala 3 String: $s")

  println()

/**
 * 範例 3: 交集型別 (Intersection Types)
 *
 * 表示一個值必須同時滿足 A "和" B 型別。
 */
def example3(): Unit =
  println("--- 範例 3: 交集型別 (Intersection Types) ---")

  trait Resettable:
    def reset(): Unit

  trait Growable:
    def grow(): Unit

  // ==========================================
  // Scala 2 風格 (with 關鍵字)
  // ==========================================
  def processSc2(x: Resettable with Growable): Unit =
    x.reset()
    x.grow()

  // ==========================================
  // Scala 3 風格 (& 符號)
  // ==========================================
  // 語義上更接近集合的交集，且具交換律 (A & B 等同於 B & A)
  def processSc3(x: Resettable & Growable): Unit =
    x.reset()
    x.grow()

  class Component extends Resettable, Growable:
    def reset() = print("Reset..")
    def grow() = println("Grow..")

  val comp = new Component
  print("Scala 2 call: ")
  processSc2(comp)
  
  print("Scala 3 call: ")
  processSc3(comp)

  println()

/**
 * 範例 4: 不透明型別別名 (Opaque Type Aliases)
 *
 * 提供零成本的抽象：在編譯期區分型別，執行期無包裝開銷。
 */
// Value Class 必須定義在頂層
case class LogIDSc2(val id: String) extends AnyVal

def example4(): Unit =
  println("--- 範例 4: Opaque Types vs Value Classes ---")

  // ==========================================
  // Scala 2 風格 (Value Class)
  // ==========================================
  // 繼承 AnyVal 來避免執行期分配物件，但有時仍會發生裝箱 (boxing)
  
  val id2 = LogIDSc2("user-123")
  println(s"Scala 2 Value Class: $id2")

  // ==========================================
  // Scala 3 風格 (Opaque Type)
  // ==========================================
  object LogTypes:
    opaque type LogID = String
    
    // 必須在定義域內提供建構與存取方法
    object LogID:
      def apply(s: String): LogID = s
    
    extension (id: LogID)
      def value: String = id

  import LogTypes.*
  val id3: LogID = LogID("user-456")
  // val fail: LogID = "user-456" // 編譯錯誤！外部無法直接將 String 視為 LogID
  
  println(s"Scala 3 Opaque Type: ${id3.value}") // 執行期這就是一個單純的 String
  
  println()
