package metaprogramming

import metaprogramming.Macros.*

@main def runMetaprogramming(): Unit =
  println("=== 章節 20: 元程式設計 (Scala 3 Macros) ===\n")

  exampleDebugMacro()
  exampleCodeGeneration()
  exampleCompileTimeOperations()
  exampleScala2Comparison()

  println("=== 章節完成 ===")

/**
 * 範例 1: Debug Macro
 */
def exampleDebugMacro(): Unit =
  println("--- 範例 1: Debug Macro ---")

  val x = 10
  
  // 使用 Macro
  debug(x)
  debug(x * 2)
  debug("Hello " + "World")
  
  println("(注意：上述輸出中的變數名稱或運算式是編譯期取得的)")

/**
 * 範例 2: 簡單的程式碼生成 (Code Generation)
 */
def exampleCodeGeneration(): Unit =
  println("\n--- 範例 2: 程式碼生成 ---")
  
  // 呼叫 macro
  inspectType[Int]
  inspectType[String]
  inspectType[List[Int]]

/**
 * 範例 3: Compile-time Operations（編譯期操作）與 Quoted Pattern（引號模式）
 */
def exampleCompileTimeOperations(): Unit =
  println("\n--- 範例 3: 編譯期操作與引號模式 ---")

  val left = 1
  val right = 2
  println(s"Compile-time validation: ${requireNonNegative(0)}")
  println(s"Quoted pattern detects addition: ${isAddition(left + right)}")
  println(s"Quoted pattern rejects other expressions: ${isAddition(3 * 4)}")
  println(s"Mirror product derivation: ${FieldCount[AuditEvent]}")
  println(s"Mirror sum derivation: ${FieldCount[Command]}")

/**
 * 範例 4: Scala 2 與 Scala 3 Macro 對照 (僅供參考)
 *
 * 由於 Scala 2 Macro 依賴 `scala-reflect` 且 API 完全不同，無法在 Scala 3 專案中直接執行。
 * 以下展示若在 Scala 2 中實作相同的 `debug` macro 該如何寫。
 */
def exampleScala2Comparison(): Unit =
  println("\n--- 範例 4: Scala 2 Macro 對照 (Reference Only) ---")
  
  val scala2Code = """
  // ==========================================
  // Scala 2 風格 (Legacy)
  // ==========================================
  import scala.language.experimental.macros
  import scala.reflect.macros.blackbox.Context

  object MacrosScala2 {
    def debug(x: Any): Unit = macro debugImpl

    def debugImpl(c: Context)(x: c.Expr[Any]): c.Expr[Unit] = {
      import c.universe._
      
      // 1. 取得原始碼字串 (Scala 2 較困難，通常只能拿到樹狀結構的字串表示)
      val paramRep = show(x.tree)
      val paramRepTree = Literal(Constant(paramRep))
      
      // 2. 使用 reify 建構 AST
      reify {
        println("Value of " + c.Expr[String](paramRepTree).splice + " is: " + x.splice)
      }
    }
  }
  """
  
  println(scala2Code)
  println("主要差異：")
  println("1. Scala 3 使用 `inline` + `quoted` ('{...})，語法更接近普通 Scala 程式碼。")
  println("2. Scala 2 使用 `Context` + `Universe` (AST)，API 非常複雜且依賴編譯器內部實作。")
  println("3. Scala 3 Macro 提供分層且有型別的 API；實際編譯成本取決於巨集的工作量與展開大小。")
