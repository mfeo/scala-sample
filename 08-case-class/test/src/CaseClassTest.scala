/**
 * 測試檔案：CaseClass.scala
 *
 * 展示如何為 Case Class 編寫測試。
 */

class CaseClassTest extends munit.FunSuite:

  case class Book(title: String, author: String, year: Int)

  case class Library(name: String, books: List[Book])

  // 測試用例 1: Case Class 的相等性
  test("case class equality should work correctly"):
    val book1 = Book("Scala Programming", "Odersky", 2016)
    val book2 = Book("Scala Programming", "Odersky", 2016)
    val book3 = Book("Scala Programming", "Odersky", 2015)

    assertEquals(book1, book2)
    assertNotEquals(book1, book3)

  // 測試用例 2: copy 方法
  test("copy method should create modified copy"):
    val book = Book("Scala Programming", "Odersky", 2016)
    val newBook = book.copy(year = 2019)

    assertEquals(book.title, newBook.title)
    assertEquals(book.author, newBook.author)
    assertNotEquals(book.year, newBook.year)
    assertEquals(newBook.year, 2019)

  // 測試用例 3: 嵌入的 Case Class
  test("nested case class should work correctly"):
    val books = List(
      Book("Book 1", "Author 1", 2020),
      Book("Book 2", "Author 2", 2021)
    )
    val library = Library("City Library", books)

    assertEquals(library.name, "City Library")
    assertEquals(library.books.length, 2)
    assertEquals(library.books(0).title, "Book 1")

  // 測試用例 4: 模式匹配
  test("pattern matching with case class"):
    val book = Book("Scala Programming", "Odersky", 2016)

    val result = book match
      case Book("Scala Programming", author, _) => Some(author)
      case _ => None

    assertEquals(result, Some("Odersky"))

  // 測試用例 5: hashCode 的一致性
  test("case class with same values should have same hashCode"):
    val book1 = Book("Scala Programming", "Odersky", 2016)
    val book2 = Book("Scala Programming", "Odersky", 2016)

    assertEquals(book1.hashCode(), book2.hashCode())
