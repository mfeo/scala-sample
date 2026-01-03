/**
 * 章節 13: 函數式程式設計 (Functional Programming)
 *
 * 學習目標：
 * 1. 理解純函式 (Pure Functions) 的概念與優勢
 * 2. 掌握不可變性 (Immutability) 的重要性
 * 3. 學習函數組合 (Function Composition)
 * 4. 深入理解柯里化與部分應用 (Currying & Partial Application)
 * 5. 認識惰性求值 (Lazy Evaluation)
 * 6. 掌握尾遞迴最佳化 (Tail Recursion)
 * 7. 了解常見的 FP 設計模式
 */

import scala.annotation.tailrec

@main def run(): Unit =
  println("=== 章節 13: 函數式程式設計 ===\n")

  example1()
  example2()
  example3()
  example4()
  example5()
  example6()
  example7()

  println("=== 章節完成 ===")

/**
 * 範例 1: 純函式 (Pure Functions)
 *
 * 純函式的特性：
 * 1. 相同輸入永遠產生相同輸出（確定性）
 * 2. 沒有副作用（不修改外部狀態）
 * 3. 引用透明性（可以用結果替換函式呼叫）
 */
def example1(): Unit =
  println("--- 範例 1: 純函式 ---")

  // 純函式範例：相同輸入永遠得到相同輸出
  def add(a: Int, b: Int): Int = a + b
  def multiply(a: Int, b: Int): Int = a * b

  println(s"add(2, 3) = ${add(2, 3)}")
  println(s"add(2, 3) = ${add(2, 3)}")  // 永遠是 5

  // 非純函式範例 1：依賴外部狀態
  var counter = 0
  def impureIncrement(): Int =
    counter += 1  // 副作用：修改外部變數
    counter

  println(s"\n非純函式（依賴外部狀態）:")
  println(s"impureIncrement() = ${impureIncrement()}")  // 1
  println(s"impureIncrement() = ${impureIncrement()}")  // 2（不同結果！）

  // 純函式版本：將狀態作為參數傳入
  def pureIncrement(n: Int): Int = n + 1

  println(s"\n純函式版本:")
  println(s"pureIncrement(0) = ${pureIncrement(0)}")  // 永遠是 1
  println(s"pureIncrement(0) = ${pureIncrement(0)}")  // 永遠是 1

  // 引用透明性範例
  val x = add(2, 3)  // x = 5
  val result1 = x + x  // 可以替換為 add(2, 3) + add(2, 3)
  val result2 = add(2, 3) + add(2, 3)  // 結果相同
  println(s"\n引用透明性: $result1 == $result2 => ${result1 == result2}")

  println()

/**
 * 範例 2: 不可變性 (Immutability)
 *
 * FP 強調使用不可變的資料結構：
 * - 更容易推理程式行為
 * - 天然支援並行處理
 * - 避免意外的狀態修改
 */
def example2(): Unit =
  println("--- 範例 2: 不可變性 ---")

  // val vs var
  val immutableValue = 10
  // immutableValue = 20  // 編譯錯誤！

  var mutableValue = 10
  mutableValue = 20  // 可以修改
  println(s"var 可以修改: mutableValue = $mutableValue")

  // 不可變集合
  val list1 = List(1, 2, 3)
  val list2 = list1 :+ 4  // 建立新的 List，原本的不變
  println(s"\n不可變 List:")
  println(s"原始 list1 = $list1")
  println(s"新的 list2 = $list2")

  // 使用 case class 的 copy 方法
  case class Person(name: String, age: Int)

  val alice = Person("Alice", 25)
  val olderAlice = alice.copy(age = 26)  // 建立新物件
  println(s"\n使用 copy 方法:")
  println(s"原始: $alice")
  println(s"修改後: $olderAlice")

  // 不可變的資料轉換鏈
  val numbers = List(1, 2, 3, 4, 5)
  val result = numbers
    .map(_ * 2)      // List(2, 4, 6, 8, 10)
    .filter(_ > 5)   // List(6, 8, 10)
    .sum             // 24
  println(s"\n轉換鏈（每步都產生新集合）:")
  println(s"原始: $numbers")
  println(s"結果: $result")

  println()

/**
 * 範例 3: 函數組合 (Function Composition)
 *
 * 函數組合是將小函式組合成大函式的技術。
 * Scala 提供 andThen 和 compose 方法。
 */
def example3(): Unit =
  println("--- 範例 3: 函數組合 ---")

  // 定義簡單的函式
  val addOne: Int => Int = _ + 1
  val double: Int => Int = _ * 2
  val square: Int => Int = x => x * x

  // 使用 andThen：從左到右執行
  val addOneThenDouble = addOne.andThen(double)
  println(s"addOne.andThen(double)(5) = ${addOneThenDouble(5)}")  // (5+1)*2 = 12

  // 使用 compose：從右到左執行
  val doubleComposeAddOne = double.compose(addOne)
  println(s"double.compose(addOne)(5) = ${doubleComposeAddOne(5)}")  // (5+1)*2 = 12

  // 組合多個函式
  val pipeline = addOne.andThen(double).andThen(square)
  println(s"\n多重組合 (addOne -> double -> square):")
  println(s"pipeline(3) = ${pipeline(3)}")  // ((3+1)*2)^2 = 64

  // 實際應用：字串處理管道
  val trim: String => String = _.trim
  val toUpper: String => String = _.toUpperCase
  val addBrackets: String => String = s => s"[$s]"

  val formatString = trim.andThen(toUpper).andThen(addBrackets)
  println(s"\n字串處理管道:")
  println(s"formatString(\"  hello  \") = ${formatString("  hello  ")}")

  // 使用 pipe 風格（Scala 3 的 extension method）
  extension [A](a: A)
    def |>[B](f: A => B): B = f(a)

  val result = 5 |> addOne |> double |> square
  println(s"\n使用 pipe 運算子: 5 |> addOne |> double |> square = $result")

  println()

/**
 * 範例 4: 柯里化與部分應用 (Currying & Partial Application)
 *
 * 柯里化：將多參數函式轉換為一系列單參數函式
 * 部分應用：固定部分參數，產生新的函式
 */
def example4(): Unit =
  println("--- 範例 4: 柯里化與部分應用 ---")

  // 普通多參數函式
  def add(a: Int, b: Int): Int = a + b

  // 柯里化版本（多參數列表）
  def curriedAdd(a: Int)(b: Int): Int = a + b

  println(s"add(2, 3) = ${add(2, 3)}")
  println(s"curriedAdd(2)(3) = ${curriedAdd(2)(3)}")

  // 部分應用：固定第一個參數
  val add5 = curriedAdd(5)
  println(s"\n部分應用 curriedAdd(5):")
  println(s"add5(10) = ${add5(10)}")
  println(s"add5(20) = ${add5(20)}")

  // 將普通函式轉換為柯里化版本
  val curriedAddAlt = (add _).curried
  val add10 = curriedAddAlt(10)
  println(s"\n轉換為柯里化: add10(5) = ${add10(5)}")

  // 實際應用：建立特定格式化函式
  def formatMessage(prefix: String)(message: String)(suffix: String): String =
    s"$prefix $message $suffix"

  val errorFormat = formatMessage("[ERROR]")(_: String)("!!!")
  val infoFormat = formatMessage("[INFO]")(_: String)(".")

  println(s"\n格式化函式:")
  println(errorFormat("Something went wrong"))
  println(infoFormat("Process completed"))

  // 柯里化與型別推斷
  def transform[A, B](list: List[A])(f: A => B): List[B] = list.map(f)

  val numbers = List(1, 2, 3, 4, 5)
  val doubled = transform(numbers)(_ * 2)  // 型別自動推斷
  println(s"\n柯里化幫助型別推斷:")
  println(s"transform(numbers)(_ * 2) = $doubled")

  println()

/**
 * 範例 5: 惰性求值 (Lazy Evaluation)
 *
 * 惰性求值：延遲計算直到真正需要結果時才執行
 * - lazy val：延遲初始化變數
 * - LazyList：延遲求值的串流
 */
def example5(): Unit =
  println("--- 範例 5: 惰性求值 ---")

  // lazy val：延遲計算直到第一次存取
  println("定義 lazy val:")
  lazy val expensiveComputation =
    println("  [執行昂貴計算...]")
    42

  println("lazy val 已定義，但尚未執行")
  println(s"第一次存取: ${expensiveComputation}")  // 這時才執行
  println(s"第二次存取: ${expensiveComputation}")  // 使用快取的值

  // LazyList（無限序列）
  println("\nLazyList（無限序列）:")

  // 定義自然數的無限序列
  def naturals(n: Int): LazyList[Int] = n #:: naturals(n + 1)
  val nums = naturals(1)

  println(s"前 10 個自然數: ${nums.take(10).toList}")

  // 費波那契數列（惰性版本）
  lazy val fibs: LazyList[Int] = 0 #:: 1 #:: fibs.zip(fibs.tail).map(_ + _)
  println(s"前 10 個費波那契數: ${fibs.take(10).toList}")

  // 短路求值
  println("\n短路求值:")
  def expensiveCheck(): Boolean =
    println("  [執行昂貴檢查]")
    true

  val result1 = false && expensiveCheck()  // expensiveCheck 不會執行
  println(s"false && expensiveCheck() = $result1")

  val result2 = true || expensiveCheck()  // expensiveCheck 不會執行
  println(s"true || expensiveCheck() = $result2")

  // 使用 Option 的惰性操作
  val maybeValue: Option[Int] = Some(10)
  val lazyResult = maybeValue.map { v =>
    println(s"  [處理值: $v]")
    v * 2
  }
  println(s"\nOption.map 的惰性特性:")
  println(s"結果: $lazyResult")

  println()

/**
 * 範例 6: 尾遞迴 (Tail Recursion)
 *
 * 尾遞迴：遞迴呼叫是函式的最後一個動作
 * Scala 編譯器可以將尾遞迴最佳化為迴圈，避免堆疊溢位
 */
def example6(): Unit =
  println("--- 範例 6: 尾遞迴 ---")

  // 非尾遞迴的階乘（會堆疊溢位）
  def factorialNonTail(n: Int): BigInt =
    if n <= 1 then 1
    else n * factorialNonTail(n - 1)  // 乘法在遞迴之後，不是尾遞迴

  // 尾遞迴的階乘
  @tailrec
  def factorialTail(n: Int, acc: BigInt = 1): BigInt =
    if n <= 1 then acc
    else factorialTail(n - 1, n * acc)  // 遞迴是最後一個動作

  println(s"factorialNonTail(10) = ${factorialNonTail(10)}")
  println(s"factorialTail(10) = ${factorialTail(10)}")
  println(s"factorialTail(1000) = ${factorialTail(1000).toString.take(20)}...（可處理大數）")

  // 尾遞迴的列表求和
  @tailrec
  def sumList(list: List[Int], acc: Int = 0): Int =
    list match
      case Nil => acc
      case head :: tail => sumList(tail, acc + head)

  val numbers = (1 to 100).toList
  println(s"\nsumList(1 to 100) = ${sumList(numbers)}")

  // 尾遞迴的列表反轉
  @tailrec
  def reverseList[A](list: List[A], acc: List[A] = Nil): List[A] =
    list match
      case Nil => acc
      case head :: tail => reverseList(tail, head :: acc)

  println(s"reverseList(List(1,2,3,4,5)) = ${reverseList(List(1, 2, 3, 4, 5))}")

  // 尾遞迴的費波那契
  @tailrec
  def fibonacci(n: Int, a: BigInt = 0, b: BigInt = 1): BigInt =
    if n <= 0 then a
    else fibonacci(n - 1, b, a + b)

  println(s"\nfibonacci(50) = ${fibonacci(50)}")

  println()

/**
 * 範例 7: 常見 FP 模式
 *
 * 展示函數式程式設計中的常見模式與技巧
 */
def example7(): Unit =
  println("--- 範例 7: 常見 FP 模式 ---")

  // 1. Railway Oriented Programming（軌道導向程式設計）
  println("1. Railway 模式（使用 Either 鏈接操作）:")

  def parseNumber(s: String): Either[String, Int] =
    s.toIntOption.toRight(s"無法解析 '$s' 為數字")

  def validatePositive(n: Int): Either[String, Int] =
    if n > 0 then Right(n) else Left(s"$n 不是正數")

  def validateEven(n: Int): Either[String, Int] =
    if n % 2 == 0 then Right(n) else Left(s"$n 不是偶數")

  // 使用 for comprehension 鏈接操作
  def processNumber(input: String): Either[String, Int] =
    for
      num <- parseNumber(input)
      positive <- validatePositive(num)
      even <- validateEven(positive)
    yield even * 2

  println(s"  processNumber(\"4\") = ${processNumber("4")}")
  println(s"  processNumber(\"3\") = ${processNumber("3")}")
  println(s"  processNumber(\"-2\") = ${processNumber("-2")}")
  println(s"  processNumber(\"abc\") = ${processNumber("abc")}")

  // 2. 函數式的資料轉換
  println("\n2. 函數式資料轉換:")

  case class Order(id: Int, items: List[String], total: Double)
  val orders = List(
    Order(1, List("書", "筆"), 150.0),
    Order(2, List("電腦"), 30000.0),
    Order(3, List("咖啡", "蛋糕"), 200.0)
  )

  val expensiveOrderItems = orders
    .filter(_.total > 100)
    .flatMap(_.items)
    .distinct

  println(s"  高價訂單的商品: $expensiveOrderItems")

  // 3. 使用 fold 實現複雜聚合
  println("\n3. 使用 fold 進行複雜聚合:")

  val words = List("apple", "banana", "cherry", "date")
  val wordStats = words.foldLeft(Map.empty[Char, Int]) { (acc, word) =>
    val firstChar = word.head
    acc.updated(firstChar, acc.getOrElse(firstChar, 0) + 1)
  }
  println(s"  首字母統計: $wordStats")

  // 4. 組合子模式
  println("\n4. 組合子模式（Combinator Pattern）:")

  type Predicate[A] = A => Boolean

  extension [A](p: Predicate[A])
    def and(other: Predicate[A]): Predicate[A] = a => p(a) && other(a)
    def or(other: Predicate[A]): Predicate[A] = a => p(a) || other(a)
    def not: Predicate[A] = a => !p(a)

  val isPositive: Predicate[Int] = _ > 0
  val isEven: Predicate[Int] = _ % 2 == 0
  val isSmall: Predicate[Int] = _ < 100

  val isPositiveEven = isPositive.and(isEven)
  val isPositiveEvenOrSmall = isPositiveEven.or(isSmall)

  val testNumbers = List(-10, -3, 0, 5, 10, 50, 100, 200)
  println(s"  測試數字: $testNumbers")
  println(s"  正偶數: ${testNumbers.filter(isPositiveEven)}")
  println(s"  正偶數或小於100: ${testNumbers.filter(isPositiveEvenOrSmall)}")

  // 總結
  println("\n--- FP 核心原則總結 ---")
  println("1. 優先使用純函式（無副作用）")
  println("2. 使用不可變資料結構")
  println("3. 使用函數組合建構複雜邏輯")
  println("4. 利用高階函式抽象共同模式")
  println("5. 使用惰性求值處理大型或無限資料")
  println("6. 使用尾遞迴避免堆疊溢位")

  println()
