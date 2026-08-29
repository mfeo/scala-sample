package modern_syntax_safety
import scala.language.strictEquality
import scala.compiletime.testing.typeCheckErrors

class ModernSyntaxSafetyTest extends munit.FunSuite:

  test("Multiversal Equality"):
    case class Id(n: Int) derives CanEqual
    assert(Id(1) == Id(1))
    assert(Id(1) != Id(2))
    val errors = typeCheckErrors("""
      import scala.language.strictEquality
      case class Id(n: Int) derives CanEqual
      Id(1) == 1
    """)
    assert(errors.nonEmpty)

  test("Parameter Untupling"):
    val list = List((1, "a"))
    val res = list.map( (n, s) => s"$n-$s" )
    assertEquals(res.head, "1-a")
    
  test("Inline function"):
    inline def add(a: Int, b: Int) = a + b
    assertEquals(add(1, 2), 3)

  test("inline conditionals cover both compile-time branches"):
    assertEquals(chooseMessage(true), "enabled")
    assertEquals(chooseMessage(false), "disabled")
    assert(typeCheckErrors("""
      import modern_syntax_safety.chooseMessage
      val dynamic = true
      chooseMessage(dynamic)
    """).nonEmpty)

  test("transparent inline preserves precise result types"):
    val text: String = defaultValue(true)
    val number: Int = defaultValue(false)
    assertEquals(text, "")
    assertEquals(number, 0)

  test("TypeTest accepts matching values and rejects non-matching values without mutation"):
    val input: Any = "Scala"
    val unrelated = List(1, 2, 3)
    assertEquals(checkedCast[Any, String](input), Some("Scala"))
    assertEquals(checkedCast[Any, String](42), None)
    assertEquals(unrelated, List(1, 2, 3))

  test("Matchable handles known, boundary, and fallback values"):
    assertEquals(describeMatchable(0), "Int(0)")
    assertEquals(describeMatchable(""), "String()")
    assertEquals(describeMatchable(true), "Other")
