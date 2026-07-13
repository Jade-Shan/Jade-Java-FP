# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 构建与测试

```bash
# 编译
mvn compile

# 运行所有测试
mvn test

# 运行单个测试类
mvn -Dtest=MonadTest test
mvn -Dtest=TailCallOptimizationTest test

# 打包（含源码和 javadoc）
mvn package

# 发布到 Maven Central（需要 GPG 密钥和 Sonatype token）
mvn deploy
```

项目使用 **Java 21** + **Maven**，依赖 JUnit 4.13.1（不是 JUnit 5），测试写在 `src/test/java/` 下。

## 架构概览

`net.jadedungeon.javautil` —— 一个 Java 函数式编程工具库，核心是将 Haskell/ML 风格的函数式抽象带到 Java 中。

### 核心类型层级

```
Functor<T>  ──map──►  Monad<T>  ──filter──►  MFilterable<T>
               │
               ├── flatMap
               │
    ┌──────────┼──────────┐
    │          │          │
  Option<T>  Seq<T>   Identity<T> / Promise<T>
  (单值容器)  (不可变链表)  (简单包装)
```

- **`Functor<T>`** / **`Monad<T>`** — 定义了 `map` 和 `flatMap`，是整个库的根基。`Monad` 继承了 `Functor`。
- **`SemiGroup<T>`** / **`Monoid<T>`** — 代数结构接口：`op(T, T)` 和 `getZero()`。
- **`MonadHelper`** — 提供 `liftM2` ~ `liftM5`，将普通多参数函数提升到 Monad 上下文中运行。
- **`Curryable`** — `curry` / `uncurry` 工具方法。

### 单值容器 (`collection/solitary/`)

| 类型 | 说明 |
|------|------|
| `Option<T>` (abstract) | Maybe/Optional Monad，工厂方法 `Option.of(val)` 自动根据 null 返回 `Some` 或 `None` |
| `Some<T>` | 有值状态 |
| `None<T>` | 无值状态，单例 `None.NONE` |
| `Identity<T>` | Identity Monad，最简单的 Monad 实现 |
| `Promise<T>` | 与 Identity 类似的值包装器 |

所有类型都用 `static of()` 工厂方法构造，构造函数私有。`None` 和 `Nil` 用单例模式。

### 不可变链表 (`collection/multi/`)

| 类型 | 说明 |
|------|------|
| `Seq<T>` (abstract) | 不可变持久化链表，实现了 `Monad<T>`。**关键：`map` 和 `flatMap` 内部使用了 `CallStack` 做 TCO**，避免大列表栈溢出 |
| `Cons<T>` | 非空节点（head + tail），不可变 |
| `Nil<T>` | 空列表单例 `Nil.NIL` |
| `BinSeq<L, R>` | 双序列容器，根据 `Either` 将元素分入左右两个 Seq |

`Seq.attach(element)` 在头部插入（O(1)），返回新引用——原 Seq 不变。`Seq.of(Iterable)` 从 Java 集合创建 Seq。

### 尾调用优化 (`tco/`)

`CallStack<T>` 是蹦床（Trampoline）模式的实现：

- `StackBottom<T>` — 递归终点，持有即刻值
- `StackFrame<T>` — 中间栈帧，持有 `Supplier<CallStack<T>>` 用于惰性求值
- `eval()` 方法将递归链展开为迭代循环，避免 `StackOverflowError`

`Seq.map` 和 `Seq.flatMap` 依赖这个机制来处理大数据量的递归操作。

### 数据结构 (`collection/struct/`)

- **`Either<L, R>`** — 可区分联合类型，有 `Left<L,R>` 和 `Right<L,R>` 两个实现。提供了 `map`（双函数）、`isMatch`（双谓词）和 `left()`/`right()`（返回 `Optional`）
- **`Tuple2/3/4/5`** — 元组类型，不可变，有 `equals`/`hashCode`

### JSON 工具 (`json/`)

基于 `org.json` 库的辅助工具，统一使用 `Consumer<Exception> errHandler` 模式处理异常：

- **读取方向**：`JsonValueField`（标量值）、`JsonArrayField`（数组 → List）、`JsonObjField`（嵌套对象）
- **写入方向**：`ObjValueField`（标量值）、`ObjListField`（List → JSONArray）
- **辅助**：`JsonArr2Stream`（JSONArray → Stream）、`BaseJsonTool`（字符串 → JSONObject）

### 代理工具 (`Proxy/`)

- `JadeProxyFactory` — JDK 动态代理（基于接口）
- `JadeProxyCglib` — CGLib 代理（基于类继承，用于没有接口的目标类）

### 通用工具 (`common/`)

- `TimeUtils` — Date ↔ LocalDate ↔ LocalDateTime 互转，默认时区 `Asia/Shanghai`

## 代码约定

- 所有容器类型是**不可变的**（immutable），操作方法返回新实例
- 工厂方法统一使用 `static of(...)` / `static empty()`，构造函数为 private/protected
- `Consumer<Exception>` 作为可选错误处理器，`null` 时直接抛出异常
- 方法签名中 `// nl` 注释表示此处换行是为满足行宽限制
- 大量使用 `@SuppressWarnings("unchecked")` 处理泛型擦除
- 单例模式：`None.NONE`、`Nil.NIL`
