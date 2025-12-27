/**
 * 章節 04: 控制流程
 *
 * 學習目標：
 * 1. 掌握 if-else 條件判斷
 * 2. 學習 while 和 do-while 迴圈
 * 3. 使用 for 迴圈和 ranges
 * 4. 引入 match 表達式基礎
 * 5. 認識 for comprehension 的基本概念
 */

@main def run(): Unit =
  println("=== 章節 04: 控制流程 ===\n")

  example1()
  example2()
  example3()
  example4()
  example5()

  println("=== 章節完成 ===")

/**
 * 範例 1: if-else 條件判斷
 *
 * if-else 是進行條件判斷的基本方式。
 * 在 Scala 中，if-else 是表達式（有回傳值），不只是語句。
 */
def example1(): Unit =
  println("--- 範例 1: if-else 條件判斷 ---")

  val score = 85

  // 基本 if-else
  if score >= 90 then
    println("等級: A")
  else if score >= 80 then
    println("等級: B")
  else if score >= 70 then
    println("等級: C")
  else
    println("等級: F")

  // if-else 是表達式，可以指派給變數
  val grade = if score >= 90 then "A"
              else if score >= 80 then "B"
              else if score >= 70 then "C"
              else "F"
  println(s"分數 $score 對應等級: $grade")

  // if 而不需 else（回傳 () 或 Unit）
  val age = 25
  if age >= 18 then
    println("成人")

  println()

/**
 * 範例 2: while 和 do-while 迴圈
 *
 * while: 先檢查條件，條件為真時執行迴圈體
 * do-while: 先執行迴圈體，再檢查條件（至少執行一次）
 */
def example2(): Unit =
  println("--- 範例 2: while 和 do-while 迴圈 ---")

  // while 迴圈：先檢查條件
  println("while 迴圈（計數 1 到 5）:")
  var count = 1
  while count <= 5 do
    println(s"  計數: $count")
    count += 1

  // do-while 迴圈：先執行再檢查
  println("\ndo-while 迴圈（計數 1 到 3）:")
  var num = 1
  do
    println(s"  計數: $num")
    num += 1
  while num <= 3

  println()

/**
 * 範例 3: for 迴圈和 ranges
 *
 * for 迴圈在 Scala 中非常靈活。
 * 可以用 ranges（範圍）、集合等進行迭代。
 */
def example3(): Unit =
  println("--- 範例 3: for 迴圈和 ranges ---")

  // 使用 range 迴圈：1 to 5（包含 5）
  println("for 迴圈（1 to 5）:")
  for i <- 1 to 5 do
    println(s"  i = $i")

  // 使用 until：1 until 5（不包含 5）
  println("\nfor 迴圈（1 until 5）:")
  for i <- 1 until 5 do
    println(s"  i = $i")

  // 使用 step 指定步長
  println("\nfor 迴圈（1 to 10 by 2）:")
  for i <- 1 to 10 by 2 do
    print(s"$i ")
  println()

  // 迴圈中使用多個變數
  println("\n迭代 range 中的每個值:")
  for i <- 1 to 3 do
    for j <- 1 to 3 do
      print(s"($i,$j) ")
  println()

  // for 迴圈也可以有初始化和 yield（後續章節會詳細說明）
  val numbers = for i <- 1 to 5 yield i * 2
  println(s"\nfor comprehension（產生新序列）: $numbers")

  println()

/**
 * 範例 4: match 表達式（模式匹配基礎）
 *
 * match 表達式是 Scala 的強大功能，類似於其他語言的 switch。
 * 但比 switch 更強大，支援多種模式匹配。
 */
def example4(): Unit =
  println("--- 範例 4: match 表達式 ---")

  val day = "Monday"

  // 基本的 match 表達式
  val dayType = day match
    case "Saturday" | "Sunday" => "週末"
    case "Monday" | "Tuesday" | "Wednesday" | "Thursday" | "Friday" => "工作日"
    case _ => "未知日期"

  println(s"$day 是 $dayType")

  // match 表達式支援值的匹配
  val number = 3
  val description = number match
    case 1 => "一"
    case 2 => "二"
    case 3 => "三"
    case 4 => "四"
    case 5 => "五"
    case _ => "其他數字"

  println(s"數字 $number 的中文表示: $description")

  // match 可以進行範圍匹配（使用 guard）
  val score = 85
  val result = score match
    case s if s >= 90 => "優秀"
    case s if s >= 80 => "良好"
    case s if s >= 70 => "合格"
    case _ => "不合格"

  println(s"分數 $score 的評價: $result")

  println()

/**
 * 範例 5: for comprehension（for 推導式）
 *
 * for comprehension 是一種強大的語法糖，用於對集合進行變換。
 * 它可以替代嵌套迴圈並以更函數式的方式產生新集合。
 */
def example5(): Unit =
  println("--- 範例 5: for comprehension ---")

  // 簡單的 for comprehension
  val numbers = 1 to 5
  val doubled = for i <- numbers yield i * 2
  println(s"原始序列: $numbers")
  println(s"乘以 2: $doubled")

  // 有篩選條件的 for comprehension
  val evens = for i <- 1 to 10 if i % 2 == 0 yield i
  println(s"\n1 到 10 的偶數: $evens")

  // 多個迭代器的 for comprehension
  val pairs = for
    i <- 1 to 3
    j <- 1 to 3
  yield (i, j)
  println(s"\n1 到 3 的所有配對: $pairs")

  // 更複雜的例子：產生乘法表
  println("\n2 到 4 的乘法表:")
  for
    i <- 2 to 4
    j <- 2 to 4
  do
    println(s"  $i × $j = ${i * j}")

  println()
