package contextual_abstractions

import scala.compiletime.testing.typeCheckErrors

class ContextualAbstractionsTest extends munit.FunSuite:
  import Show.given

  test("context bound renders available instances and parameterized options"):
    assertEquals(render(0), "0")
    assertEquals(render(Option("Scala")), "Some(\"Scala\")")
    assertEquals(render(Option.empty[String]), "None")

  test("renderAll preserves order and handles an empty boundary"):
    assertEquals(renderAll(List(2, 1, 0)), List("2", "1", "0"))
    assertEquals(renderAll(List.empty[Int]), Nil)

  test("missing contextual evidence is rejected at compile time"):
    val errors = typeCheckErrors("""
      import contextual_abstractions.*
      case class Unknown(value: Int)
      render(Unknown(1))
    """)
    assert(errors.nonEmpty)

  test("context functions receive the selected context"):
    assertEquals(withLogContext("DEBUG")(contextualMessage("started")), "DEBUG: started")
    assertEquals(withLogContext("")(contextualMessage("empty")), ": empty")

  test("by-name context parameters do not evaluate early or mutate unrelated state"):
    var evaluations = 0
    val unrelated = 10
    val deferred = evaluateLater(using {
      evaluations += 1
      evaluations
    })
    assertEquals(evaluations, 0)
    assertEquals(deferred(), 1)
    assertEquals(deferred(), 2)
    assertEquals(unrelated, 10)

  test("email construction accepts valid input and rejects invalid boundaries"):
    assertEquals(Email.from("a@b.co").map(_.value), Some("a@b.co"))
    assertEquals(Email.from("a@b"), None)
    assertEquals(Email.from("@b.co"), None)
    assertEquals(Email.from(""), None)
