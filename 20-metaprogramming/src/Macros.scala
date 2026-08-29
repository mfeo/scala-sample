package metaprogramming

import scala.quoted.*
import scala.compiletime.error
import scala.compiletime.constValue
import scala.deriving.Mirror

trait FieldCount[T]:
  def value: Int

object FieldCount:
  inline def apply[T](using count: FieldCount[T]): Int = count.value

  private def instance[T](count: Int): FieldCount[T] =
    new FieldCount[T]:
      val value: Int = count

  inline given derived[T](using mirror: Mirror.Of[T]): FieldCount[T] =
    inline mirror match
      case product: Mirror.ProductOf[T] =>
        instance(constValue[Tuple.Size[product.MirroredElemTypes]])
      case sum: Mirror.SumOf[T] =>
        instance(constValue[Tuple.Size[sum.MirroredElemTypes]])

case class AuditEvent(id: Long, action: String, successful: Boolean) derives FieldCount

enum Command derives FieldCount:
  case Start, Stop, Restart

object Macros:
  /**
   * 使用 scala.compiletime 在不進入反射 API 的情況下產生編譯期錯誤。
   */
  inline def requireNonNegative(inline value: Int): Int =
    inline if value >= 0 then value
    else error("value must be non-negative")

  /**
   * 定義行內函式作為 Macro 的進入點
   */
  inline def debug(inline expr: Any): Unit =
    ${ debugImpl('expr) }

  /**
   * Macro 的實作
   */
  def debugImpl(expr: Expr[Any])(using Quotes): Expr[Unit] =
    import quotes.reflect.*

    // 取得運算式的原始程式碼位置資訊
    // 注意：這依賴於編譯器能否取得原始碼位置
    val pos = expr.asTerm.pos
    val code = pos.sourceCode.getOrElse("Unknown Code")

    '{
      println("Value of " + ${Expr(code)} + " is: " + $expr)
    }

  inline def inspectType[T]: Unit =
    ${ inspectTypeImpl[T] }

  def inspectTypeImpl[T: Type](using Quotes): Expr[Unit] =
    import quotes.reflect.*
    val typeName = Type.show[T]
    '{
      println("This is a type: " + ${Expr(typeName)})
    }

  /**
   * Quoted pattern（引號模式）可解構接收到的程式碼，而不只讀取文字位置。
   */
  inline def isAddition(inline expr: Int): Boolean =
    ${ isAdditionImpl('expr) }

  def isAdditionImpl(expr: Expr[Int])(using Quotes): Expr[Boolean] =
    expr match
      case '{ ($left: Int) + ($right: Int) } => Expr(true)
      case _ => Expr(false)
