@echo off
echo 正在编译多项式加法程序...
gcc -o polynomial polynomial.c
if %errorlevel% equ 0 (
    echo 编译成功！
    echo.
    echo 正在运行程序...
    echo.
    polynomial.exe
    echo.
    echo 程序执行完毕。
) else (
    echo 编译失败！请检查代码或确保已安装gcc编译器。
)
pause 