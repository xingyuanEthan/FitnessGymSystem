@echo off
rem 编译项目的脚本
set "JAVA_HOME=C:\Program Files\Java\jdk-11"
set "PATH=%JAVA_HOME%\bin;%PATH%"

set "SRC_DIR=src"
set "OUT_DIR=out"
set "LIB_DIR=lib"
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

rem 设置编译后的 classpath
set "CP=%OUT_DIR%;%CP%"

echo 步骤1: 编译项目...
if exist "%OUT_DIR%" (
    rmdir /s /q "%OUT_DIR%"
)
mkdir "%OUT_DIR%"

rem 查找所有 java 文件并编译
javac -d %OUT_DIR% -cp %CP% -encoding UTF-8 src/dao/*.java src/db/*.java src/entity/*.java src/ui/*.java src/TestDatabase.java src/TestDB.java
if %errorlevel% neq 0 (
    echo 编译失败!
    exit /b %errorlevel%
)
echo 编译成功!
echo.
exit /b 0 