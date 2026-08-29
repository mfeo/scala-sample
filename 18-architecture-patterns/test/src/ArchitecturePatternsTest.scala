package architecture_patterns

class ArchitecturePatternsTest extends munit.FunSuite:

  test("Self-types should enforce dependency"):
    trait Config { def url: String = "db://localhost" }
    trait Service { self: Config =>
      def connect() = s"Connecting to $url"
    }
    class App extends Service with Config
    assertEquals(new App().connect(), "Connecting to db://localhost")

  test("Export clauses should delegate methods"):
    class Inner { def action() = "Action!" }
    class Outer {
      val inner = new Inner
      export inner.action
    }
    assertEquals(new Outer().action(), "Action!")

  test("trait parameters provide constructor context"):
    assertEquals(FriendlyGreeter().greet("Scala"), "Hello, Scala")
    assertEquals(FriendlyGreeter().greet(""), "Hello, ")

  test("open classes remain extensible without mutating the parent"):
    val parent = ExtensibleService("base")
    val child = AuditService()
    assertEquals(parent.name, "base")
    assertEquals(child.name, "audit")
    assertEquals(parent.name, "base")

  test("universal apply and target-named infix methods preserve values"):
    assertEquals(Device("").name, "")
    assertEquals(Vector2(0, 0) + Vector2(Int.MaxValue, Int.MinValue), Vector2(Int.MaxValue, Int.MinValue))
