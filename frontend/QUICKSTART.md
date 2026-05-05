# 🚀 前端项目快速启动指南

## 📁 项目结构

```
frontend/
├── index.html                    # 智能SQL生成系统（主页面）
├── ai-assistant.html             # AI智能助手（新增）
├── start.bat                     # SQL系统启动脚本（Windows）
├── start.sh                      # SQL系统启动脚本（Linux/Mac）
├── start-ai.bat                  # AI助手启动脚本（Windows）
├── start-ai.sh                   # AI助手启动脚本（Linux/Mac）
├── README.md                     # 详细说明文档
├── AI_ASSISTANT_README.md        # AI助手使用指南
├── AI_ASSISTANT_PREVIEW.md       # AI助手界面预览
└── AI_ASSISTANT_SUMMARY.md       # AI助手开发总结
```

---

## 🎯 两个核心功能

### 1. 🗄️ 智能SQL生成系统

**文件：** `index.html`

**功能：**

- 三种生成模式（优化、结构化、RAG融合）
- 实时统计面板
- 质量评分展示
- SQL安全检查
- 性能建议

**启动方式：**

```bash
# Windows
start.bat

# macOS/Linux
./start.sh

# 或直接打开
open index.html
```

**访问地址：** `http://localhost:8091/smart-sql`

---

### 2. 🤖 AI智能助手

**文件：** `ai-assistant.html`

**功能：**

- 💬 智能对话（支持Markdown）
- 🗄️ SQL优化（左右对比）
- 💻 代码生成（一键复制）

**启动方式：**

```bash
# Windows
start-ai.bat

# macOS/Linux
./start-ai.sh

# 或直接打开
open ai-assistant.html
```

**访问地址：** `http://localhost:8091/ai`

---

## ⚡ 快速启动

### 前置条件

✅ Java环境已安装  
✅ Maven已安装  
✅ 后端服务配置完成

### 步骤一：启动后端服务

```bash
cd /Users/chenyang/code-space/springboot-mybatis
mvn spring-boot:run
```

等待看到以下日志表示启动成功：
```
Started SpringbootMybatisApplication in X.XXX seconds
```

### 步骤二：启动前端页面

#### 选项A：智能SQL生成系统

```bash
cd frontend

# Windows
start.bat

# macOS/Linux
./start.sh
```

#### 选项B：AI智能助手

```bash
cd frontend

# Windows
start-ai.bat

# macOS/Linux
./start-ai.sh
```

### 步骤三：开始使用

浏览器会自动打开对应页面，即可开始使用！

---

## 🌐 手动访问

如果自动打开失败，可以手动在浏览器中访问：

### 智能SQL生成系统
```
file:///Users/chenyang/code-space/springboot-mybatis/frontend/index.html
```

### AI智能助手
```
file:///Users/chenyang/code-space/springboot-mybatis/frontend/ai-assistant.html
```

---

## 🔧 常见问题

### Q1: 页面显示空白？

**原因：** 后端服务未启动

**解决：**

```bash
# 检查后端是否运行
netstat -ano | grep 8091

# 如果没有，启动后端
mvn spring-boot:run
```

### Q2: 请求失败或超时？

**原因：** API地址配置错误

**解决：**
编辑对应的HTML文件，确认API地址正确：

```javascript
// index.html 第578行
const API_BASE = 'http://localhost:8091/smart-sql';

// ai-assistant.html 第534行
const API_BASE = 'http://localhost:8091/ai';
```

### Q3: 跨域错误？

**原因：** CORS配置问题

**解决：** 后端已配置CORS，检查 `CorsConfig.java` 是否存在

### Q4: 样式加载失败？

**原因：** CDN资源无法访问

**解决：**

- 检查网络连接
- 尝试刷新页面
- 清除浏览器缓存

---

## 📱 移动端访问

两个页面都支持响应式设计，可以在移动设备上访问。

**推荐方式：**

1. 确保手机和电脑在同一局域网
2. 查看电脑IP地址：`ifconfig` (Mac/Linux) 或 `ipconfig` (Windows)
3. 在手机浏览器访问：`http://电脑IP:端口/frontend/xxx.html`

---

## 🎨 界面预览

### 智能SQL生成系统

```
┌─────────────────────────────────────┐
│     智能SQL生成系统                  │
├──────────┬──────────────────────────┤
│ 输入区    │ 结果展示区                │
│          │                          │
│ [生成SQL] │ 生成的SQL                 │
│          │ 质量评分 ⭐⭐⭐⭐⭐      │
│          │ 安全检查 ✅               │
└──────────┴──────────────────────────┘
```

### AI智能助手

```
┌─────────────────────────────────────┐
│     🤖 AI 智能助手                   │
├──────────┬──────────────────────────┤
│ 💬 对话   │                          │
│ 🗄️ SQL   │   内容显示区域            │
│ 💻 代码   │                          │
│          │                          │
└──────────┴──────────────────────────┘
```

---

## 📊 功能对比

| 特性         | SQL生成系统 | AI助手 |
|------------|---------|------|
| 自然语言转SQL   | ✅       | ❌    |
| 多模式生成      | ✅       | ❌    |
| 质量评分       | ✅       | ❌    |
| 智能对话       | ❌       | ✅    |
| SQL优化      | ⚠️ 基础   | ✅ 专业 |
| 代码生成       | ❌       | ✅    |
| Markdown渲染 | ❌       | ✅    |
| 对话历史       | ❌       | ✅    |

**建议：**

- 需要生成SQL → 使用 **SQL生成系统**
- 需要聊天问答 → 使用 **AI助手**
- 需要优化SQL → 两者都可，推荐 **AI助手**
- 需要生成代码 → 使用 **AI助手**

---

## 🔗 相关文档

- 📖 SQL系统详细说明：[README.md](README.md)
- 📖 AI助手使用指南：[AI_ASSISTANT_README.md](AI_ASSISTANT_README.md)
- 🎨 AI助手界面预览：[AI_ASSISTANT_PREVIEW.md](AI_ASSISTANT_PREVIEW.md)
- 📝 AI助手开发总结：[AI_ASSISTANT_SUMMARY.md](AI_ASSISTANT_SUMMARY.md)

---

## 💡 使用技巧

### SQL生成系统

1. **选择合适模式：**
   - 简单查询 → 优化模式
   - 需要详细分析 → 结构化模式
   - 复杂业务场景 → RAG融合模式

2. **利用示例：**
   - 点击"示例"按钮查看常用查询
   - 修改示例快速生成自己的SQL

3. **安全检查：**
   - 执行前务必进行安全检查
   - 注意红色警告信息

### AI助手

1. **智能对话：**
   - 问题越具体，回答越准确
   - 支持多轮对话，记住上下文
   - 使用 Ctrl+Enter 快速发送

2. **SQL优化：**
   - 提供完整的SQL语句
   - 查看左右对比，理解优化点
   - 参考优化建议改进写法

3. **代码生成：**
   - 详细描述需求（技术栈、功能、约束）
   - 指定代码风格和规范
   - 生成后人工审查和调整

---

## 🚀 性能提示

### 响应时间

- **简单请求：** 1-3秒
- **中等复杂：** 3-10秒
- **复杂请求：** 10-30秒

### 优化建议

1. **网络优化：**
   - 使用本地模型（Ollama）减少延迟
   - 确保网络稳定

2. **缓存利用：**
   - 相同问题会命中缓存
   - 定期清理无效缓存

3. **并发控制：**
   - 避免同时发送多个请求
   - 等待当前请求完成

---

## 🎉 开始使用

现在你已经了解了所有启动方式和使用方法，开始体验吧！

```bash
# 1. 启动后端
mvn spring-boot:run

# 2. 启动前端（二选一）
cd frontend
./start.sh          # SQL生成系统
# 或
./start-ai.sh       # AI助手

# 3. 在浏览器中使用
```

**祝你使用愉快！** 🚀
