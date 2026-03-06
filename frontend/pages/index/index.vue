<template>
    <view class="container">
        <!-- 顶部欢迎区域 -->
        <view class="header-bg">
            <text class="welcome-text">欢迎使用健康健身</text>
            <text class="sub-text">科学的健身指导，助你达成目标</text>
        </view>

        <!-- 身体数据卡片 -->
        <view class="card data-card">
            <view class="card-header">
                <text class="title">我的身体数据</text>
            </view>
            <view class="data-row">
                <view class="data-item">
                    <text class="data-value">{{ userInfo.height || '--' }}</text>
                    <text class="data-label">身高</text>
                </view>
                <view class="divider"></view>
                <view class="data-item">
                    <text class="data-value">{{ userInfo.weight || '--' }}</text>
                    <text class="data-label">体重</text>
                </view>
                <view class="divider"></view>
                <view class="data-item">
                    <text class="data-value">{{ bmi || '--' }}</text>
                    <text class="data-label">BMI</text>
                </view>
            </view>
        </view>

        <!-- 今日概览 -->
        <view class="card">
            <text class="title">今日概览</text>
            <view class="overview-row">
                <view class="overview-item bg-emerald">
                    <text class="overview-label">摄入热量</text>
                    <text class="overview-value text-emerald">{{ todayStats.caloriesConsumed || 0 }}</text>
                    <text class="overview-unit">kcal</text>
                </view>
                <view class="overview-item bg-amber">
                    <text class="overview-label">消耗热量</text>
                    <text class="overview-value text-amber">{{ todayStats.caloriesBurned || 0 }}</text>
                    <text class="overview-unit">kcal</text>
                </view>
            </view>
        </view>

        <!-- 快捷功能 -->
        <view class="card">
            <text class="title">快捷功能</text>
            <view class="function-list">
                <view class="function-item" @click="goToPage('/pages/food/index')">
                    <view class="function-icon bg-emerald">🍎</view>
                    <text class="function-name">食物库</text>
                </view>
                <view class="function-item" @click="goToPage('/pages/exercise/index')">
                    <view class="function-icon bg-amber">🏃</view>
                    <text class="function-name">运动库</text>
                </view>
                <view class="function-item" @click="goToPage('/pages/plan/index')">
                    <view class="function-icon" style="background-color: #3B82F6;">📋</view>
                    <text class="function-name">训练计划</text>
                </view>
                <view class="function-item" @click="goToPage('/pages/checkin/index')">
                    <view class="function-icon" style="background-color: #EC4899;">✅</view>
                    <text class="function-name">签到打卡</text>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
import api from '@/api/api.js'

export default {
    data() {
        return {
            userInfo: {},
            todayStats: {}
        }
    },
    computed: {
        bmi() {
            if (this.userInfo.height && this.userInfo.weight) {
                const height = this.userInfo.height / 100
                return (this.userInfo.weight / (height * height)).toFixed(1)
            }
            return '--'
        }
    },
    onLoad() {
        this.loadUserInfo()
        this.loadTodayStats()
    },
    methods: {
        async loadUserInfo() {
            // TODO: 从缓存或登录信息获取用户ID
            const openId = 'test_open_id'
            try {
                const res = await api.getUserByOpenId(openId)
                if (res.code === 200 && res.data) {
                    this.userInfo = res.data
                }
            } catch (e) {
                console.log('加载用户信息失败', e)
            }
        },
        async loadTodayStats() {
            // TODO: 加载今日统计数据
        },
        goToPage(url) {
            uni.switchTab({
                url: url
            })
        }
    }
}
</script>

<style scoped>
.header-bg {
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    padding: 60rpx 40rpx 80rpx;
    border-radius: 0 0 60rpx 60rpx;
}

.welcome-text {
    display: block;
    font-size: 48rpx;
    font-weight: bold;
    color: #FFFFFF;
    margin-bottom: 20rpx;
}

.sub-text {
    display: block;
    font-size: 28rpx;
    color: rgba(255, 255, 255, 0.8);
}

.data-card {
    margin-top: -40rpx;
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30rpx;
}

.data-row {
    display: flex;
    justify-content: space-around;
    align-items: center;
}

.data-item {
    text-align: center;
}

.data-value {
    display: block;
    font-size: 48rpx;
    font-weight: bold;
    color: #1F2937;
    margin-bottom: 10rpx;
}

.data-label {
    font-size: 24rpx;
    color: #6B7280;
}

.divider {
    width: 2rpx;
    height: 80rpx;
    background-color: #E5E7EB;
}

.overview-row {
    display: flex;
    gap: 20rpx;
}

.overview-item {
    flex: 1;
    padding: 40rpx 20rpx;
    border-radius: 20rpx;
    text-align: center;
}

.overview-label {
    display: block;
    font-size: 24rpx;
    margin-bottom: 10rpx;
}

.overview-value {
    display: block;
    font-size: 48rpx;
    font-weight: bold;
    margin-bottom: 10rpx;
}

.overview-unit {
    font-size: 24rpx;
    color: #6B7280;
}

.function-list {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20rpx;
}

.function-item {
    text-align: center;
}

.function-icon {
    width: 100rpx;
    height: 100rpx;
    border-radius: 20rpx;
    margin: 0 auto 15rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 48rpx;
}

.function-name {
    font-size: 24rpx;
    color: #6B7280;
}
</style>
