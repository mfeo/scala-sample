# Scala 學習範例專案

這是一個針對 Scala 初學者設計的學習專案，透過 20 個獨立章節循序漸進地學習 Scala 程式語言。本專案明確以 Scala 3.3.8 LTS（Long-Term Support，長期支援版本）為準，不宣稱涵蓋較新的 Scala 3.7–3.8 功能。

## 專案特色

- ✅ 使用 Mill 建置工具（高效能、易於使用）
- ✅ 20 個獨立章節，每個都可單獨編譯執行
- ✅ 大量繁體中文註解，適合華語學習者
- ✅ 涵蓋 Scala 核心概念與最佳實踐
- ✅ 使用並解釋 Scala 3.3.8 LTS 語法與語意
- ✅ 包含部分測試範例，展示 Scala 測試寫法

## 環境需求

- Java 11 或更高版本
- Mill 1.1.2 或更高版本
- （可選）IDE：IntelliJ IDEA 或 VS Code + Metals

## 安裝 Mill

### macOS/Linux
```bash
curl -L https://repo1.maven.org/maven2/com/lihaoyi/mill-dist/1.1.8/mill-dist-1.1.8-mill.sh -o /usr/local/bin/mill
chmod +x /usr/local/bin/mill
```

### Windows
下載 [Mill Windows 版本](https://github.com/com-lihaoyi/mill/releases)

### 驗證安裝
```bash
mill --version
```

## 快速開始

### 編譯所有模組
```bash
mill _.compile
```

### 執行特定章節
```bash
# 執行第一章：Hello World
mill 01-hello-world.run

# 執行第五章：Functions
mill 05-functions.run

# 執行第十二章：Error Handling
mill 12-error-handling.run
```

### 互動模式（REPL）
```bash
mill -i 01-hello-world.console
```

### 執行測試
```bash
# 執行所有測試
mill _.test

# 執行特定模組的測試
mill 08-case-class.test
```

### 清理建置產物
```bash
mill clean
```

## 學習路徑

### 階段一：基礎語法（第 1-4 章）
1. **01-hello-world** - 認識 Scala 程式結構
2. **02-variables** - 變數與型別系統
3. **03-constants** - 不可變性概念
4. **04-control-flow** - 控制流程

### 階段二：函式與集合（第 5-7 章）
5. **05-functions** - 函式式程式設計基礎
6. **06-collections** - 集合操作與進階函式
7. **07-map-set** - 進階集合類型

### 階段三：物件導向（第 8-9 章）
8. **08-case-class** - 資料類別
9. **09-trait-object** - 抽象與混入

### 階段四：進階主題與函數式編程（第 10-13 章）
10. **10-pattern-matching** - 進階模式匹配
11. **11-option-try** - 安全的空值處理
12. **12-error-handling** - 錯誤處理策略
13. **13-functional-programming** - 函數式程式設計核心

### 階段五：Scala 3.3.8 功能與並行（第 14-16 章）
14. **14-contextual-abstractions** - 上下文抽象（Given、Using、Context Function）
15. **15-advanced-types** - 進階型別系統（Enum、Union/Intersection、Match Type、Type Lambda）
16. **16-concurrency** - 並行程式設計 (Future/Promise)

### 階段六：進階架構與現代語法（第 17-19 章）
17. **17-type-system-mastery** - 型別系統精通（Variance、Abstract/Structural Types、Kind Polymorphism）
18. **18-architecture-patterns** - 架構設計模式（Self-types、Export、Trait Parameters、Open/Transparent）
19. **19-modern-syntax-safety** - 現代語法與安全（Multiversal Equality、Inline、TypeTest、Safe Initialization）

### 階段七：元程式設計（第 20 章）
20. **20-metaprogramming** - 元程式設計（Inline、Compile-time Operations、Quotes、Splices、Mirror Derivation）

## 章節概覽

| 章節 | 主題 | 核心概念 | 難度 |
|------|------|----------|------|
| 01 | Hello World | 基本語法、程式入口 | ⭐ |
| 02 | Variables | var/val、型別推斷 | ⭐ |
| 03 | Constants | 不可變性、lazy val | ⭐ |
| 04 | Control Flow | if/for/while/match | ⭐⭐ |
| 05 | Functions | 高階函式、Lambda | ⭐⭐ |
| 06 | Collections | List/Array/Seq | ⭐⭐ |
| 07 | Map & Set | 字典與集合 | ⭐⭐ |
| 08 | Case Class | 資料類別、模式匹配 | ⭐⭐⭐ |
| 09 | Trait & Object | 抽象、混入、單例 | ⭐⭐⭐ |
| 10 | Pattern Matching | 進階模式匹配 | ⭐⭐⭐ |
| 11 | Option & Try | Option/Try monad | ⭐⭐⭐⭐ |
| 12 | Error Handling | Either、異常處理 | ⭐⭐⭐⭐ |
| 13 | Functional Programming | 純函式、柯里化 | ⭐⭐⭐⭐ |
| 14 | Contextual Abstractions | Given/Using、Extension、Context Function | ⭐⭐⭐⭐⭐ |
| 15 | Advanced Types | Enum、Union/Intersection、Match Type、Type Lambda | ⭐⭐⭐⭐⭐ |
| 16 | Concurrency | Future、Promise | ⭐⭐⭐⭐ |
| 17 | Type System Mastery | Variance、Abstract/Structural Types、Kind Polymorphism | ⭐⭐⭐⭐⭐ |
| 18 | Architecture Patterns | Self-types、Export、Trait Parameters、Open/Transparent | ⭐⭐⭐⭐⭐ |
| 19 | Modern Syntax & Safety | Multiversal Equality、Inline、TypeTest、Safe Initialization | ⭐⭐⭐⭐⭐ |
| 20 | Metaprogramming | Compile-time Operations、Macros、Quotes、Mirror | ⭐⭐⭐⭐⭐ |

## Scala 3.3.8 功能範圍

本專案以可實際編譯、執行與測試的 Scala 3.3.8 功能為範圍：

- 新語法：縮排式區塊、選用大括號、新控制結構、頂層定義、`@main`、通用 `apply`、`*` 可變參數展開，以及 `*`/`as` 匯入語法。
- 上下文抽象：`given`、`using`、context bound（上下文界限）、given import（給定實例匯入）、extension method（擴充方法）、context function（上下文函式）、傳名上下文參數與隱式轉換。
- 型別系統：enum（列舉）、聯集與交集型別、不透明型別、型別 Lambda、匹配型別、相依函式、多型函式、種類多型、結構型別、`Matchable` 與 `TypeTest`。
- 類別設計：trait parameter（特徵參數）、`open`、`transparent`、`export`、`infix` 與 `@targetName`。
- 安全與元程式設計：嚴格相等性、安全初始化、`inline`、`transparent inline`、編譯期操作、引號與拼接、反射，以及以 `Mirror` 進行型別類別衍生。

Scala 3.7 的 Named Tuples（具名元組）及 Scala 3.8 的 Better Fors（改良 for 推導式）與 `runtimeChecked` 不在本專案的 3.3.8 範圍內。

## Scala 2 遷移重點

- 型別萬用字元由 `_` 改成 `?`。
- 萬用匯入使用 `*`，重新命名使用 `as`。
- 可變參數序列使用 `values*` 展開，不再使用 `values: _*`。
- 方法可自動進行 Eta expansion（轉換成函式值），通常不再需要尾端 `_`。
- Scala 3 調整了型別推斷、多載解析與隱式解析規則；新程式應優先使用 `given` 與 `using`。
- Scala 3 已移除真正的 `do-while`、existential type（存在型別）、一般型別投影、程序語法、early initializer（早期初始化器）、package object（套件物件）與 Scala 2 巨集。

## 與 Go 的對應關係

如果您熟悉 Go 語言，以下對照表可幫助您快速理解：

| Go 概念 | Scala 對應 | 說明 |
|---------|-----------|------|
| struct | case class | 資料結構定義 |
| interface | trait | 抽象介面 |
| pointer | Option/reference | Scala 使用 Option 處理可能為空的情況 |
| slice | List/Seq | 動態序列 |
| map | Map | 鍵值對 |
| error | Either/Try | 錯誤處理 |
| defer | try-finally | 資源清理 |

## 常用 Mill 指令

```bash
# 編譯特定模組
mill 01-hello-world.compile

# 執行特定模組
mill 01-hello-world.run

# 查看模組產物
mill 01-hello-world.jar

# 執行所有測試
mill _.test

# 清理建置產物
mill clean

# 查看所有可用指令
mill resolve _

# 互動式開發（REPL）
mill -i <module-name>.console
```

## 專案結構

```
scala-sample/
├── build.mill            # Mill 建置設定
├── .gitignore            # Git 忽略檔案
├── README.md             # 本文件
├── 01-hello-world/
│   ├── src/
│   │   └── HelloWorld.scala
│   └── test/src/         # 測試檔案（部分章節）
├── 02-variables/
│   └── src/
│       └── Variables.scala
├── 03-constants/
│   └── src/
│       └── Constants.scala
├── 04-control-flow/
│   └── src/
│       └── ControlFlow.scala
├── 05-functions/
│   ├── src/
│   │   └── Functions.scala
│   └── test/src/
│       └── FunctionsTest.scala
├── 06-collections/
│   └── src/
│       └── Collections.scala
├── 07-map-set/
│   └── src/
│       └── MapSet.scala
├── 08-case-class/
│   ├── src/
│   │   └── CaseClass.scala
│   └── test/src/
│       └── CaseClassTest.scala
├── 09-trait-object/
│   └── src/
│       └── TraitObject.scala
├── 10-pattern-matching/
│   └── src/
│       └── PatternMatching.scala
├── 11-option-try/
│   ├── src/
│   │   └── OptionTry.scala
│   └── test/src/
│       └── OptionTryTest.scala
├── 12-error-handling/
│   ├── src/
│   │   └── ErrorHandling.scala
│   └── test/src/
│       └── ErrorHandlingTest.scala
├── 13-functional-programming/
│   └── src/
│       └── FunctionalProgramming.scala
├── 14-contextual-abstractions/
│   ├── src/
│   │   └── ContextualAbstractions.scala
│   └── test/src/
│       └── ContextualAbstractionsTest.scala
├── 15-advanced-types/
│   ├── src/
│   │   └── AdvancedTypes.scala
│   └── test/src/
│       └── AdvancedTypesTest.scala
├── 16-concurrency/
│   └── src/
│       └── Concurrency.scala
├── 17-type-system-mastery/
│   ├── src/
│   │   └── TypeSystemMastery.scala
│   └── test/src/
│       └── TypeSystemMasteryTest.scala
├── 18-architecture-patterns/
│   ├── src/
│   │   └── ArchitecturePatterns.scala
│   └── test/src/
│       └── ArchitecturePatternsTest.scala
├── 19-modern-syntax-safety/
│   ├── src/
│   │   └── ModernSyntaxSafety.scala
│   └── test/src/
│       └── ModernSyntaxSafetyTest.scala
└── 20-metaprogramming/
    ├── src/
    │   ├── Macros.scala
    │   └── Metaprogramming.scala
    └── test/src/
        └── MetaprogrammingTest.scala
```

## 學習建議

1. **循序漸進**：建議按照章節順序學習
2. **動手實踐**：修改範例程式碼，觀察結果
3. **閱讀註解**：每個範例都有詳細的中文註解
4. **舉一反三**：嘗試自己撰寫類似的程式
5. **參考文件**：配合 [Scala 官方文件](https://docs.scala-lang.org/)

## 進階學習資源

- [Scala 官方文件](https://docs.scala-lang.org/)
- [Scala 3 Book](https://docs.scala-lang.org/scala3/book/introduction.html)
- [Mill 官方文件](https://mill-build.com/)
- [Scala Exercises](https://www.scala-exercises.org/)
- [Programming in Scala](https://booksites.artima.com/programming_in_scala_5ed)

## 貢獻

歡迎提交 Issue 或 Pull Request 來改進這個學習專案！

## 授權

MIT License

## 致謝

本專案設計參考了 Go 語言學習專案的結構，重新設計以適應 Scala 語言特性，是一個專注於幫助初學者循序漸進學習 Scala 的教育資源。
