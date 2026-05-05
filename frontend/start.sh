#!/bin/bash

# 智能SQL生成系统 - 前端启动脚本

echo "=========================================="
echo "  智能SQL生成系统 - 前端启动"
echo "=========================================="
echo ""

# 检查是否安装了Python3
if command -v python3 &> /dev/null; then
    echo "✅ 检测到 Python3"
    PYTHON_CMD="python3"
elif command -v python &> /dev/null; then
    echo "✅ 检测到 Python"
    PYTHON_CMD="python"
else
    echo "❌ 未检测到 Python，请先安装 Python3"
    exit 1
fi

# 进入前端目录
cd "$(dirname "$0")"

echo ""
echo "📁 前端目录: $(pwd)"
echo ""
echo "🚀 正在启动本地服务器..."
echo ""
echo "访问地址: http://localhost:8080"
echo ""
echo "按 Ctrl+C 停止服务器"
echo ""
echo "=========================================="
echo ""

# 启动HTTP服务器
$PYTHON_CMD -m http.server 8080
