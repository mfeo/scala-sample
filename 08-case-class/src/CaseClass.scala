/**
 * 章節 08: Case Class 與模式匹配基礎
 *
 * 學習目標：
 * 1. 認識 Case Class 的定義與特性
 * 2. 理解自動產生的方法（apply、copy、equals、hashCode、toString）
 * 3. 學習 Case Class 與模式匹配的結合
 * 4. 理解不可變資料結構的重要性
 * 5. 掌握參數預設值的使用
 * 6. 認識 Case Class 與普通 class 的區別
 */

@main def run(): Unit =
  println("=== 章節 08: Case Class 與模式匹配基礎 ===\n")

  example1()
  example2()
  example3()
  example4()
  example5()
  example6()

  println("=== 章節完成 ===")

/**
 * 範例 1: Case Class 的定義與基本特性
 *
 * Case Class 自動為我們生成許多有用的方法。
 * 定義 case class 非常簡潔。
 */
def example1(): Unit =
  println("--- 範例 1: Case Class 的定義與基本特性 ---")

  // 定義一個 Case Class
  case class Person(name: String, age: Int, city: String)

  // 建立實例（不需要 new 關鍵字）
  val alice = Person("Alice", 25, "台北")
  val bob = Person("Bob", 30, "台中")

  println(s"alice = $alice")
  println(s"bob = $bob")

  // Case Class 自動產生了 toString 方法（友好的輸出）
  println(s"alice.toString() = ${alice.toString()}")

  // 訪問欄位
  println(s"alice.name = ${alice.name}")
  println(s"alice.age = ${alice.age}")
  println(s"alice.city = ${alice.city}")

  // Case Class 自動產生了 equals 方法（值相等）
  val alice2 = Person("Alice", 25, "台北")
  println(s"alice == alice2: ${alice == alice2}")  // true，因為值相同

  // Case Class 自動產生了 hashCode 方法
  println(s"alice.hashCode() = ${alice.hashCode()}")
  println(s"alice2.hashCode() = ${alice2.hashCode()}")

  println()

/**
 * 範例 2: copy 方法（建立修改後的副本）
 *
 * Case Class 的 copy 方法允許我們建立一個只修改了某些欄位的副本。
 */
def example2(): Unit =
  println("--- 範例 2: copy 方法 ---")

  case class User(name: String, email: String, age: Int)

  val user1 = User("Alice", "alice@example.com", 25)
  println(s"原始使用者: $user1")

  // 使用 copy 修改某個欄位
  val user2 = user1.copy(age = 26)
  println(s"修改年齡: $user2")

  // 修改多個欄位
  val user3 = user1.copy(name = "Alice Smith", age = 30)
  println(s"修改名字和年齡: $user3")

  // 不修改任何欄位（建立相同的副本）
  val user4 = user1.copy()
  println(s"完整副本: $user4")
  println(s"user1 == user4: ${user1 == user4}")  // true，但是不同的物件

  println()

/**
 * 範例 3: Case Class 與模式匹配
 *
 * Case Class 與模式匹配結合得非常好。
 * 可以在 match 表達式中輕鬆地解構 Case Class。
 */
def example3(): Unit =
  println("--- 範例 3: Case Class 與模式匹配 ---")

  case class Point(x: Int, y: Int)

  val point = Point(3, 4)
  println(s"point = $point")

  // 使用模式匹配解構 Case Class
  point match
    case Point(0, 0) => println("原點")
    case Point(0, y) => println(s"在 Y 軸上，y = $y")
    case Point(x, 0) => println(s"在 X 軸上，x = $x")
    case Point(x, y) =>
      println(s"一般點: ($x, $y)")
      val distance = Math.sqrt(x * x + y * y)
      println(s"距離原點: $distance")

  println()

/**
 * 範例 4: 複雜的 Case Class
 *
 * Case Class 可以包含複雜的欄位型別，甚至可以互相嵌入。
 */
def example4(): Unit =
  println("--- 範例 4: 複雜的 Case Class ---")

  // 定義一個簡單的 Case Class
  case class Address(street: String, city: String, zip: String)

  // 定義包含另一個 Case Class 的 Case Class
  case class Employee(name: String, id: Int, address: Address, salary: Double)

  val emp = Employee(
    "Alice",
    1001,
    Address("123 Main St", "台北", "10001"),
    60000.0
  )

  println(s"員工: $emp")
  println(s"員工名字: ${emp.name}")
  println(s"員工城市: ${emp.address.city}")

  // 使用 copy 修改嵌入的資料
  val newEmp = emp.copy(
    salary = 65000.0,
    address = emp.address.copy(city = "台中")
  )
  println(s"修改後的員工: $newEmp")

  println()

/**
 * 範例 5: 參數預設值
 *
 * Case Class 的參數可以有預設值。
 */
def example5(): Unit =
  println("--- 範例 5: 參數預設值 ---")

  case class Config(
      host: String,
      port: Int = 8080,
      debug: Boolean = false,
      maxConnections: Int = 100
  )

  // 使用所有預設值
  val config1 = Config("localhost")
  println(s"config1 = $config1")

  // 指定部分參數
  val config2 = Config("192.168.1.1", 9000)
  println(s"config2 = $config2")

  // 使用具名參數
  val config3 = Config(
    host = "example.com",
    debug = true,
    maxConnections = 200
  )
  println(s"config3 = $config3")

  println()

/**
 * 範例 6: Case Class 與普通 class 的區別
 *
 * Case Class 比普通 class 自動產生更多有用的方法。
 */
def example6(): Unit =
  println("--- 範例 6: Case Class vs 普通 class ---")

  // 普通 class（需要手動實作許多方法）
  class RegularPoint(val x: Int, val y: Int):
    override def toString(): String =
      s"RegularPoint($x, $y)"

  // Case Class（自動產生許多方法）
  case class CasePoint(x: Int, y: Int)

  val regular1 = new RegularPoint(1, 2)
  val regular2 = new RegularPoint(1, 2)

  println(s"普通 class:")
  println(s"  regular1 = $regular1")
  println(s"  regular2 = $regular2")
  println(s"  regular1 == regular2: ${regular1 == regular2}")  // false（比較的是參考）
  println(s"  regular1 eq regular2: ${regular1 eq regular2}")  // false

  val case1 = CasePoint(1, 2)
  val case2 = CasePoint(1, 2)

  println(s"\nCase Class:")
  println(s"  case1 = $case1")
  println(s"  case2 = $case2")
  println(s"  case1 == case2: ${case1 == case2}")  // true（比較的是值）
  println(s"  case1 eq case2: ${case1 eq case2}")  // false（不同的物件）

  println("\nCase Class 的優勢:")
  println("  ✓ 自動產生 equals 和 hashCode")
  println("  ✓ 自動產生 toString")
  println("  ✓ 自動產生 copy 方法")
  println("  ✓ 可以用於模式匹配")
  println("  ✓ 參數自動成為 val")
  println("  ✓ 定義更簡潔")

  println()
