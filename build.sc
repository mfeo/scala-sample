import mill._
import mill.scalalib._

// Scala 版本定義
val scalaVersion = "3.3.1"

// 基礎模組特徵，包含共同的 Scala 設定
trait LearningModule extends ScalaModule {
  def scalaVersion = "3.3.1"

  // 測試依賴（使用 munit）
  def ivyDeps = Agg(
    ivy"org.scalameta::munit:1.0.0"
  )

  object test extends ScalaTests {
    def ivyDeps = super.ivyDeps() ++ Agg(
      ivy"org.scalameta::munit:1.0.0"
    )
    def testFramework = "munit.Framework"
  }
}

// 章節 01: Hello World 與基本語法
object `01-hello-world` extends LearningModule

// 章節 02: 變數與型別推斷
object `02-variables` extends LearningModule

// 章節 03: 常數與不可變性
object `03-constants` extends LearningModule

// 章節 04: 控制流程
object `04-control-flow` extends LearningModule

// 章節 05: 函式與高階函式
object `05-functions` extends LearningModule

// 章節 06: 集合（List、Array、Seq）
object `06-collections` extends LearningModule

// 章節 07: Map 與 Set
object `07-map-set` extends LearningModule

// 章節 08: Case Class 與模式匹配基礎
object `08-case-class` extends LearningModule

// 章節 09: Trait 與 Companion Object
object `09-trait-object` extends LearningModule

// 章節 10: 進階模式匹配
object `10-pattern-matching` extends LearningModule

// 章節 11: Option 與 Try
object `11-option-try` extends LearningModule

// 章節 12: Either 與異常處理
object `12-error-handling` extends LearningModule
