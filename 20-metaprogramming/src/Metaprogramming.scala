package metaprogramming

import metaprogramming.Macros.*

@main def runMetaprogramming(): Unit =
  println("=== 章節 20: 元程式設計 (Scala 3 Macros) ===\n")

  exampleDebugMacro()
  exampleCodeGeneration()
  exampleCompileTimeOperations()

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
