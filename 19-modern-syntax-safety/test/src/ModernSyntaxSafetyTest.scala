package modern_syntax_safety
import scala.language.strictEquality

class ModernSyntaxSafetyTest extends munit.FunSuite:

  test("Multiversal Equality"):
    case class Id(n: Int) derives CanEqual
    assert(Id(1) == Id(1))
    assert(Id(1) != Id(2))
    // assert(Id(1) != 1) // Compilation error if enabled, so we can't test runtime behavior easily other than valid ones

  test("Parameter Untupling"):
    val list = List((1, "a"))
    val res = list.map( (n, s) => s"$n-$s" )
    assertEquals(res.head, "1-a")
    
  test("Inline function"):
    inline def add(a: Int, b: Int) = a + b
    assertEquals(add(1, 2), 3)
