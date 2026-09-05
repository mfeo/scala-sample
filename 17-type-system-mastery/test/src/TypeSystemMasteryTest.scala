package type_system_mastery

class TypeSystemMasteryTest extends munit.FunSuite:

  test("Covariance: Box[Cat] should be a Box[Animal]"):
    class Box[+T](val value: T)
    val catBox = new Box(Cat("Mimi"))
    val animalBox: Box[Animal] = catBox
    assertEquals(animalBox.value.name, "Mimi")

  test("Contravariance: Printer[Animal] should be a Printer[Cat]"):
    var lastPrinted = ""
    trait Printer[-T]:
      def print(item: T): Unit
    
    val animalPrinter = new Printer[Animal]:
      def print(animal: Animal): Unit = lastPrinted = animal.name
      
    val catPrinter: Printer[Cat] = animalPrinter
    catPrinter.print(Cat("Mimi"))
    assertEquals(lastPrinted, "Mimi")
    
  test("Abstract Type Members"):
    trait Eater:
      type F <: Food
      def eat(food: F): String

    class Cow extends Eater:
      type F = Grass
      def eat(food: Grass) = "Moo"

    assertEquals(new Cow().eat(new Grass), "Moo")

  test("kind polymorphism preserves ordinary and constructor kinds"):
    assertEquals(acceptKind[Int]("value"), "value")
    assertEquals(acceptKind[List]("unary"), "unary")
    assertEquals(acceptKind[Map]("binary"), "binary")

  test("programmatic structural type selects required members only"):
    var unrelated = 0
    val record: NamedRecord = new:
      val name = "record"
      def touch(): Unit = unrelated += 1
    assertEquals(structuralName(record), "record")
    assertEquals(unrelated, 0)
