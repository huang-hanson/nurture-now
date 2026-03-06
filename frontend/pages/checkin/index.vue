<template>
    <view class="container">
        <!-- 签到卡片 -->
        <view class="card">
            <view class="checkin-header">
                <text class="checkin-title">{{ todayCheckIn ? '今日已签到' : '点击签到' }}</text>
                <text class="checkin-days">连续签到 {{ checkInDays }} 天</text>
            </view>
            <view class="checkin-button" :class="{ 'checked': todayCheckIn }" @click="handleCheckIn">
                <text class="checkin-icon">{{ todayCheckIn ? '✓' : '📅' }}</text>
                <text class="checkin-text">{{ todayCheckIn ? '已签到' : '签到' }}</text>
            </view>
        </view>

        <!-- 签到日历 -->
        <view class="card">
            <text class="title">签到记录</text>
            <view class="calendar-grid">
                <view
                    v-for="(day, index) in checkInRecords"
                    :key="index"
                    class="calendar-item"
                    :class="{ 'checked': day.checked }"
                >
                    <text class="calendar-day">{{ day.day }}</text>
                    <text class="calendar-date">{{ day.date }}</text>
                </view>
            </view>
        </view>

        <!-- 运动目标 -->
        <view class="card">
            <text class="title">今日运动目标</text>
            <view class="goal-list">
                <view class="goal-item">
                    <text class="goal-label">时长</text>
                    <text class="goal-value">{{ dailyGoal.durationMinutes || 0 }} 分钟</text>
                </view>
                <view class="goal-item">
                    <text class="goal-label">消耗</text>
                    <text class="goal-value">{{ dailyGoal.calories || 0 }} kcal</text>
                </view>
            </view>
            <button class="btn-primary" @click="setGoal">设置目标</button>
        </view>
    </view>
</template>

<script>
import api from '@/api/api.js'

export default {
    data() {
        return {
            userId: 1,
            todayCheckIn: false,
            checkInDays: 0,
            checkInRecords: [],
            dailyGoal: {}
        }
    },
    onLoad() {
        this.loadTodayCheckIn()
        this.loadCheckInRecords()
        this.loadDailyGoal()
    },
    methods: {
        async handleCheckIn() {
            if (this.todayCheckIn) {
                uni.showToast({
                    title: '今日已签到',
                    icon: 'none'
                })
                return
            }

            try {
                const res = await api.checkIn(this.userId)
                if (res.code === 200) {
                    this.todayCheckIn = true
                    this.checkInDays = res.data.consecutiveDays
                    uni.showToast({
                        title: '签到成功！',
                        icon: 'success'
                    })
                    this.loadCheckInRecords()
                }
            } catch (e) {
                uni.showToast({
                    title: '签到失败',
                    icon: 'none'
                })
            }
        },
        async loadTodayCheckIn() {
            try {
                const res = await api.getTodayCheckIn(this.userId)
                if (res.code === 200 && res.data) {
                    this.todayCheckIn = true
                    this.checkInDays = res.data.consecutiveDays
                }
            } catch (e) {
                console.log('加载签到信息失败', e)
            }
        },
        async loadCheckInRecords() {
            try {
                const res = await api.getCheckInRecords(this.userId, 7)
                if (res.code === 200) {
                    const days = ['日', '一', '二', '三', '四', '五', '六']
                    this.checkInRecords = res.data.map(record => {
                        const date = new Date(record.checkInDate)
                        return {
                            day: days[date.getDay()],
                            date: date.getDate(),
                            checked: true
                        }
                    })
                }
            } catch (e) {
                console.log('加载签到记录失败', e)
            }
        },
        async loadDailyGoal() {
            try {
                const res = await api.getUserGoals(this.userId)
                if (res.code === 200) {
                    const daily = res.data.find(g => g.goalType === 'daily')
                    this.dailyGoal = daily || {}
                }
            } catch (e) {
                console.log('加载目标失败', e)
            }
        },
        setGoal() {
            uni.showToast({
                title: '目标设置功能开发中',
                icon: 'none'
            })
        }
    }
}
</script>

<style scoped>
.checkin-header {
    text-align: center;
    margin-bottom: 40rpx;
}

.checkin-title {
    display: block;
    font-size: 48rpx;
    font-weight: bold;
    color: #1F2937;
    margin-bottom: 10rpx;
}

.checkin-days {
    font-size: 28rpx;
    color: #10B981;
}

.checkin-button {
    width: 200rpx;
    height: 200rpx;
    border-radius: 50%;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    margin: 0 auto;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    box-shadow: 0 10rpx 30rpx rgba(16, 185, 129, 0.3);
}

.checkin-button.checked {
    background: linear-gradient(135deg, #E5E7EB 0%, #D1D5DB 100%);
    box-shadow: none;
}

.checkin-icon {
    font-size: 60rpx;
    margin-bottom: 10rpx;
}

.checkin-text {
    font-size: 32rpx;
    color: #FFFFFF;
}

.calendar-grid {
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    gap: 10rpx;
    margin-top: 20rpx;
}

.calendar-item {
    padding: 20rpx 10rpx;
    text-align: center;
    border-radius: 10rpx;
    background-color: #F5F5F5;
}

.calendar-item.checked {
    background-color: #10B981;
}

.calendar-day {
    display: block;
    font-size: 24rpx;
    color: #6B7280;
}

.calendar-item.checked .calendar-day {
    color: #FFFFFF;
}

.calendar-date {
    display: block;
    font-size: 36rpx;
    font-weight: bold;
    color: #1F2937;
}

.calendar-item.checked .calendar-date {
    color: #FFFFFF;
}

.goal-list {
    display: flex;
    gap: 30rpx;
    margin-bottom: 30rpx;
}

.goal-item {
    flex: 1;
    padding: 30rpx;
    background-color: #F5F5F5;
    border-radius: 15rpx;
    text-align: center;
}

.goal-label {
    display: block;
    font-size: 24rpx;
    color: #6B7280;
    margin-bottom: 10rpx;
}

.goal-value {
    display: block;
    font-size: 36rpx;
    font-weight: bold;
    color: #10B981;
}
</style>
