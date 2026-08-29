/**
 * 章節 09: Trait 與 Companion Object
 *
 * 學習目標：
 * 1. 認識 Trait 的定義與實作
 * 2. 理解抽象方法與具體方法
 * 3. 學習 Trait 混入（Mixin）
 * 4. 掌握 Companion Object 的概念
 * 5. 理解 apply 和 unapply 方法
 * 6. 實踐工廠模式
 */

@main def run(): Unit =
  println("=== 章節 09: Trait 與 Companion Object ===\n")

  example1()
  example2()
  example3()
  example4()
  example5()
  example6()

  println("=== 章節完成 ===")

/**
 * 範例 1: Trait 的定義與實作
 *
 * Trait 是 Scala 中定義介面和混入功能的方式。
 * 一個 class 可以混入多個 Trait。
 */
def example1(): Unit =
  println("--- 範例 1: Trait 的定義與實作 ---")

  // 定義一個 Trait（抽象方法）
  trait Animal:
    def name: String
    def makeSound(): Unit
    def describe(): Unit =
      println(s"這是一隻 $name")

  // 定義一個實作 Trait 的 Class
  class Dog(val name: String) extends Animal:
    def makeSound(): Unit =
      println(s"$name 汪汪！")

  class Cat(val name: String) extends Animal:
    def makeSound(): Unit =
      println(s"$name 喵喵！")

  // 使用
  val dog = new Dog("旺財")
  val cat = new Cat("咪咪")

  dog.describe()
  dog.makeSound()

  cat.describe()
  cat.makeSound()

  println()

/**
 * 範例 2: Trait 混入（Mixin）
 *
 * Scala 支援多重 Trait 混入，實現多重繼承的功能。
 */
def example2(): Unit =
  println("--- 範例 2: Trait 混入 ---")

  // 定義多個 Trait
  trait Swimmer:
    def swim(): Unit =
      println("游泳中...")

  trait Flyer:
    def fly(): Unit =
      println("飛行中...")

  // Duck 同時混入兩個 Trait
  class Duck(name: String) extends Swimmer with Flyer:
    def describe(): Unit =
      println(s"$name 是一隻鴨子")

  val duck = new Duck("唐老鴨")
  duck.describe()
  duck.swim()
  duck.fly()

  println()

/**
 * 範例 3: 抽象方法與具體方法
 *
 * Trait 可以包含抽象方法（子類別必須實作）和具體方法（有實作）。
 */
def example3(): Unit =
  println("--- 範例 3: 抽象方法與具體方法 ---")

  trait Shape:
    // 抽象方法（沒有實作）
    def area(): Double

    // 具體方法（有實作，但可以被重寫）
    def describe(): String =
      f"面積: ${area()}%.2f"

    def printInfo(): Unit =
      println(describe())

  case class Circle(radius: Double) extends Shape:
    def area(): Double =
      3.14159 * radius * radius

  case class Square(side: Double) extends Shape:
    def area(): Double =
      side * side

  val circle = Circle(5.0)
  val square = Square(4.0)

  circle.printInfo()
  square.printInfo()

  println()

/**
 * 範例 4: Companion Object（伴生物件）
 *
 * Companion Object 是與 class 同名的 object。
 * 它可以訪問該 class 的私有成員。
 * 通常用於定義工廠方法和靜態方法。
 */
def example4(): Unit =
  println("--- 範例 4: Companion Object ---")

  // 定義 class
  class Person private(val name: String, val age: Int):
    override def toString(): String =
      s"Person($name, $age)"

  // Companion Object（與 class 同名）
  object Person:
    // 工廠方法
    def apply(name: String, age: Int): Person =
      new Person(name, age)

    def fromString(str: String): Option[Person] =
      val parts = str.split(",")
      if parts.length == 2 then
        try
          Some(Person(parts(0).trim, parts(1).trim.toInt))
        catch
          case _ => None
      else None

  // 使用工廠方法（看起來像是調用 apply）
  val person1 = Person("Alice", 25)
  println(s"person1 = $person1")

  val person2 = Person.fromString("Bob, 30")
  println(s"person2 = $person2")

  val person3 = Person.fromString("Invalid")
  println(s"person3 = $person3")

  println()

/**
 * 範例 5: apply 和 unapply 方法
 *
 * apply 方法允許物件像函式一樣被呼叫。
 * unapply 方法支援模式匹配和解構。
 */
def example5(): Unit =
  println("--- 範例 5: apply 和 unapply 方法 ---")

  class Email private(val value: String):
    override def toString(): String = value

  object Email:
    // apply：建立 Email 實例
    def apply(value: String): Option[Email] =
      if isValidEmail(value) then
        Some(new Email(value))
      else
        None

    // unapply：在模式匹配中解構 Email
    def unapply(email: Email): Option[String] =
      Some(email.value)

    private def isValidEmail(str: String): Boolean =
      str.contains("@") && str.contains(".")

  // 使用 apply
  val email1 = Email("alice@example.com")
  val email2 = Email("invalid-email")

  println(s"email1 = $email1")
  println(s"email2 = $email2")

  // 使用 unapply（在模式匹配中）
  email1 match
    case Some(Email(addr)) => println(s"有效的電郵地址: $addr")
    case Some(_) => println("無法辨識的電郵物件")
    case None => println("無效的電郵地址")

  email2 match
    case Some(Email(addr)) => println(s"有效的電郵地址: $addr")
    case Some(_) => println("無法辨識的電郵物件")
    case None => println("無效的電郵地址")

  println()

/**
 * 範例 6: 實踐工廠模式
 *
 * 使用 Trait、Class 和 Companion Object 實現工廠模式。
 */
def example6(): Unit =
  println("--- 範例 6: 工廠模式 ---")

  // 定義通用的 Trait
  trait Logger:
    def log(message: String): Unit

  // 具體的實作
  class ConsoleLogger extends Logger:
    def log(message: String): Unit =
      println(s"[Console] $message")

  class FileLogger(filename: String) extends Logger:
    def log(message: String): Unit =
      println(s"[File: $filename] $message")

  // 工廠物件
  object LoggerFactory:
    def create(logType: String, filename: String = ""): Logger =
      logType.toLowerCase() match
        case "console" => ConsoleLogger()
        case "file" => FileLogger(filename)
        case _ => ConsoleLogger()  // 預設使用 Console

  // 使用工廠
  val consoleLogger = LoggerFactory.create("console")
  consoleLogger.log("這是一條控制台日誌")

  val fileLogger = LoggerFactory.create("file", "app.log")
  fileLogger.log("這是一條檔案日誌")

  println()
