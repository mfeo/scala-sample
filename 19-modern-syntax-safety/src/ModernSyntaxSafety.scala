/**
 * 章節 19: 現代語法與安全 (Modern Syntax & Safety)
 *
 * 學習目標：
 * 1. 了解 Scala 3 的通用相等性 (Multiversal Equality) 以避免型別錯誤 (Scala 3 Only)
 * 2. 使用參數解構 (Parameter Untupling) 簡化 Tuple 處理 (Scala 3 Only)
 * 3. 認識行內函式 (Inline) 進行效能優化 (Scala 3 Only)
 *
 * 這些是 Scala 3 引入的「生活品質」(QoL) 改進，讓程式碼更安全、簡潔且高效。
 */

package modern_syntax_safety

import scala.language.strictEquality // 啟用嚴格相等性檢查

@main def runModernSyntaxSafety(): Unit =
  println("=== 章節 19: 現代語法與安全 ===\n")

  // 範例 1: 通用相等性 (Multiversal Equality)
  exampleMultiversalEquality()

  // 範例 2: 參數解構 (Parameter Untupling)
  exampleParameterUntupling()

  // 範例 3: 行內函式 (Inline)
  exampleInlineBasics()

  println("\n=== 章節完成 ===")

/**
 * 範例 1: 通用相等性 (Multiversal Equality)
 * (Scala 3 Only)
 *
 * 在傳統 Scala (和 Java) 中，`1 == "1"` 是合法的，但結果永遠是 false。
 * 這容易隱藏 bug。Scala 3 引入了 `CanEqual` type class 來限制只有相關型別才能比較。
 */
def exampleMultiversalEquality(): Unit =
  println("--- 範例 1: 通用相等性 (Multiversal Equality) ---")

  // 定義一個簡單的 ID 類別
  // `derives CanEqual` 自動產生 CanEqual 實例，允許相同型別比較
  case class UserId(id: Int) derives CanEqual

  val user1 = UserId(1)
  val user2 = UserId(1)
  val user3 = UserId(2)

  println(s"user1 == user2: ${user1 == user2}")
  println(s"user1 == user3: ${user1 == user3}")

  // 下面的程式碼在啟用 strictEquality 後會導致編譯錯誤：
  // println(user1 == 1) 
  // 錯誤：Values of types UserId and Int cannot be compared with == or !=

  // 如果確實需要比較不同型別（例如 Int 和 Double），需要明確給出 CanEqual 實例
  given CanEqual[Int, String] = CanEqual.derived
  println(s"Int 與 String 比較 (因為有 given CanEqual): ${1 == "1"}")

/**
 * 範例 2: 參數解構 (Parameter Untupling)
 * (Scala 3 Only)
 *
 * 當處理 Tuple 的集合時 (例如 Map)，以前需要使用 `case` 來解構。
 * Scala 3 允許自動解構，讓程式碼更簡潔。
 */
def exampleParameterUntupling(): Unit =
  println("\n--- 範例 2: 參數解構 (Parameter Untupling) ---")

  val pairs = List((1, "One"), (2, "Two"), (3, "Three"))

  // Scala 2 風格 (仍可用)
  val mappedOld = pairs.map { case (num, name) => s"$num -> $name" }
  
  // Scala 3 風格：自動參數解構
  // 注意：不需要 `case`，直接寫 (num, name)
  val mappedNew = pairs.map( (num, name) => s"$num -> $name" )

  println("Scala 2 風格: " + mappedOld.mkString(", "))
  println("Scala 3 風格: " + mappedNew.mkString(", "))

/**
 * 範例 3: 行內函式 (Inline)
 * (Scala 3 Only)
 *
 * `inline` 關鍵字指示編譯器在呼叫處直接展開函式程式碼，而不是進行一般的函式呼叫。
 * 這可以消除函式呼叫的開銷，對於頻繁呼叫的小型函式很有用。
 * 它也是 Scala 3 強大的 Macro 系統 (Metaprogramming) 的基礎。
 */
def exampleInlineBasics(): Unit =
  println("\n--- 範例 3: 行內函式 (Inline) ---")

  // 定義一個行內函式
  inline def log(msg: String, level: Int): Unit =
    if level > 0 then println(s"[LOG] $msg")

  // 呼叫時，編譯器會將程式碼展開為：
  // if 1 > 0 then println(s"[LOG] 行內展開測試")
  log("行內展開測試", 1)

  // 甚至更進一步，如果是常數條件，編譯器會進行「死碼消除」(Dead Code Elimination)
  // 下面這行在編譯後，連 if 判斷都不會存在，完全被移除
  log("這行不會被印出，也不會產生執行期開銷", 0)
  
  println("行內函式已執行 (請參考原始碼說明)。")
