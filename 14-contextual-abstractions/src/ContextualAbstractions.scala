/**
 * 章節 14: Contextual Abstractions（上下文抽象）
 *
 * 學習目標：
 * 1. 使用 given instance（給定實例）提供上下文值
 * 2. 使用 using clause（使用子句）、context bound（上下文界限）與 summon 取得上下文值
 * 3. 定義 extension method（擴充方法）與型別類別
 * 4. 使用參數化 given、given import（給定實例匯入）與 implicit conversion（隱式轉換）
 * 5. 使用 context function（上下文函式）與 by-name context parameter（傳名上下文參數）
 */

package contextual_abstractions

import scala.Conversion

trait Show[T]:
  def show(value: T): String

object Show:
  given Show[Int] with
    def show(value: Int): String = value.toString

  given Show[String] with
    def show(value: String): String = s"\"$value\""

  given [T](using elementShow: Show[T]): Show[Option[T]] with
    def show(value: Option[T]): String =
      value match
        case Some(element) => s"Some(${elementShow.show(element)})"
        case None => "None"

  extension [T](value: T)(using show: Show[T])
    def rendered: String = show.show(value)

def render[T: Show](value: T): String =
  summon[Show[T]].show(value)

def renderAll[T](values: List[T])(using show: Show[T]): List[String] =
  values.map(show.show)

case class LogContext(prefix: String)

type Logged[A] = LogContext ?=> A

def contextualMessage(message: String): Logged[String] =
  s"${summon[LogContext].prefix}: $message"

def withLogContext[A](prefix: String)(operation: Logged[A]): A =
  operation(using LogContext(prefix))

def evaluateLater[A](using value: => A): () => A =
  () => value

case class Email private (value: String)

object Email:
  def from(value: String): Option[Email] =
    val at = value.indexOf('@')
    val lastDot = value.lastIndexOf('.')
    Option.when(at > 0 && lastDot > at + 1 && lastDot < value.length - 1)(Email(value))

object EmailConversions:
  given Conversion[String, Option[Email]] with
    def apply(value: String): Option[Email] = Email.from(value)

@main def run(): Unit =
  println("=== 章節 14: Contextual Abstractions（上下文抽象）===\n")

  import Show.given
  import Show.*

  println(s"Context bound 與 summon: ${render(42)}")
  println(s"參數化 given: ${render(Option("Scala"))}")
  println(s"Extension method: ${Option(7).rendered}")
  println(s"Context function: ${withLogContext("INFO")(contextualMessage("ready"))}")

  var evaluations = 0
  val deferred = evaluateLater(using {
    evaluations += 1
    evaluations
  })
  println(s"By-name context parameter: ${deferred()}, ${deferred()}")

  import EmailConversions.given
  import scala.language.implicitConversions
  val converted: Option[Email] = "alice@example.com"
  println(s"Implicit conversion: $converted")

  println("\n=== 章節完成 ===")
