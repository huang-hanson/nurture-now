#!/bin/bash

# 健身健身小程序停止脚本

set -e

echo "=========================================="
echo "  健身健身小程序 - 停止服务"
echo "=========================================="

# 检查是否在项目根目录
if [ ! -d "logs" ]; then
    echo "❌ 错误：未找到日志目录，服务可能未启动"
    exit 1
fi

echo ""

# 停止后端服务
if [ -f "logs/backend.pid" ]; then
    BACKEND_PID=$(cat logs/backend.pid)
    if ps -p $BACKEND_PID > /dev/null 2>&1; then
        echo "🛑 正在停止后端服务 (PID: $BACKEND_PID)..."
        kill $BACKEND_PID
        sleep 2
        if ps -p $BACKEND_PID > /dev/null 2>&1; then
            echo "⚠️  后端服务未正常响应，强制停止..."
            kill -9 $BACKEND_PID
        fi
        echo "✅ 后端服务已停止"
    else
        echo "⚠️  后端服务未运行"
    fi
    rm -f logs/backend.pid
else
    echo "⚠️  未找到后端 PID 文件"
fi

echo ""

# 停止前端服务
if [ -f "logs/frontend.pid" ]; then
    FRONTEND_PID=$(cat logs/frontend.pid)
    if ps -p $FRONTEND_PID > /dev/null 2>&1; then
        echo "🛑 正在停止前端服务 (PID: $FRONTEND_PID)..."
        kill $FRONTEND_PID
        sleep 2
        if ps -p $FRONTEND_PID > /dev/null 2>&1; then
            echo "⚠️  前端服务未正常响应，强制停止..."
            kill -9 $FRONTEND_PID
        fi
        echo "✅ 前端服务已停止"
    else
        echo "⚠️  前端服务未运行"
    fi
    rm -f logs/frontend.pid
else
    echo "⚠️  未找到前端 PID 文件"
fi

echo ""
echo "=========================================="
echo "  ✅ 所有服务已停止"
echo "=========================================="
