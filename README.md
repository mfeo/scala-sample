# Scala 學習範例專案

這是一個針對 Scala 初學者設計的學習專案，通過 16 個獨立章節循序漸進地學習 Scala 程式語言。本專案參考了 Go 語言學習專案的結構，重新設計以充分展現 Scala 的特色與強大功能。

## 專案特色

- ✅ 使用 Mill 建置工具（高效能、易於使用）
- ✅ 16 個獨立章節，每個都可單獨編譯執行
- ✅ 大量繁體中文註解，適合華語學習者
- ✅ 涵蓋 Scala 核心概念與最佳實踐
- ✅ 使用 Scala 3 最新語法
- ✅ 包含部分測試範例，展示 Scala 測試寫法

## 環境需求

- Java 11 或更高版本
- Mill 0.11.0 或更高版本（建議使用最新版）
- （可選）IDE：IntelliJ IDEA 或 VS Code + Metals

## 安裝 Mill

### macOS/Linux
```bash
curl -L https://github.com/com-lihaoyi/mill/releases/download/0.11.6/0.11.6 > /usr/local/bin/mill
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
6. **06-collections** - 集合操作
7. **07-map-set** - 進階集合類型

### 階段三：物件導向（第 8-9 章）
8. **08-case-class** - 資料類別
9. **09-trait-object** - 抽象與混入

### 階段四：進階主題與函數式編程（第 10-13 章）
10. **10-pattern-matching** - 進階模式匹配
11. **11-option-try** - 安全的空值處理
12. **12-error-handling** - 錯誤處理策略
13. **13-functional-programming** - 函數式程式設計核心

### 階段五：Scala 3 新特性與並行（第 14-16 章）
14. **14-contextual-abstractions** - 上下文抽象 (Given/Using)
15. **15-advanced-types** - 進階型別系統 (Enum, Union Types)
16. **16-concurrency** - 並行程式設計 (Future/Promise)

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
| 14 | Contextual Abstractions | Given/Using、Extension | ⭐⭐⭐⭐⭐ |
| 15 | Advanced Types | Enum、Union/Intersection | ⭐⭐⭐⭐⭐ |
| 16 | Concurrency | Future、Promise | ⭐⭐⭐⭐ |

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
├── build.sc              # Mill 建置設定
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
│   └── src/
│       └── ErrorHandling.scala
├── 13-functional-programming/
│   └── src/
│       └── FunctionalProgramming.scala
├── 14-contextual-abstractions/
│   └── src/
│       └── ContextualAbstractions.scala
├── 15-advanced-types/
│   └── src/
│       └── AdvancedTypes.scala
└── 16-concurrency/
    └── src/
        └── Concurrency.scala
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
