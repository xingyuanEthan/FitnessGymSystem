@echo off
chcp 65001 >nul
cls

echo ========================================
echo    高校健身房管理系统 - 一键启动
echo ========================================
echo.

REM 步骤1: 调用编译脚本
echo 正在编译项目...
call compile.bat
if %errorlevel% neq 0 (
    echo.
    echo 编译步骤失败，请检查错误信息。
    pause
    exit /b 1
)
echo 编译完成!
echo.

REM 步骤2: 调用数据库初始化脚本
echo 正在初始化数据库...
call init-db.bat
if %errorlevel% neq 0 (
    echo.
    echo 数据库初始化失败，请检查错误信息。
    pause
    exit /b 1
)
echo 数据库准备就绪!
echo.

REM 步骤3: 调用运行脚本
echo 正在启动应用程序...
call run.bat
if %errorlevel% neq 0 (
    echo.
    echo 程序运行失败，请检查错误信息。
    pause
    exit /b 1
)

echo.
echo 程序已结束.
pause 