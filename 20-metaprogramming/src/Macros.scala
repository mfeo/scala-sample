package metaprogramming

import scala.quoted.*

object Macros:
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
