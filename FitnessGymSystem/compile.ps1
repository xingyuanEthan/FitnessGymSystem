Write-Host "正在编译健身房管理系统..." -ForegroundColor Green
Write-Host ""

# 创建输出目录
if (!(Test-Path "out")) {
    New-Item -ItemType Directory -Path "out" | Out-Null
}

# 设置Java Home,如果你的环境遍量没有配置
# $env:JAVA_HOME = "C:\Program Files\Java\jdk-11"
# $env:PATH = "$env:JAVA_HOME\bin;" + $env:PATH

$srcDir = "src"
$outDir = "out"
$libDir = "lib"

# 自动获取lib下所有jar包
$classpath = Get-ChildItem -Path $libDir -Filter *.jar | ForEach-Object { $_.FullName } | Join-String -Separator ';'
$classpath = "$outDir;$classpath"

# 获取所有.java源文件
$sourceFiles = Get-ChildItem -Path $srcDir -Recurse -Filter *.java | ForEach-Object { $_.FullName }

# 编译
Write-Host "步骤1: 编译项目..."
if (Test-Path $outDir) {
    Remove-Item -Recurse -Force $outDir
}
New-Item -ItemType Directory -Path $outDir

javac -d $outDir -cp $classpath -encoding UTF-8 $sourceFiles

if ($LASTEXITCODE -ne 0) {
    Write-Host "编译失败!" -ForegroundColor Red
    exit 1
}

Write-Host "编译成功!" -ForegroundColor Green
exit 0

Write-Host ""
Read-Host "按回车键继续" 