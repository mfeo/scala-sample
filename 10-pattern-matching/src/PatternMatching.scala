/**
 * 章節 10: 進階模式匹配
 *
 * 學習目標：
 * 1. 深入理解 match 表達式
 * 2. 掌握型別匹配
 * 3. 學習 Case Class 的解構與模式匹配
 * 4. 使用 Guard 條件進行複雜匹配
 * 5. 進行嵌套模式匹配
 * 6. 使用變數綁定
 * 7. 理解 Sealed Trait 與窮舉匹配
 */

@main def run(): Unit =
  println("=== 章節 10: 進階模式匹配 ===\n")

  example1()
  example2()
  example3()
  example4()
  example5()
  example6()
  example7()

  println("=== 章節完成 ===")

/**
 * 範例 1: 基本的 match 表達式複習
 *
 * match 是一個表達式，會回傳值。
 * 可以進行複雜的模式匹配。
 */
def example1(): Unit =
  println("--- 範例 1: 基本的 match 表達式 ---")

  def classifyNumber(num: Int): String = num match
    case 0 => "零"
    case 1 => "一"
    case 2 => "二"
    case 3 | 4 | 5 => "小數字"
    case _ => "大數字"

  println(s"0 分類為: ${classifyNumber(0)}")
  println(s"3 分類為: ${classifyNumber(3)}")
  println(s"100 分類為: ${classifyNumber(100)}")

  println()

/**
 * 範例 2: 型別匹配
 *
 * 可以根據變數的型別進行匹配。
 * 這對於處理多型物件很有用。
 */
def example2(): Unit =
  println("--- 範例 2: 型別匹配 ---")

  def describeValue(x: Any): String = x match
    case i: Int => s"整數: $i"
    case d: Double => s"浮點數: $d"
    case s: String => s"字串: $s"
    case b: Boolean => s"布林值: $b"
    case _: List[_] => "列表"
    case _: Map[_, _] => "映射"
    case _ => "其他型別"

  println(describeValue(42))
  println(describeValue(3.14))
  println(describeValue("Hello"))
  println(describeValue(true))
  println(describeValue(List(1, 2, 3)))
  println(describeValue(Map("a" -> 1)))

  println()

/**
 * 範例 3: Case Class 的模式匹配與解構
 *
 * Case Class 與模式匹配的結合是 Scala 的強大功能。
 */
def example3(): Unit =
  println("--- 範例 3: Case Class 的解構 ---")

  case class Person(name: String, age: Int, city: String)
  case class Company(name: String, employees: Int)

  def describePerson(person: Any): Unit = person match
    case Person(name, age, city) =>
      println(s"人物: 名字=$name, 年齡=$age, 城市=$city")
    case Company(name, employees) =>
      println(s"公司: 名字=$name, 員工數=$employees")
    case _ =>
      println("未知型別")

  describePerson(Person("Alice", 25, "台北"))
  describePerson(Company("TechCorp", 100))
  describePerson("Something else")

  // 在模式匹配中忽略某些欄位
  def getPersonName(person: Person): String = person match
    case Person(name, _, _) => name

  val alice = Person("Alice", 25, "台北")
  println(s"\n人名: ${getPersonName(alice)}")

  println()

/**
 * 範例 4: Guard 條件（when 子句）
 *
 * 可以在模式匹配中加入額外的條件。
 */
def example4(): Unit =
  println("--- 範例 4: Guard 條件 ---")

  def evaluateGrade(score: Int): String = score match
    case s if s >= 90 => "優秀"
    case s if s >= 80 => "良好"
    case s if s >= 70 => "合格"
    case s if s >= 60 => "及格"
    case _ => "不及格"

  println(s"95 分: ${evaluateGrade(95)}")
  println(s"85 分: ${evaluateGrade(85)}")
  println(s"75 分: ${evaluateGrade(75)}")
  println(s"65 分: ${evaluateGrade(65)}")
  println(s"50 分: ${evaluateGrade(50)}")

  // 結合型別匹配和 Guard
  def processValue(value: Any): String = value match
    case i: Int if i > 0 => s"正整數: $i"
    case i: Int if i < 0 => s"負整數: $i"
    case i: Int => "零"
    case d: Double if d > 0 => s"正浮點數: $d"
    case _ => "其他"

  println(s"\n10: ${processValue(10)}")
  println(s"-5: ${processValue(-5)}")
  println(s"3.14: ${processValue(3.14)}")

  println()

/**
 * 範例 5: 嵌套模式匹配
 *
 * 可以進行複雜的嵌套模式匹配。
 */
def example5(): Unit =
  println("--- 範例 5: 嵌套模式匹配 ---")

  case class Address(street: String, city: String)
  case class Person(name: String, address: Address)

  def describeAddress(person: Person): String = person match
    case Person(name, Address(street, city)) =>
      s"$name 住在 $city 的 $street"

  val person = Person("Alice", Address("123 Main St", "台北"))
  println(describeAddress(person))

  // 對列表的嵌套模式匹配
  def processTriple(triple: (Int, Int, Int)): String = triple match
    case (0, 0, 0) => "全零"
    case (x, 0, 0) => s"只有第一個非零: $x"
    case (0, y, 0) => s"只有第二個非零: $y"
    case (0, 0, z) => s"只有第三個非零: $z"
    case (x, y, z) => s"三個都非零: $x, $y, $z"

  println(processTriple((0, 0, 0)))
  println(processTriple((5, 0, 0)))
  println(processTriple((1, 2, 3)))

  println()

/**
 * 範例 6: 變數綁定（@ 符號）
 *
 * @ 符號允許在模式中綁定變數，同時進行嵌套匹配。
 */
def example6(): Unit =
  println("--- 範例 6: 變數綁定 ---")

  case class Point(x: Int, y: Int)

  def analyzePoint(point: Point): String = point match
    case p @ Point(0, 0) => s"原點: $p"
    case p @ Point(x, 0) => s"X 軸上的點 $p（x=$x）"
    case p @ Point(0, y) => s"Y 軸上的點 $p（y=$y）"
    case p @ Point(x, y) if x == y => s"對角線上的點 $p"
    case p => s"一般點: $p"

  println(analyzePoint(Point(0, 0)))
  println(analyzePoint(Point(5, 0)))
  println(analyzePoint(Point(0, -3)))
  println(analyzePoint(Point(3, 3)))
  println(analyzePoint(Point(2, 5)))

  println()

/**
 * 範例 7: Sealed Trait 與窮舉匹配
 *
 * Sealed Trait 限制實作只能在同一檔案中。
 * 編譯器可以檢查模式匹配是否窮舉所有情況。
 */
def example7(): Unit =
  println("--- 範例 7: Sealed Trait 與窮舉匹配 ---")

  // Sealed 表示實作只能在這個檔案中
  sealed trait TrafficLight
  case object Red extends TrafficLight
  case object Yellow extends TrafficLight
  case object Green extends TrafficLight

  def handleTrafficLight(light: TrafficLight): String = light match
    case Red => "停止"
    case Yellow => "準備"
    case Green => "通行"
    // 編譯器會檢查是否覆蓋所有情況
    // 如果刪除任何 case，會出現編譯警告

  println(s"紅燈: ${handleTrafficLight(Red)}")
  println(s"黃燈: ${handleTrafficLight(Yellow)}")
  println(s"綠燈: ${handleTrafficLight(Green)}")

  // 另一個例子：Sealed Trait 與 Case Class
  sealed trait Shape
  case class Circle(radius: Double) extends Shape
  case class Rectangle(width: Double, height: Double) extends Shape
  case class Triangle(a: Double, b: Double, c: Double) extends Shape

  def calculateArea(shape: Shape): Double = shape match
    case Circle(r) => 3.14159 * r * r
    case Rectangle(w, h) => w * h
    case Triangle(a, b, c) =>
      // Heron's formula
      val s = (a + b + c) / 2
      Math.sqrt(s * (s - a) * (s - b) * (s - c))

  println(s"\n圓形（半徑 5）的面積: ${calculateArea(Circle(5.0))}")
  println(s"矩形（寬 4，高 6）的面積: ${calculateArea(Rectangle(4.0, 6.0))}")
  println(s"三角形（邊長 3, 4, 5）的面積: ${calculateArea(Triangle(3.0, 4.0, 5.0))}")

  println()
