package advanced_types

import scala.compiletime.testing.typeCheckErrors

class AdvancedTypesTest extends munit.FunSuite:
  test("enum exposes stable metadata and exhaustive ADT behavior"):
    assertEquals(TrafficSignal.values.toList, List(TrafficSignal.Red, TrafficSignal.Yellow, TrafficSignal.Green))
    assertEquals(TrafficSignal.valueOf("Yellow"), TrafficSignal.Yellow)
    assertEquals(TrafficSignal.Green.ordinal, 2)
    assertEquals(describeMessage(Message.Quit), "quit")
    assertEquals(describeMessage(Message.Move(0, Int.MinValue)), s"move(0,${Int.MinValue})")
    assertEquals(describeMessage(Message.Write("")), "write()")

  test("enum valueOf rejects unknown names without changing enum state"):
    intercept[IllegalArgumentException](TrafficSignal.valueOf("Purple"))
    assertEquals(TrafficSignal.values.length, 3)

  test("union parser covers valid, boundary, and invalid input"):
    assertEquals(describeParsed(parseNumber("0")), "number=0")
    assertEquals(describeParsed(parseNumber(Int.MaxValue.toString)), s"number=${Int.MaxValue}")
    assertEquals(describeParsed(parseNumber("")), "Invalid number: ")
    assertEquals(describeParsed(parseNumber("2147483648")), "Invalid number: 2147483648")

  test("intersection type invokes each capability exactly once"):
    var resets = 0
    var grows = 0
    val component = new Resettable with Growable:
      def reset(): String =
        resets += 1
        "reset"
      def grow(): String =
        grows += 1
        "grow"
    assertEquals(operate(component), "reset" -> "grow")
    assertEquals((resets, grows), (1, 1))

  test("opaque identifiers validate construction and hide representation"):
    import UserId.*
    assertEquals(UserId.from(1).map(_.value), Some(1L))
    assertEquals(UserId.from(Long.MaxValue).map(_.value), Some(Long.MaxValue))
    assertEquals(UserId.from(0), None)
    assertEquals(UserId.from(-1), None)
    assert(typeCheckErrors("""
      import advanced_types.UserId
      val id: UserId.Type = 1L
    """).nonEmpty)

  test("type lambda, match type, and polymorphic function preserve their types"):
    val map: KeyedMap[Int] = emptyKeyedMap
    val charEvidence: ElementOf[String] =:= Char = summon
    val intEvidence: ElementOf[List[Int]] =:= Int = summon
    assertEquals(map, Map.empty[String, Int])
    assert(charEvidence != null)
    assert(intEvidence != null)
    assertEquals(identityFunction[Int](0), 0)
    assertEquals(identityFunction[String](""), "")

  test("dependent function preserves the selected key type"):
    val intEntry = new Keyed:
      type Key = Int
      val key = 42
    val stringEntry = new Keyed:
      type Key = String
      val key = "id"
    val intKey: Int = extractKey(intEntry)
    val stringKey: String = extractKey(stringEntry)
    assertEquals(intKey, 42)
    assertEquals(stringKey, "id")

  test("wildcard collection operation handles empty and heterogeneous collections"):
    assertEquals(collectionSize(List.empty[Nothing]), 0)
    assertEquals(collectionSize(List(1, "two", true)), 3)
