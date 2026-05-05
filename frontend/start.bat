@echo off
chcp 65001 >nul

echo ==========================================
echo   智能SQL生成系统 - 前端启动
echo ==========================================
echo.

REM 检查是否安装了Python
where python >nul 2>nul
if %errorlevel% equ 0 (
    echo ✅ 检测到 Python
    set PYTHON_CMD=python
) else (
    where python3 >nul 2>nul
    if %errorlevel% equ 0 (
        echo ✅ 检测到 Python3
        set PYTHON_CMD=python3
    ) else (
        echo ❌ 未检测到 Python，请先安装 Python3
        pause
        exit /b 1
    )
)

echo.
echo 📁 前端目录: %~dp0
echo.
echo 🚀 正在启动本地服务器...
echo.
echo 访问地址: http://localhost:8080
echo.
echo 按 Ctrl+C 停止服务器
echo.
echo ==========================================
echo.

REM 启动HTTP服务器
cd /d "%~dp0"
%PYTHON_CMD% -m http.server 8080

pause
