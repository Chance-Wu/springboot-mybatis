# 智能SQL生成系统 - 前端页面

## 📖 概述

这是一个基于Vue 3和Element Plus构建的现代化前端页面，用于展示智能SQL生成功能。

## ✨ 功能特性

### 1. **三种生成模式**

- **优化模式**：带缓存、重试、质量评估
- **结构化模式**：返回完整元数据和详细分析
- **RAG融合模式**：结合业务知识和数据库Schema

### 2. **实时统计面板**

- 总请求数
- 缓存命中率
- 平均响应时间
- 成功率

### 3. **质量评分展示**

- 格式规范（20分）
- 最佳实践（30分）
- 性能考虑（30分）
- 安全性（20分）
- 总体评级（优秀/良好/一般/需改进）

### 4. **安全检查工具**

- 实时SQL安全性检测
- 危险操作拦截
- SQL注入防护

### 5. **性能建议**

- 高优先级警告
- 中优先级优化建议
- 低优先级提示

### 6. **SQL分析**

- SQL类型识别（简单/JION/聚合等）
- 复杂度评估
- 使用的表和字段
- 生成时间

## 🚀 快速开始

### 方式一：直接打开（推荐）

```bash
# 1. 启动后端服务
cd /Users/chenyang/code-space/springboot-mybatis
mvn spring-boot:run

# 2. 在浏览器中打开前端页面
open frontend/index.html
```

### 方式二：使用本地服务器

```bash
# 安装http-server
npm install -g http-server

# 进入前端目录
cd frontend

# 启动服务器
http-server -p 8080

# 访问
open http://localhost:8080
```

### 方式三：使用Python服务器

```bash
cd frontend
python3 -m http.server 8080
open http://localhost:8080
```

## 📱 界面说明

### 顶部统计面板

```
┌─────────────────────────────────────────────┐
│  总请求数    缓存命中率   平均响应   成功率   │
│    100        45.00%      1850ms     98%     │
└─────────────────────────────────────────────┘
```

### 左侧输入区

```
┌─────────────────────────────────────────────┐
│  💬 自然语言查询                             │
├─────────────────────────────────────────────┤
│  [优化模式] [结构化模式] [RAG融合]           │
│                                             │
│  ┌───────────────────────────────────────┐  │
│  │ 请输入您的查询需求...                  │  │
│  │                                       │  │
│  └───────────────────────────────────────┘  │
│                                             │
│  [生成SQL] [清空] [示例]                    │
│                                             │
│  快捷示例：                                 │
│  [查询所有用户] [查询年龄大于18岁的用户]...  │
└─────────────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│  🔒 SQL安全检查                              │
├─────────────────────────────────────────────┤
│  ┌───────────────────────────────────────┐  │
│  │ 输入SQL语句进行安全检查...             │  │
│  └───────────────────────────────────────┘  │
│  [检查安全性]                               │
└─────────────────────────────────────────────┘
```

### 右侧结果区

```
┌─────────────────────────────────────────────┐
│  ✨ 生成结果                         ⚡缓存  │
├─────────────────────────────────────────────┤
│  生成的SQL：                                 │
│  ┌───────────────────────────────────────┐  │
│  │ SELECT id, username FROM user         │  │
│  │ WHERE age > 18 LIMIT 100              │  │
│  └───────────────────────────────────────┘  │
│                                             │
│  质量评分：                                  │
│  ┌────────┐ ┌────────┐                     │
│  │格式20/20│ │实践30/30│                    │
│  └────────┘ └────────┘                     │
│  ┌────────┐ ┌────────┐                     │
│  │性能30/30│ │安全20/20│                    │
│  └────────┘ └────────┘                     │
│           🏆 优秀 (100分)                   │
│                                             │
│  安全检查：                                  │
│  🔒 SQL安全检查通过                          │
│                                             │
│  SQL分析：                                   │
│  类型: 简单查询  复杂度: 简单                │
│  使用的表: [user]                           │
│  生成时间: 1850ms                            │
└─────────────────────────────────────────────┘
```

## 🎯 使用示例

### 示例1：简单查询

1. 选择"结构化模式"
2. 输入："查询年龄大于18岁的用户"
3. 点击"生成SQL"

**结果：**

```sql
SELECT id, username, age FROM user WHERE age > 18 LIMIT 100
```

**质量评分：** 🏆 优秀 (100分)

### 示例2：聚合查询

1. 输入："统计每个年龄的用户数量"
2. 点击"生成SQL"

**结果：**

```sql
SELECT age, COUNT(*) as count FROM user GROUP BY age
```

### 示例3：JOIN查询

1. 输入："查询每个用户的订单数量"
2. 点击"生成SQL"

**结果：**

```sql
SELECT u.username, COUNT(o.id) as order_count
FROM user u
LEFT JOIN orders o ON u.id = o.user_id
GROUP BY u.username
LIMIT 100
```

### 示例4：安全检查

1. 在"SQL安全检查"区域输入：
   ```sql
   DELETE FROM user WHERE id = 1
   ```
2. 点击"检查安全性"

**结果：** ❌ 检测到危险操作关键字: DELETE，不允许执行

## 🎨 界面特色

### 1. **渐变背景**

紫色渐变背景，现代感十足

### 2. **卡片悬浮效果**

鼠标悬停时卡片微微上浮

### 3. **响应式布局**

自适应不同屏幕尺寸

### 4. **加载动画**

生成过程中显示加载图标

### 5. **颜色编码**

- 🟢 绿色：成功、安全、优秀
- 🟡 黄色：警告、良好
- 🔴 红色：错误、危险、需改进

### 6. **图标系统**

使用Element Plus Icons，直观易懂

## 📊 数据可视化

### 质量评分环形图（可扩展）

```javascript
// 可以集成ECharts显示环形图
const qualityChart = echarts.init(document.getElementById('quality-chart'));
qualityChart.setOption({
    series: [{
        type: 'pie',
        data: [
            { value: 20, name: '格式' },
            { value: 30, name: '实践' },
            { value: 30, name: '性能' },
            { value: 20, name: '安全' }
        ]
    }]
});
```

### 统计趋势图（可扩展）

```javascript
// 可以显示请求量趋势
const trendChart = echarts.init(document.getElementById('trend-chart'));
trendChart.setOption({
    xAxis: { type: 'category', data: ['Mon', 'Tue', 'Wed'] },
    yAxis: { type: 'value' },
    series: [{ data: [120, 200, 150], type: 'line' }]
});
```

## 🔧 自定义配置

### 修改API地址

编辑 `index.html` 第578行：

```javascript
const API_BASE = 'http://localhost:8091/smart-sql';
```

### 修改主题颜色

编辑CSS部分：

```css
body {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
```

### 添加更多示例

编辑 `examples` 数组：

```javascript
const examples = [
    '查询所有用户',
    '查询年龄大于18岁的用户',
    // 添加新示例...
];
```

## 📱 移动端适配

页面已支持响应式设计，在移动设备上会自动调整为单列布局。

## 🚀 性能优化

### 1. **CDN加速**

使用unpkg CDN加载依赖：

- Vue 3
- Element Plus
- Axios

### 2. **缓存利用**

- 浏览器缓存静态资源
- API响应缓存（后端实现）

### 3. **按需加载**

只加载必要的组件和图标

## 🔮 未来扩展

### 1. **SQL执行**

```javascript
// 添加执行按钮
const executeSql = async () => {
    const response = await axios.post(`${API_BASE}/execute`, {
        sql: result.sql
    });
    // 显示执行结果
};
```

### 2. **历史记录**

```javascript
// 保存查询历史
const history = JSON.parse(localStorage.getItem('sql-history') || '[]');
history.push({ question, sql, timestamp: Date.now() });
localStorage.setItem('sql-history', JSON.stringify(history));
```

### 3. **SQL格式化**

```javascript
// 集成sql-formatter
import sqlFormatter from 'sql-formatter';
const formattedSql = sqlFormatter.format(result.sql);
```

### 4. **导出功能**

```javascript
// 导出为文件
const exportSql = () => {
    const blob = new Blob([result.sql], { type: 'text/plain' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'query.sql';
    a.click();
};
```

## 🐛 常见问题

### Q1: 页面空白？

**A:** 检查后端服务是否启动，确认API地址正确。

### Q2: 生成失败？

**A:** 打开浏览器控制台查看错误信息，检查网络连接。

### Q3: 样式异常？

**A:** 清除浏览器缓存，重新加载页面。

### Q4: 跨域问题？

**A:** 确保后端已配置CORS（项目已配置）。

## 📞 技术支持

如有问题，请检查：

1. 后端服务是否正常运行
2. 端口是否正确（默认8091）
3. 浏览器控制台是否有错误

## 📄 许可证

MIT License

---

**享受智能SQL生成的便利！** 🎉
