/**
 * 章節 07: Map 與 Set
 *
 * 學習目標：
 * 1. 掌握 Map（不可變映射）的使用
 * 2. 理解可變 Map（mutable.Map）
 * 3. 學習 Map 的常用操作
 * 4. 掌握 Set（集合）的概念
 * 5. 進行 Set 的集合運算（交集、聯集、差集）
 * 6. 實踐 Option 與 Map 的結合使用
 */

@main def run(): Unit =
  println("=== 章節 07: Map 與 Set ===\n")

  example1()
  example2()
  example3()
  example4()
  example5()
  example6()

  println("=== 章節完成 ===")

/**
 * 範例 1: 不可變 Map（Map）
 *
 * Map 是鍵值對的集合。預設的 Map 是不可變的。
 */
def example1(): Unit =
  println("--- 範例 1: 不可變 Map ---")

  // 建立 Map（使用箭頭語法）
  val scores = Map(
    "Alice" -> 95,
    "Bob" -> 87,
    "Charlie" -> 92
  )
  println(s"scores = $scores")

  // 建立空 Map
  val emptyMap: Map[String, Int] = Map()
  println(s"emptyMap = $emptyMap")

  // 訪問值
  println(s"scores(\"Alice\") = ${scores("Alice")}")

  // 檢查鍵是否存在
  println(s"scores.contains(\"Bob\") = ${scores.contains("Bob")}")
  println(s"scores.contains(\"Diana\") = ${scores.contains("Diana")}")

  // 取得所有的鍵和值
  println(s"keys = ${scores.keys}")
  println(s"values = ${scores.values}")

  // Map 是不可變的，修改會產生新 Map
  val newScores = scores + ("Diana" -> 88)
  println(s"加入 Diana: $newScores")

  val reducedScores = scores - "Bob"
  println(s"移除 Bob: $reducedScores")

  println()

/**
 * 範例 2: 可變 Map
 *
 * 可變 Map 允許在原地進行修改。
 * 需要導入 scala.collection.mutable
 */
def example2(): Unit =
  println("--- 範例 2: 可變 Map ---")

  import scala.collection.mutable

  // 建立可變 Map
  val mutableScores = mutable.Map(
    "Alice" -> 95,
    "Bob" -> 87,
    "Charlie" -> 92
  )
  println(s"原始 Map = $mutableScores")

  // 修改值
  mutableScores("Alice") = 100
  println(s"修改 Alice 的分數: $mutableScores")

  // 添加新鍵值對
  mutableScores += ("Diana" -> 88)
  println(s"添加 Diana: $mutableScores")

  // 移除鍵值對
  mutableScores -= "Bob"
  println(s"移除 Bob: $mutableScores")

  // clear 清空所有元素
  val tempMap = mutable.Map("x" -> 1, "y" -> 2)
  println(s"清空前: $tempMap")
  tempMap.clear()
  println(s"清空後: $tempMap")

  println()

/**
 * 範例 3: Map 的常用操作
 *
 * 展示 Map 中的常用方法和操作。
 */
def example3(): Unit =
  println("--- 範例 3: Map 的常用操作 ---")

  val userAges = Map(
    "Alice" -> 25,
    "Bob" -> 30,
    "Charlie" -> 22,
    "Diana" -> 28
  )
  println(s"userAges = $userAges")

  // 使用 get 方法（回傳 Option）
  val aliceAge = userAges.get("Alice")
  println(s"get(\"Alice\") = $aliceAge")

  val eveAge = userAges.get("Eve")
  println(s"get(\"Eve\") = $eveAge")

  // 使用 getOrElse
  println(s"getOrElse(\"Eve\", 0) = ${userAges.getOrElse("Eve", 0)}")

  // 針對值進行 map 操作
  val incrementedAges = userAges.map((name, age) => name -> (age + 1))
  println(s"所有人加一歲: $incrementedAges")

  // 篩選 Map
  val adultsOver25 = userAges.filter((name, age) => age > 25)
  println(s"年齡超過 25 歲: $adultsOver25")

  // 轉換 Map
  val ageList = userAges.map((name, age) => s"$name: $age 歲")
  println(s"轉換為列表: $ageList")

  println()

/**
 * 範例 4: Set（集合）
 *
 * Set 是一個無序的、不含重複元素的集合。
 */
def example4(): Unit =
  println("--- 範例 4: Set（集合） ---")

  // 建立 Set
  val colors = Set("紅", "綠", "藍", "黃", "紅")  // 重複的元素只會出現一次
  println(s"colors = $colors")

  // 建立空 Set
  val emptySet: Set[String] = Set()
  println(s"emptySet = $emptySet")

  // 檢查元素是否在 Set 中
  println(s"colors.contains(\"紅\") = ${colors.contains("紅")}")
  println(s"colors.contains(\"紫\") = ${colors.contains("紫")}")

  // Set 的大小
  println(s"colors.size = ${colors.size}")

  // 添加和移除元素（產生新 Set）
  val newColors = colors + "紫"
  println(s"添加紫色: $newColors")

  val reducedColors = colors - "綠"
  println(s"移除綠色: $reducedColors")

  println()

/**
 * 範例 5: Set 的集合運算
 *
 * Set 支援交集、聯集、差集等數學操作。
 */
def example5(): Unit =
  println("--- 範例 5: Set 的集合運算 ---")

  val setA = Set(1, 2, 3, 4, 5)
  val setB = Set(4, 5, 6, 7, 8)

  println(s"setA = $setA")
  println(s"setB = $setB")

  // 聯集（並集）
  val union = setA ++ setB
  println(s"聯集（setA ++ setB）: $union")

  // 交集
  val intersection = setA & setB
  println(s"交集（setA & setB）: $intersection")

  // 差集（在 A 但不在 B）
  val difference = setA -- setB
  println(s"差集（setA -- setB）: $difference")

  // 對稱差集（在 A 或 B 但不在兩者）
  val symmetricDiff = (setA -- setB) ++ (setB -- setA)
  println(s"對稱差集: $symmetricDiff")

  // 子集檢查
  val subsetA = Set(2, 3, 4)
  println(s"$subsetA 是 $setA 的子集: ${subsetA.subsetOf(setA)}")

  println()

/**
 * 範例 6: Map 與 Option 的結合
 *
 * Map 和 Option 的結合是 Scala 中處理可能不存在的值的典範做法。
 */
def example6(): Unit =
  println("--- 範例 6: Map 與 Option 的結合 ---")

  val userDatabase = Map(
    "alice" -> "Alice Smith",
    "bob" -> "Bob Johnson",
    "charlie" -> "Charlie Brown"
  )

  println(s"使用者資料庫: $userDatabase")

  // 使用 get 取得 Option 值
  def lookupUser(username: String): Option[String] =
    userDatabase.get(username)

  // 處理 Option 值
  def greetUser(username: String): Unit =
    lookupUser(username) match
      case Some(fullName) => println(s"歡迎，$fullName！")
      case None => println(s"找不到使用者 $username")

  greetUser("alice")
  greetUser("bob")
  greetUser("diana")

  // 使用 getOrElse 提供預設值
  println(s"alice 的名字: ${userDatabase.get("alice").getOrElse("Unknown")}")
  println(s"diana 的名字: ${userDatabase.get("diana").getOrElse("Unknown")}")

  // 使用 foreach 在 Option 有值時執行操作
  println("\n使用 foreach 輸出存在的使用者:")
  lookupUser("bob").foreach(name => println(s"  找到: $name"))
  lookupUser("eve").foreach(name => println(s"  找到: $name"))

  println()
