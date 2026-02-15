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
