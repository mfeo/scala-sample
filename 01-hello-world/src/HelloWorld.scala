/**
 * 章節 01: Hello World 與基本語法
 *
 * 學習目標：
 * 1. 認識 Scala 3 的程式結構
 * 2. 使用 @main 註解定義程式入口點
 * 3. 學習基本輸出方式（println、print、printf）
 * 4. 認識字串插值的使用方法
 * 5. 了解 Scala 命令列參數的處理方式
 */

// 使用 @main 註解定義程式的主入口點
// 這是 Scala 3 推薦的方式，比傳統的 def main(args: Array[String]) 更簡潔
@main def run(args: String*): Unit =
  println("=== 章節 01: Hello World 與基本語法 ===\n")

  // 範例 1: 基本輸出
  example1()

  // 範例 2: 字串插值
  example2()

  // 範例 3: 不同的輸出方式
  example3()

  // 範例 4: 命令列參數處理
  example4(args)

  println("\n=== 章節完成 ===")

/**
 * 範例 1: 基本輸出
 *
 * println 會輸出文字後加上換行符號。這是最常用的輸出方式。
 */
def example1(): Unit =
  println("--- 範例 1: 基本輸出 ---")

  // 最基本的「Hello, World!」程式
  println("Hello, World!")

  // 也可以輸出中文
  println("你好，Scala！")

  println()

/**
 * 範例 2: 字串插值
 *
 * Scala 提供多種字串插值方式：
 * - s"..." : 簡單字串插值，可以使用 ${variable} 或 $variable
 * - f"..." : 格式化字串，支援 printf 風格的格式化
 * - raw"..." : 原始字串，不處理轉義字元
 */
def example2(): Unit =
  println("--- 範例 2: 字串插值 ---")

  // 定義一些變數用於字串插值
  val name = "Scala"
  val version = "3.3.1"
  val year = 2024

  // 使用 s 字串插值
  val message1 = s"歡迎使用 $name 版本 $version"
  println(message1)

  // 使用表達式在字串中
  val sum = s"1 + 1 = ${1 + 1}"
  println(sum)

  // 使用 f 字串進行格式化（類似 printf）
  val pi = 3.14159265
  println(f"圓周率約為: $pi%.2f")

  // 在字串中計算較複雜的表達式
  val result = s"$year 年的 $name 學習者有 ${100 * 2} 人"
  println(result)

  println()

/**
 * 範例 3: 不同的輸出方式
 *
 * Scala 提供不同的輸出方式以應對不同的需求：
 * - println : 輸出後換行
 * - print : 輸出但不換行
 * - printf : 格式化輸出（和 C 的 printf 類似）
 */
def example3(): Unit =
  println("--- 範例 3: 不同的輸出方式 ---")

  // println 會加上換行符號
  println("這是第一行")
  println("這是第二行")

  // print 不會加換行符號
  print("Hello ")
  print("World")
  println() // 最後加一個換行

  // printf 支援格式化（類似 C 的 printf）
  printf("名字: %s, 年齡: %d, 分數: %.1f\n", "小明", 25, 95.5)

  println()

/**
 * 範例 4: 命令列參數處理
 *
 * @main 函式可以接收命令列參數。
 * 使用 args: String* 來捕獲所有傳入的參數。
 *
 * 執行方式：
 *   mill 01-hello-world.run arg1 arg2 arg3
 */
def example4(args: Seq[String]): Unit =
  println("--- 範例 4: 命令列參數處理 ---")

  if args.isEmpty then
    println("沒有傳入任何命令列參數")
    println("你可以嘗試執行: mill 01-hello-world.run hello scala world")
  else
    println(s"接收到 ${args.length} 個參數:")
    // 使用 zipWithIndex 來同時遍歷索引和值
    for (arg, index) <- args.zipWithIndex do
      println(s"  參數 $index: $arg")

    // 也可以用 foreach 搭配匿名函式
    println("\n所有參數用空格連接: " + args.mkString(" "))
