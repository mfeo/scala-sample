package metaprogramming

import metaprogramming.Macros.*
import scala.compiletime.testing.typeCheckErrors

class MetaprogrammingTest extends munit.FunSuite:
  test("compile-time validation accepts zero and positive boundaries"):
    assertEquals(requireNonNegative(0), 0)
    assertEquals(requireNonNegative(Int.MaxValue), Int.MaxValue)

  test("compile-time validation rejects negative constants"):
    val errors = typeCheckErrors("""
      import metaprogramming.Macros.requireNonNegative
      requireNonNegative(-1)
    """)
    assert(errors.exists(_.message.contains("value must be non-negative")))

  test("inline validation rejects values unavailable at compile time"):
    assert(typeCheckErrors("""
      import metaprogramming.Macros.requireNonNegative
      val dynamic = 1
      requireNonNegative(dynamic)
    """).nonEmpty)

  test("quoted pattern recognizes addition without evaluating unrelated effects"):
    var evaluations = 0
    val left = 1
    val right = 2
    assert(isAddition(left + right))
    assert(!isAddition({ evaluations += 1; 3 * 4 }))
    assertEquals(evaluations, 0)

  test("Mirror derivation counts product fields and sum alternatives"):
    assertEquals(FieldCount[AuditEvent], 3)
    assertEquals(FieldCount[Command], 3)
