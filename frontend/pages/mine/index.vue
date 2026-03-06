<template>
    <view class="container">
        <!-- 用户信息卡片 -->
        <view class="card user-card">
            <view class="user-info">
                <view class="avatar">👤</view>
                <view class="user-details">
                    <text class="username">{{ userInfo.nickname || '未登录' }}</text>
                    <text class="user-tip">完善个人信息开始健身之旅</text>
                </view>
            </view>
            <button class="btn-primary" @click="editProfile">完善个人信息</button>
        </view>

        <!-- 功能列表 -->
        <view class="card">
            <view class="menu-item" @click="goToPage('/pages/plan/index')">
                <text class="menu-icon">📋</text>
                <text class="menu-name">训练计划</text>
                <text class="menu-arrow">›</text>
            </view>
            <view class="menu-item" @click="showStats">
                <text class="menu-icon">📊</text>
                <text class="menu-name">数据统计</text>
                <text class="menu-arrow">›</text>
            </view>
            <view class="menu-item" @click="showSettings">
                <text class="menu-icon">⚙️</text>
                <text class="menu-name">设置</text>
                <text class="menu-arrow">›</text>
            </view>
        </view>

        <!-- 运动目标设置 -->
        <view class="card">
            <text class="title">运动量目标</text>
            <view class="goal-setting-list">
                <view class="goal-setting-item" @click="setGoal('daily')">
                    <text class="goal-icon">📅</text>
                    <view class="goal-info">
                        <text class="goal-title">每日目标</text>
                        <text class="goal-value">{{ dailyGoal.durationMinutes || 0 }}分钟 / {{ dailyGoal.calories || 0 }}kcal</text>
                    </view>
                    <text class="menu-arrow">›</text>
                </view>
                <view class="goal-setting-item" @click="setGoal('weekly')">
                    <text class="goal-icon">📆</text>
                    <view class="goal-info">
                        <text class="goal-title">每周目标</text>
                        <text class="goal-value">{{ weeklyGoal.durationMinutes || 0 }}分钟 / {{ weeklyGoal.calories || 0 }}kcal</text>
                    </view>
                    <text class="menu-arrow">›</text>
                </view>
                <view class="goal-setting-item" @click="setGoal('monthly')">
                    <text class="goal-icon">🗓️</text>
                    <view class="goal-info">
                        <text class="goal-title">每月目标</text>
                        <text class="goal-value">{{ monthlyGoal.durationMinutes || 0 }}分钟 / {{ monthlyGoal.calories || 0 }}kcal</text>
                    </view>
                    <text class="menu-arrow">›</text>
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
            userId: 1,
            userInfo: {},
            dailyGoal: {},
            weeklyGoal: {},
            monthlyGoal: {}
        }
    },
    onLoad() {
        this.loadUserInfo()
        this.loadGoals()
    },
    methods: {
        async loadUserInfo() {
            // TODO: 加载用户信息
        },
        async loadGoals() {
            try {
                const res = await api.getUserGoals(this.userId)
                if (res.code === 200) {
                    res.data.forEach(goal => {
                        if (goal.goalType === 'daily') this.dailyGoal = goal
                        else if (goal.goalType === 'weekly') this.weeklyGoal = goal
                        else if (goal.goalType === 'monthly') this.monthlyGoal = goal
                    })
                }
            } catch (e) {
                console.log('加载目标失败', e)
            }
        },
        editProfile() {
            uni.showToast({
                title: '完善信息功能开发中',
                icon: 'none'
            })
        },
        showStats() {
            uni.showToast({
                title: '数据统计功能开发中',
                icon: 'none'
            })
        },
        showSettings() {
            uni.showToast({
                title: '设置功能开发中',
                icon: 'none'
            })
        },
        setGoal(type) {
            uni.showToast({
                title: `设置${type === 'daily' ? '每日' : type === 'weekly' ? '每周' : '每月'}目标功能开发中`,
                icon: 'none'
            })
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
.user-card {
    margin-bottom: 20rpx;
}

.user-info {
    display: flex;
    align-items: center;
    margin-bottom: 30rpx;
}

.avatar {
    width: 120rpx;
    height: 120rpx;
    border-radius: 60rpx;
    background-color: #10B981;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 60rpx;
    margin-right: 30rpx;
}

.user-details {
    flex: 1;
}

.username {
    display: block;
    font-size: 36rpx;
    font-weight: bold;
    color: #1F2937;
    margin-bottom: 10rpx;
}

.user-tip {
    font-size: 24rpx;
    color: #6B7280;
}

.menu-item {
    display: flex;
    align-items: center;
    padding: 30rpx 0;
    border-bottom: 1rpx solid #E5E7EB;
}

.menu-item:last-child {
    border-bottom: none;
}

.menu-icon {
    font-size: 40rpx;
    margin-right: 20rpx;
}

.menu-name {
    flex: 1;
    font-size: 30rpx;
    color: #1F2937;
}

.menu-arrow {
    font-size: 40rpx;
    color: #9CA3AF;
}

.goal-setting-list {
    margin-top: 20rpx;
}

.goal-setting-item {
    display: flex;
    align-items: center;
    padding: 25rpx 0;
    border-bottom: 1rpx solid #E5E7EB;
}

.goal-setting-item:last-child {
    border-bottom: none;
}

.goal-icon {
    font-size: 36rpx;
    margin-right: 20rpx;
}

.goal-info {
    flex: 1;
}

.goal-title {
    display: block;
    font-size: 28rpx;
    color: #1F2937;
    margin-bottom: 5rpx;
}

.goal-value {
    font-size: 24rpx;
    color: #10B981;
}
</style>
