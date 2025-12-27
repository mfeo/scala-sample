/**
 * 章節 06: 集合（List、Array、Seq）
 *
 * 學習目標：
 * 1. 認識 Scala 的主要集合型別
 * 2. 掌握 List（不可變列表）的使用
 * 3. 學習 Array（可變陣列）的使用
 * 4. 認識 Seq 抽象和 Vector
 * 5. 掌握常用的集合操作（map、filter、fold 等）
 * 6. 理解不同集合型別的應用場景
 */

@main def run(): Unit =
  println("=== 章節 06: 集合 ===\n")

  example1()
  example2()
  example3()
  example4()
  example5()
  example6()

  println("=== 章節完成 ===")

/**
 * 範例 1: List（不可變列表）
 *
 * List 是 Scala 中最常用的集合型別。
 * 它是不可變的，適合函數式程式設計。
 */
def example1(): Unit =
  println("--- 範例 1: List（不可變列表） ---")

  // 建立 List
  val numbers = List(1, 2, 3, 4, 5)
  println(s"numbers = $numbers")

  // 建立空 List
  val emptyList: List[Int] = List()
  println(s"emptyList = $emptyList")

  // 使用 :: 操作符（cons）構建 List
  val list2 = 1 :: 2 :: 3 :: Nil
  println(s"list2 = $list2")

  // List 是不可變的，所以無法直接修改
  // numbers(0) = 10  // 編譯錯誤
  // 需要使用新的 List（通常包含修改後的元素）
  val newList = numbers.updated(0, 10)
  println(s"修改第一個元素: $newList")

  // List 的常用操作
  println(s"head（第一個元素）: ${numbers.head}")
  println(s"tail（除了第一個的其他元素）: ${numbers.tail}")
  println(s"length（長度）: ${numbers.length}")
  println(s"reverse（反轉）: ${numbers.reverse}")

  println()

/**
 * 範例 2: Array（可變陣列）
 *
 * Array 是可變的、固定大小的集合。
 * 類似於 Java 的陣列。
 */
def example2(): Unit =
  println("--- 範例 2: Array（可變陣列） ---")

  // 建立 Array
  val arr = Array(1, 2, 3, 4, 5)
  println(s"arr = ${arr.mkString("[", ", ", "]")}")

  // 建立指定大小的 Array（初始化為預設值）
  val emptyArr = new Array[Int](5)
  println(s"emptyArr（大小 5） = ${emptyArr.mkString("[", ", ", "]")}")

  // 訪問和修改元素
  println(s"arr(0) = ${arr(0)}")
  arr(0) = 10
  println(s"修改後 arr(0) = ${arr(0)}")

  // Array 的常用操作
  println(s"length = ${arr.length}")
  println(s"reverse = ${arr.reverse.mkString("[", ", ", "]")}")
  println(s"sorted = ${arr.sorted.mkString("[", ", ", "]")}")

  println()

/**
 * 範例 3: Seq 和 Vector
 *
 * Seq 是序列的抽象型別。
 * Vector 是一種高效的不可變序列，支援快速的隨機存取。
 */
def example3(): Unit =
  println("--- 範例 3: Seq 和 Vector ---")

  // List 是 Seq 的一種實現
  val seqFromList: Seq[Int] = List(1, 2, 3, 4, 5)
  println(s"seqFromList = $seqFromList")

  // Vector 是另一種 Seq 的實現，適合大序列
  val vector = Vector(10, 20, 30, 40, 50)
  println(s"vector = $vector")

  // Seq 的多型特性
  def processSeq(seq: Seq[Int]): Unit =
    println(s"  Seq 內容: ${seq.mkString(", ")}")
    println(s"  長度: ${seq.length}")
    println(s"  第一個元素: ${seq.head}")

  println("List 作為 Seq 處理:")
  processSeq(List(1, 2, 3))

  println("Vector 作為 Seq 處理:")
  processSeq(Vector(4, 5, 6))

  println()

/**
 * 範例 4: 集合的映射和篩選
 *
 * map 和 filter 是集合操作中最常用的方法。
 * 它們支援函數式程式設計風格。
 */
def example4(): Unit =
  println("--- 範例 4: 映射和篩選 ---")

  val numbers = List(1, 2, 3, 4, 5)
  println(s"原始列表: $numbers")

  // map：變換集合中的每個元素
  val doubled = numbers.map(_ * 2)
  println(s"乘以 2: $doubled")

  val squared = numbers.map(x => x * x)
  println(s"平方: $squared")

  // filter：保留滿足條件的元素
  val evens = numbers.filter(_ % 2 == 0)
  println(s"偶數: $evens")

  val greaterThan3 = numbers.filter(_ > 3)
  println(s"大於 3: $greaterThan3")

  // 結合 map 和 filter
  val result = numbers
    .filter(_ % 2 == 0)  // 先篩選偶數
    .map(_ * 10)         // 然後乘以 10
  println(s"偶數乘以 10: $result")

  println()

/**
 * 範例 5: reduce 和 fold
 *
 * reduce 和 fold 用於將集合的所有元素縮減為單一值。
 */
def example5(): Unit =
  println("--- 範例 5: reduce 和 fold ---")

  val numbers = List(1, 2, 3, 4, 5)
  println(s"列表: $numbers")

  // reduce：從第一個元素開始累積
  val sum = numbers.reduce((a, b) => a + b)
  println(s"reduce 求和: $sum")

  val product = numbers.reduce(_ * _)
  println(s"reduce 求積: $product")

  // fold：指定初始值然後累積
  val sumWithInitial = numbers.fold(0)(_ + _)
  println(s"fold 求和（初始值 0）: $sumWithInitial")

  val sumWithInitial100 = numbers.fold(100)(_ + _)
  println(s"fold 求和（初始值 100）: $sumWithInitial100")

  // foldLeft 和 foldRight
  val leftFold = numbers.foldLeft(0)((acc, x) => acc - x)
  println(s"foldLeft（0 - 1 - 2 - ... ）: $leftFold")

  val rightFold = numbers.foldRight(0)((x, acc) => x - acc)
  println(s"foldRight（... - (- 5) - (- 4) - ... ）: $rightFold")

  println()

/**
 * 範例 6: 其他常用集合操作
 *
 * 展示 Scala 集合庫中的其他有用的操作方法。
 */
def example6(): Unit =
  println("--- 範例 6: 其他常用操作 ---")

  val numbers = List(1, 2, 3, 4, 5)
  println(s"列表: $numbers")

  // foreach：對每個元素執行操作（副作用）
  print("foreach 逐個輸出: ")
  numbers.foreach(x => print(s"$x "))
  println()

  // find：找到第一個滿足條件的元素
  val firstEven = numbers.find(_ % 2 == 0)
  println(s"第一個偶數: $firstEven")

  // exists：檢查是否存在滿足條件的元素
  val hasEven = numbers.exists(_ % 2 == 0)
  println(s"是否存在偶數: $hasEven")

  // forall：檢查是否所有元素都滿足條件
  val allPositive = numbers.forall(_ > 0)
  println(s"是否都是正數: $allPositive")

  // take 和 drop
  println(s"take(3)（前 3 個）: ${numbers.take(3)}")
  println(s"drop(3)（跳過前 3 個）: ${numbers.drop(3)}")

  // zip：將兩個列表配對
  val letters = List("a", "b", "c", "d", "e")
  val zipped = numbers.zip(letters)
  println(s"zip: $zipped")

  // mkString：將列表轉換為字串
  val joined = numbers.mkString(" - ")
  println(s"mkString: $joined")

  println()
