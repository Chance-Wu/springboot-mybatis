#!/bin/bash

echo "========================================"
echo "  AI Assistant - Starting..."
echo "========================================"
echo ""

# 检查端口是否被占用
if lsof -Pi :8091 -sTCP:LISTEN -t >/dev/null ; then
    echo "[OK] Backend service is running on port 8091"
else
    echo "[WARN] Backend service may not be running!"
    echo "Please start the Spring Boot application first."
fi

echo ""
echo "Opening AI Assistant in browser..."
echo ""

# 根据操作系统打开浏览器
case "$(uname -s)" in
    Darwin)    # macOS
        open "ai-assistant.html"
        ;;
    Linux)     # Linux
        xdg-open "ai-assistant.html" 2>/dev/null || sensible-browser "ai-assistant.html" 2>/dev/null || firefox "ai-assistant.html" 2>/dev/null &
        ;;
    *)         # Windows (WSL)
        explorer.exe "ai-assistant.html" 2>/dev/null &
        ;;
esac

echo ""
echo "========================================"
echo "  AI Assistant is ready!"
echo "========================================"
echo ""
echo "Features:"
echo "  1. Smart Chat - Natural language conversation"
echo "  2. SQL Optimizer - Optimize your SQL queries"
echo "  3. Code Generator - Generate code from description"
echo ""
