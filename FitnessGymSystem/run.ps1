Write-Host "启动健身房管理系统..." -ForegroundColor Green
Write-Host ""

# 检查是否已编译
if (!(Test-Path "out")) {
    Write-Host "错误：未找到编译文件，请先运行 .\compile.ps1" -ForegroundColor Red
    Read-Host "按回车键继续"
    exit 1
}

# 设置Java Home,如果你的环境遍量没有配置
# $env:JAVA_HOME = "C:\Program Files\Java\jdk-11"
# $env:PATH = "$env:JAVA_HOME\bin;" + $env:PATH

$outDir = "out"
$libDir = "lib"
$mainClass = "ui.MainFrame"

# 自动获取lib下所有jar包
$classpath = Get-ChildItem -Path $libDir -Filter *.jar | ForEach-Object { $_.FullName } | Join-String -Separator ';'
$classpath = "$outDir;$classpath"

# 运行
Write-Host "步骤2: 启动程序..."
java -cp $classpath $mainClass

if ($LASTEXITCODE -ne 0) {
    Write-Host "程序启动失败!" -ForegroundColor Red
    exit 1
}

Write-Host "程序已退出." -ForegroundColor Green
exit 0 