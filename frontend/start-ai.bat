@echo off
echo ========================================
echo   AI Assistant - Starting...
echo ========================================
echo.

REM 检查端口是否被占用
netstat -ano | findstr :8091 >nul
if %errorlevel% equ 0 (
    echo [OK] Backend service is running on port 8091
) else (
    echo [WARN] Backend service may not be running!
    echo Please start the Spring Boot application first.
)

echo.
echo Opening AI Assistant in browser...
echo.

REM 打开浏览器
start "" "ai-assistant.html"

echo.
echo ========================================
echo   AI Assistant is ready!
echo ========================================
echo.
echo Features:
echo   1. Smart Chat - Natural language conversation
echo   2. SQL Optimizer - Optimize your SQL queries
echo   3. Code Generator - Generate code from description
echo.
echo Press any key to exit...
pause >nul
