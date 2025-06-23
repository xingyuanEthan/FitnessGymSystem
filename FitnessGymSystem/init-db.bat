@echo off
echo 步骤2: 初始化数据库...
mysql -u root -p"dongchengyi123" --default-character-set=utf8mb4 < recreate-tables.sql
if %errorlevel% neq 0 (
    echo 数据库初始化失败!
    echo 请检查MySQL服务是否启动，以及用户名密码是否正确。
    exit /b %errorlevel%
)
echo 数据库初始化成功!
exit /b 0 