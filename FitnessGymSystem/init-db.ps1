Write-Host "初始化健身房管理系统数据库..." -ForegroundColor Green
Write-Host ""

# 检查是否已编译
if (!(Test-Path "out")) {
    Write-Host "错误：未找到编译文件，请先运行 .\compile.ps1" -ForegroundColor Red
    Read-Host "按回车键继续"
    exit 1
}

# 运行数据库初始化
try {
    & java -cp "out;lib/mysql-connector-j-9.3.0.jar" TestDatabase
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "数据库初始化成功！" -ForegroundColor Green
        Write-Host "现在可以运行 .\run.ps1 启动程序" -ForegroundColor Yellow
    } else {
        Write-Host ""
        Write-Host "数据库初始化失败，请检查：" -ForegroundColor Red
        Write-Host "1. MySQL服务是否启动" -ForegroundColor Yellow
        Write-Host "2. 数据库连接配置是否正确" -ForegroundColor Yellow
        Write-Host "3. 数据库fitness是否存在" -ForegroundColor Yellow
    }
} catch {
    Write-Host ""
    Write-Host "初始化失败: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Read-Host "按回车键继续" 