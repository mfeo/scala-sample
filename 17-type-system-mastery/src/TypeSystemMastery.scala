/**
 * 章節 17: 型別系統精通 (Type System Mastery)
 *
 * 學習目標：
 * 1. 理解 Scala 的變異性 (Variance)：共變 (+T)、逆變 (-T) 與不變性 (Invariant)
 * 2. 學習抽象型別成員 (Abstract Type Members) 及其應用
 * 3. 掌握泛型與抽象型別成員的差異與選擇時機
 *
 * 這些概念是 Scala 集合庫與許多進階程式庫設計的核心，
 * 理解它們有助於閱讀複雜的原始碼並設計更靈活的 API。
 */

package type_system_mastery

import scala.AnyKind
import scala.reflect.Selectable.reflectiveSelectable

def acceptKind[Value <: AnyKind](label: String): String = label

type NamedRecord = { def name: String }

def structuralName(record: NamedRecord): String = record.name

// 定義一些用於範例的類別層次結構
trait Animal:
  def name: String

case class Cat(name: String) extends Animal
case class Dog(name: String) extends Animal
class Food:
  override def toString = "食物"

class Grass extends Food:
  override def toString = "草"

class Meat extends Food:
  override def toString = "肉"

@main def runTypeSystemMastery(): Unit =
  println("=== 章節 17: 型別系統精通 ===\n")

  // 範例 1: 變異性 - 共變 (Covariance)
  exampleVarianceCovariance()

  // 範例 2: 變異性 - 逆變 (Contravariance)
  exampleVarianceContravariance()

  // 範例 3: 變異性 - 不變性 (Invariance)
  exampleVarianceInvariance()

  // 範例 4: 抽象型別成員 (Abstract Type Members)
  exampleAbstractTypeMembers()

  // 範例 5: Kind polymorphism 與 programmatic structural type
  exampleScala3TypeFoundations()

  println("\n=== 章節完成 ===")

/**
 * 範例 1: 共變 (Covariance) [+T]
 * 共變意味著：如果 Cat 是 Animal 的子類別，那麼 Box[Cat] 也是 Box[Animal] 的子類別。
 * 通常用於「生產者」(Producer) 或「唯讀」的容器。
 *
 * 記憶口訣：Producer Extends (PE) -> output only -> +T
 */
def exampleVarianceCovariance(): Unit =
  println("--- 範例 1: 共變 (Covariance) [+T] ---")

  // 定義一個共變的容器 (類似 Scala 的 List)
  // +T 表示這個容器是共變的
  class Box[+T](val value: T)

  val catBox: Box[Cat] = new Box(Cat("咪咪"))
  
  // 因為 Box 是共變的，所以 Box[Cat] 可以賦值給 Box[Animal]
  val animalBox: Box[Animal] = catBox

  println(s"Animal Box 內容: ${animalBox.value.name}")
  println("成功將 Box[Cat] 視為 Box[Animal]")
  
  // 共變的限制：
  // 共變型別 T 通常只能出現在「回傳位置」(return position)，不能出現在「參數位置」。
  // 因為如果允許傳入 Animal 到 Box[Cat] 中，可能會傳入 Dog，破壞型別安全。

/**
 * 範例 2: 逆變 (Contravariance) [-T]
 * 逆變意味著：如果 Cat 是 Animal 的子類別，那麼 Printer[Animal] 是 Printer[Cat] 的子類別。
 * 這聽起來很反直覺，但對於「消費者」(Consumer) 來說是合理的。
 * 如果一個列印機可以列印任何動物 (Printer[Animal])，那它當然可以用來列印貓 (作為 Printer[Cat] 使用)。
 *
 * 記憶口訣：Consumer Super (CS) -> input only -> -T
 */
def exampleVarianceContravariance(): Unit =
  println("\n--- 範例 2: 逆變 (Contravariance) [-T] ---")

  // 定義一個逆變的介面 (類似 Scala 的 Function1)
  // -T 表示這個介面是逆變的
  trait Printer[-T]:
    def print(item: T): Unit

  // 一個可以列印任何動物的列印機
  val animalPrinter: Printer[Animal] = new Printer[Animal]:
    def print(animal: Animal): Unit = println(s"列印動物: ${animal.name}")

  // 因為 Printer 是逆變的，Printer[Animal] 可以賦值給 Printer[Cat]
  // 邏輯：如果你需要一個能印貓的列印機，我給你一個能印所有動物的列印機，絕對沒問題。
  val catPrinter: Printer[Cat] = animalPrinter

  catPrinter.print(Cat("波斯貓"))
  // catPrinter.print(Dog("旺財")) // 編譯錯誤：catPrinter 只能接受 Cat

/**
 * 範例 3: 不變性 (Invariance) [T]
 * 不變性意味著：即使 Cat 是 Animal 的子類別，Container[Cat] 與 Container[Animal] 沒有任何繼承關係。
 * 這是 Scala (以及 Java) 中可變集合 (Mutable Collections) 的預設行為。
 * 為了保證寫入安全，可變容器必須是不變的。
 */
def exampleVarianceInvariance(): Unit =
  println("\n--- 範例 3: 不變性 (Invariance) [T] ---")

  // 定義一個不變的容器 (類似 Scala 的 Array 或 mutable.Set)
  class Container[T](var item: T)

  val catContainer = new Container(Cat("小黑"))
  
  // 下面的程式碼如果取消註解會導致編譯錯誤：
  // val animalContainer: Container[Animal] = catContainer 
  // 錯誤原因：如果這行合法，我們就可以做以下操作：
  // animalContainer.item = Dog("小白") // 把狗放進原本是貓的容器裡！
  // val cat: Cat = catContainer.item   // 若允許此操作，型別安全就會遭到破壞
  
  println("Container[Cat] 不能賦值給 Container[Animal]，保證了寫入安全。")
  println(s"容器內容: ${catContainer.item.name}")

/**
 * 範例 4: 抽象型別成員 (Abstract Type Members)
 * 抽象型別成員是 Scala 提供的一種泛型替代方案。
 * 它將型別定義為 trait 或 class 的成員，而不是參數。
 * 
 * 適用場景：
 * 1. 當型別之間有緊密的關聯時 (例如：Food 必須對應 Animal)。
 * 2. 隱藏複雜的型別參數，讓 API 更簡潔。
 */
def exampleAbstractTypeMembers(): Unit =
  println("\n--- 範例 4: 抽象型別成員 ---")

  // 使用泛型 (Generics) 的方式
  trait EaterGeneric[F <: Food]:
    def eat(food: F): Unit

  // 使用抽象型別成員 (Abstract Type Member) 的方式
  trait Eater:
    type F <: Food // 宣告一個抽象型別成員 F，必須是 Food 的子類別
    def eat(food: F): Unit

  // 實作 Cow，指定 F 為 Grass
  class Cow extends Eater:
    type F = Grass
    def eat(food: Grass): Unit = println(s"牛正在吃: $food")

  val cow = new Cow()
  cow.eat(new Grass())
  // cow.eat(new Meat()) // 編譯錯誤：Cow 只能吃 Grass
  
  println("透過 `type F = Grass`，我們將具體型別綁定到了實作中。")

/**
 * 範例 5: Kind Polymorphism（種類多型）與 Programmatic Structural Type（程式化結構型別）
 *
 * AnyKind 讓型別定義同時接受普通型別與型別建構器。
 * 結構型別依照成員形狀接受物件；reflectiveSelectable 提供 JVM 上的反射式方法選取。
 */
def exampleScala3TypeFoundations(): Unit =
  println("\n--- 範例 5: Kind Polymorphism 與 Structural Type ---")

  val ordinary = acceptKind[Int]("Int")
  val constructor = acceptKind[List]("List")
  val binaryConstructor = acceptKind[Map]("Map")

  val record: NamedRecord = new:
    val name = "structural"

  println(s"普通型別: $ordinary")
  println(s"一元型別建構器: $constructor")
  println(s"二元型別建構器: $binaryConstructor")
  println(s"結構型別: ${structuralName(record)}")
