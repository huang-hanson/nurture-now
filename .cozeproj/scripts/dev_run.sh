#!/bin/bash

# 健身健身小程序启动脚本（简化版）
# uni-app (Vue 3) + Spring Boot 2.4.2

set -e

echo "=========================================="
echo "  健身健身小程序 - 启动开发环境"
echo "=========================================="
echo ""

# 检查是否在项目根目录
if [ ! -f "backend/pom.xml" ]; then
    echo "❌ 错误：请在项目根目录运行此脚本"
    exit 1
fi

# 检查 Java 是否安装
if ! command -v java &> /dev/null; then
    echo "❌ 错误：未检测到 Java"
    exit 1
fi

# 检查 Maven 是否安装
if ! command -v mvn &> /dev/null; then
    echo "❌ 错误：未检测到 Maven"
    exit 1
fi

echo "✅ 环境检查通过"
echo "Java 版本：$(java -version 2>&1 | head -n 1)"
echo "Maven 版本：$(mvn -version | head -n 1)"
echo ""

# 创建日志目录
mkdir -p logs

echo "=========================================="
echo "  步骤 1: 启动后端服务 (Spring Boot)"
echo "=========================================="

cd backend

# 启动后端（后台运行）
echo "🚀 启动后端服务..."
nohup mvn spring-boot:run > ../logs/backend.log 2>&1 &
BACKEND_PID=$!
echo "后端 PID: $BACKEND_PID"

# 保存 PID
echo $BACKEND_PID > ../logs/backend.pid

# 等待后端启动
echo "⏳ 等待后端服务启动（约 30 秒）..."
for i in {1..30}; do
    sleep 1
    echo -n "."
    if ! ps -p $BACKEND_PID > /dev/null; then
        echo ""
        echo "❌ 后端服务启动失败"
        echo "📊 请查看日志：tail -f logs/backend.log"
        exit 1
    fi
done
echo ""

# 检查后端日志，确认启动成功
if grep -q "Started FitnessApplication" ../logs/backend.log; then
    echo "✅ 后端服务启动成功"
else
    echo "⚠️  后端服务可能还在启动中，请稍后查看日志"
fi

cd ..

echo ""
echo "=========================================="
echo "  🎉 后端服务启动完成！"
echo "=========================================="
echo ""
echo "📍 后端 API 地址：http://localhost:8080"
echo "📊 后端日志：tail -f logs/backend.log"
echo ""
echo "=========================================="
echo "  前端启动说明"
echo "=========================================="
echo ""
echo "📱 前端（uni-app）需要使用 HBuilderX 启动："
echo ""
echo "1. 下载 HBuilderX：https://www.dcloud.io/hbuilderx.html"
echo ""
echo "2. 打开 HBuilderX"
echo ""
echo "3. 文件 -> 导入 -> 从本地目录导入"
echo ""
echo "4. 选择项目目录：$(pwd)/frontend"
echo ""
echo "5. 点击工具栏的\"运行\" -> \"运行到浏览器\"（推荐 Chrome）"
echo ""
echo "📄 详细说明请查看："
echo "   - README.md（项目说明）"
echo "   - DEPLOYMENT.md（部署指南）"
echo "   - UI_PREVIEW.md（UI 预览）"
echo ""
echo "=========================================="
echo "  🛑 停止服务"
echo "=========================================="
echo ""
echo "停止后端：bash .cozeproj/scripts/stop.sh"
echo ""
echo "=========================================="
