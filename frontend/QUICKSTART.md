# 🚀 快速启动指南

## 第一步：启动后端服务

```bash
cd /Users/chenyang/code-space/springboot-mybatis
mvn spring-boot:run
```

等待看到以下日志表示启动成功：

```
Started SpringbootMybatisApplication in X.XXX seconds
```

## 第二步：启动前端页面

### 方式一：直接打开（最简单）

直接在浏览器中打开：

```
/Users/chenyang/code-space/springboot-mybatis/frontend/index.html
```

或者在终端执行：

```bash
open frontend/index.html
```

### 方式二：使用启动脚本（推荐）

**macOS/Linux:**

```bash
cd frontend
./start.sh
```

**Windows:**

```bash
cd frontend
start.bat
```

然后在浏览器访问：http://localhost:8080

### 方式三：手动启动服务器

```bash
cd frontend
python3 -m http.server 8080
```

然后访问：http://localhost:8080

## 第三步：开始使用

1. **输入查询需求**
    - 例如："查询年龄大于18岁的用户"

2. **选择生成模式**
    - 优化模式：快速生成
    - 结构化模式：详细信息（推荐）
    - RAG融合：结合业务知识

3. **点击"生成SQL"**

4. **查看结果**
    - SQL语句
    - 质量评分
    - 安全检查
    - 性能建议

## 📸 界面预览

```
┌──────────────────────────────────────────────────────┐
│         🚀 智能SQL生成系统                            │
│  基于AI的自然语言转SQL，支持RAG融合、安全控制          │
└──────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────┐
│  总请求数: 100  缓存命中率: 45%  平均响应: 1850ms     │
└──────────────────────────────────────────────────────┘

┌─────────────────────┐    ┌─────────────────────────┐
│ 💬 自然语言查询      │    │ ✨ 生成结果              │
│                     │    │                         │
│ [优化][结构化][融合] │    │ SELECT id, username     │
│                     │    │ FROM user               │
│ ┌─────────────────┐ │    │ WHERE age > 18          │
│ │查询需求...      │ │    │ LIMIT 100               │
│ └─────────────────┘ │    │                         │
│                     │    │ 🏆 优秀 (100分)          │
│ [生成SQL] [清空]    │    │ 🔒 安全检查通过          │
│                     │    │                         │
│ 快捷示例:           │    │ 类型: 简单查询           │
│ • 查询所有用户      │    │ 复杂度: 简单             │
│ • 查询年龄>18的用户 │    │ 生成时间: 1850ms         │
└─────────────────────┘    └─────────────────────────┘
```

## 🎯 快速测试

### 测试1：简单查询

```
输入：查询所有用户
预期：SELECT id, username FROM user LIMIT 100
```

### 测试2：条件查询

```
输入：查询年龄大于18岁的用户
预期：SELECT id, username, age FROM user WHERE age > 18 LIMIT 100
```

### 测试3：聚合查询

```
输入：统计每个年龄的用户数量
预期：SELECT age, COUNT(*) as count FROM user GROUP BY age
```

### 测试4：安全检查

```
输入SQL：DELETE FROM user WHERE id = 1
预期：❌ 检测到危险操作关键字: DELETE
```

## ⚡ 快捷键

- `Ctrl + Enter`：生成SQL
- `Esc`：清空输入

## 🔧 常见问题

### Q: 页面显示空白？

**A:** 检查后端是否启动，端口是否为8091

### Q: 生成失败？

**A:**

1. 检查网络连接
2. 查看浏览器控制台错误
3. 确认后端服务正常

### Q: 样式异常？

**A:** 清除浏览器缓存，刷新页面

### Q: 如何修改API地址？

**A:** 编辑 `index.html` 第578行：

```javascript
const API_BASE = 'http://localhost:8091/smart-sql';
```

## 📱 移动端使用

页面已支持响应式设计，可以在手机和平板上使用。

## 🎉 开始体验

现在你已经准备好了，开始体验智能SQL生成的便利吧！

如有问题，请查看 `frontend/README.md` 获取更多信息。
