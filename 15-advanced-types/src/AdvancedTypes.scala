/**
 * 章節 15: Scala 3.3.8 的進階型別
 *
 * 學習目標：
 * 1. 使用 enum（列舉）建立有限狀態與 ADT（Algebraic Data Type，代數資料型別）
 * 2. 使用 union type（聯集型別）、intersection type（交集型別）與 opaque type（不透明型別）
 * 3. 使用 type lambda（型別 Lambda）與 match type（匹配型別）進行型別層級運算
 * 4. 使用 polymorphic function（多型函式）與 dependent function（相依函式）
 * 5. 使用 `?` 表示 wildcard type argument（萬用型別引數）
 */

package advanced_types

enum TrafficSignal(val durationSeconds: Int) derives CanEqual:
  case Red extends TrafficSignal(60)
  case Yellow extends TrafficSignal(5)
  case Green extends TrafficSignal(45)

enum Message derives CanEqual:
  case Quit
  case Move(x: Int, y: Int)
  case Write(content: String)

def describeMessage(message: Message): String =
  message match
    case Message.Quit => "quit"
    case Message.Move(x, y) => s"move($x,$y)"
    case Message.Write(content) => s"write($content)"

def parseNumber(value: String): String | Int =
  value.toIntOption match
    case Some(number) => number
    case None => s"Invalid number: $value"

def describeParsed(value: String | Int): String =
  value match
    case number: Int => s"number=$number"
    case error: String => error

trait Resettable:
  def reset(): String

trait Growable:
  def grow(): String

def operate(component: Resettable & Growable): (String, String) =
  component.reset() -> component.grow()

object UserId:
  opaque type Type = Long

  def from(value: Long): Option[Type] = Option.when(value > 0)(value)

  extension (id: Type)
    def value: Long = id

type KeyedMap = [Value] =>> Map[String, Value]

def emptyKeyedMap[Value]: KeyedMap[Value] = Map.empty

type ElementOf[Container] = Container match
  case String => Char
  case Array[element] => element
  case Iterable[element] => element

val identityFunction: [Value] => Value => Value =
  [Value] => (value: Value) => value

trait Keyed:
  type Key
  def key: Key

val extractKey: (entry: Keyed) => entry.Key =
  (entry: Keyed) => entry.key

def collectionSize(values: Iterable[?]): Int = values.size

@main def run(): Unit =
  println("=== 章節 15: Scala 3.3.8 的進階型別 ===\n")

  println(s"Enum: ${TrafficSignal.values.mkString(", ")}")
  println(s"ADT: ${describeMessage(Message.Move(10, 20))}")
  println(s"Union type: ${describeParsed(parseNumber("123"))}")

  val component = new Resettable with Growable:
    def reset(): String = "reset"
    def grow(): String = "grow"
  println(s"Intersection type: ${operate(component)}")

  import UserId.*
  println(s"Opaque type: ${UserId.from(1).map(_.value)}")

  val map: KeyedMap[Int] = emptyKeyedMap
  println(s"Type lambda: $map")
  println(s"Polymorphic function: ${identityFunction[String]("Scala")}")
  println(s"Wildcard type argument: ${collectionSize(List(1, 2, 3))}")

  println("\n=== 章節完成 ===")
