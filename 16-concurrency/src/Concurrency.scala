/**
 * 章節 16: 並行程式設計 (Concurrency)
 *
 * 學習目標：
 * 1. 理解 Future 的非同步計算模型
 * 2. 掌握 Promise 的手動完成機制
 * 3. 了解 ExecutionContext 的角色
 *
 * 註：Future 與 Promise 是 Scala 2.10 引入的標準庫功能，
 * 在 Scala 3 中 API 基本保持一致，屬於兩者共有的核心特性。
 */

import scala.concurrent.{Future, Promise, Await}
import scala.concurrent.duration._
import scala.util.{Success, Failure}
import java.util.concurrent.Executors

// 需要隱式 ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

@main def run(): Unit =
  println("=== 章節 16: 並行程式設計 (Concurrency) ===\n")

  example1()
  example2()
  example3()

  println("=== 章節完成 ===")

/**
 * 範例 1: Future 基礎
 *
 * Future 代表一個可能尚未完成的計算結果。
 * 這是 Scala 2 和 Scala 3 共有的標準並行模型。
 */
def example1(): Unit =
  println("--- 範例 1: Future 基礎 ---")

  println("1. 啟動非同步計算...")
  val futureResult: Future[Int] = Future {
    Thread.sleep(500) // 模擬耗時操作
    println("   (計算完成)")
    42
  }

  println("2. 主執行緒繼續執行...")

  // 使用回呼 (Callback) 處理結果
  futureResult.onComplete {
    case Success(value) => println(s"3. Callback 收到結果: $value")
    case Failure(e) => println(s"3. 計算失敗: ${e.getMessage}")
  }

  // 為了範例展示，我們在這裡阻塞等待結果 (實務上應盡量避免)
  Await.result(futureResult, 2.seconds)
  
  println()

/**
 * 範例 2: 組合 Future (Map, FlatMap, For-Comprehension)
 *
 * Future 是 Monad，可以像 Option 或 List 一樣進行組合。
 */
def example2(): Unit =
  println("--- 範例 2: 組合 Future ---")

  def fetchUser(id: Int): Future[String] = Future {
    Thread.sleep(100)
    s"User$id"
  }

  def fetchRole(user: String): Future[String] = Future {
    Thread.sleep(100)
    if (user == "User1") "Admin" else "Guest"
  }

  println("開始鏈式呼叫...")

  // 使用 for-comprehension 串接多個非同步操作
  // 這在 Scala 2 和 Scala 3 中寫法完全相同
  val resultFuture: Future[String] = for {
    user <- fetchUser(1)
    role <- fetchRole(user)
  } yield s"$user is $role"

  val result = Await.result(resultFuture, 2.seconds)
  println(s"最終結果: $result")

  println()

/**
 * 範例 3: Promise 手動控制
 *
 * Promise 是一個可寫入的單次賦值容器，用於手動完成 Future。
 */
def example3(): Unit =
  println("--- 範例 3: Promise ---")

  val promise = Promise[String]()
  val future = promise.future

  // 模擬一個非同步生產者
  new Thread(() => {
    println("   (生產者正在工作...)")
    Thread.sleep(500)
    promise.success("手動完成的任務")
    println("   (生產者已設定結果)")
  }).start()

  println("等待 Promise 完成...")
  val res = Await.result(future, 2.seconds)
  println(s"收到 Promise 結果: $res")

  println()
