/**
 * 章節 18: 架構設計模式 (Architecture Patterns)
 *
 * 學習目標：
 * 1. 學習自身型別 (Self-types) 用於依賴注入 (Scala 2 & 3 Common)
 * 2. 掌握 Scala 3 的匯出子句 (Export Clauses) 實現組合優於繼承 (Scala 3 Only)
 *
 * 這些模式有助於設計低耦合、高內聚的系統架構。
 */

package architecture_patterns

@main def runArchitecturePatterns(): Unit =
  println("=== 章節 18: 架構設計模式 ===\n")

  // 範例 1: 自身型別 (Self-types)
  exampleSelfTypes()

  // 範例 2: 匯出子句 (Export Clauses)
  exampleExportClauses()

  println("\n=== 章節完成 ===")

/**
 * 範例 1: 自身型別 (Self-types)
 * (Scala 2 & 3 Common)
 *
 * 自身型別允許我們宣告一個 Trait 必須混入 (mix-in) 另一個型別才能被實例化。
 * 這常用於實現「蛋糕模式」(Cake Pattern) 進行依賴注入。
 *
 * 語法：trait A { self: B => ... }
 * 意義：Trait A 說「我需要 B 的功能才能運作，但我不想繼承 B」。
 */
def exampleSelfTypes(): Unit =
  println("--- 範例 1: 自身型別 (Self-types) ---")

  trait UserConfig:
    def databaseUrl: String = "jdbc:mysql://localhost:3306/db"

  // DatabaseService 需要 UserConfig，但不是繼承關係
  trait DatabaseService:
    // 自身型別宣告：任何混入 DatabaseService 的類別，也必須混入 UserConfig
    self: UserConfig => 

    def connect(): Unit = 
      // 可以直接使用 UserConfig 的方法，因為編譯器保證 self 也是 UserConfig
      println(s"連接到資料庫: $databaseUrl")

  // 實作：同時混入 DatabaseService 和 UserConfig
  class MyApp extends DatabaseService with UserConfig
  
  // class BrokenApp extends DatabaseService // 編譯錯誤：缺少 UserConfig

  val app = new MyApp()
  app.connect()
  println("透過 Self-type 成功存取了依賴的 UserConfig 成員。\n")

/**
 * 範例 2: 匯出子句 (Export Clauses)
 * (Scala 3 Only)
 *
 * 「組合優於繼承」是軟體設計的重要原則。
 * Scala 3 提供了 `export` 關鍵字，讓我們能輕鬆地將成員物件的方法暴露出去，
 * 而不需要手動撰寫代理方法 (Forwarding methods)。
 */
def exampleExportClauses(): Unit =
  println("\n--- 範例 2: 匯出子句 (Export Clauses) ---")

  class Printer:
    def print(msg: String): Unit = println(s"[印表機] $msg")
    def status(): String = "就緒"

  class Scanner:
    def scan(): String = "掃描內容..."

  // Copier 組合了 Printer 和 Scanner
  class Copier:
    private val printer = new Printer()
    private val scanner = new Scanner()

    // 匯出 printer 的所有方法
    export printer.*
    
    // 也可以只匯出特定方法，並重新命名
    export scanner.{scan as scanDoc}

    // 也可以定義自己的方法
    def copy(): Unit =
      val content = scanDoc()
      print(s"複製: $content")

  val copier = new Copier()
  
  // 直接呼叫被匯出的方法
  copier.print("測試文件") // 來自 Printer
  println(s"掃描器狀態: ${copier.status()}") // 來自 Printer
  
  // 呼叫重新命名的方法
  println(s"掃描結果: ${copier.scanDoc()}")
  
  copier.copy()
