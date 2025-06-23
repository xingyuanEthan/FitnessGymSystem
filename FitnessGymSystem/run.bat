@echo off
rem 运行主程序的脚本
set "JAVA_HOME=C:\Program Files\Java\jdk-11"
set "PATH=%JAVA_HOME%\bin;%PATH%"

set "OUT_DIR=out"
set "LIB_DIR=lib"
set "MAIN_CLASS=ui.MainFrame"
set "CP="

rem 检查 LIB_DIR 是否存在
if not exist "%LIB_DIR%" (
    echo 错误: lib 目录不存在.
    goto :eof
)

rem 拼接 classpath
for %%i in ("%LIB_DIR%\*.jar") do (
    if defined CP (
        set "CP=!CP!;%%i"
    ) else (
        set "CP=%%i"
    )
)

set "CP=%OUT_DIR%;%CP%"

echo 步骤2: 启动程序...
java -cp "%CP%" %MAIN_CLASS%

if %errorlevel% neq 0 (
    echo 程序启动失败!
    exit /b %errorlevel%
)

echo 程序已退出.
exit /b 0 