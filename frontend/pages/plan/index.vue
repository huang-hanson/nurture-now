<template>
    <view class="container">
        <!-- 计划列表 -->
        <view class="card">
            <view class="card-header">
                <text class="title">我的训练计划</text>
                <button class="btn-small" @click="createPlan">+ 新建</button>
            </view>

            <view v-if="plans.length === 0" class="empty-state">
                <text class="empty-icon">📋</text>
                <text class="empty-text">暂无训练计划</text>
                <text class="empty-tip">点击上方按钮创建你的第一个计划</text>
            </view>

            <view v-else class="plan-list">
                <view
                    v-for="plan in plans"
                    :key="plan.id"
                    class="plan-item"
                    @click="viewPlan(plan)"
                >
                    <view class="plan-header">
                        <text class="plan-title">{{ plan.title }}</text>
                        <text class="plan-status" :class="'status-' + plan.status">
                            {{ getStatusText(plan.status) }}
                        </text>
                    </view>
                    <text class="plan-date">{{ plan.startDate }} ~ {{ plan.endDate || '长期' }}</text>
                    <text class="plan-desc">{{ plan.description || '暂无描述' }}</text>
                </view>
            </view>
        </view>

        <!-- 计划模板 -->
        <view class="card">
            <text class="title">推荐训练计划</text>
            <view class="template-list">
                <view
                    v-for="(template, index) in templates"
                    :key="index"
                    class="template-item"
                    @click="useTemplate(template)"
                >
                    <text class="template-icon">{{ template.icon }}</text>
                    <view class="template-info">
                        <text class="template-name">{{ template.name }}</text>
                        <text class="template-desc">{{ template.desc }}</text>
                    </view>
                    <text class="template-arrow">›</text>
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
            plans: [],
            templates: [
                {
                    name: '减脂计划',
                    desc: '以有氧运动为主，每周5次',
                    icon: '🔥'
                },
                {
                    name: '增肌计划',
                    desc: '力量训练为主，每周4次',
                    icon: '💪'
                },
                {
                    name: '塑形计划',
                    desc: '有氧+力量结合，每周3次',
                    icon: '✨'
                },
                {
                    name: '初学者计划',
                    desc: '低强度入门，每周3次',
                    icon: '🌱'
                }
            ]
        }
    },
    onLoad() {
        this.loadPlans()
    },
    methods: {
        async loadPlans() {
            try {
                const res = await api.getUserPlans(this.userId)
                if (res.code === 200) {
                    this.plans = res.data
                }
            } catch (e) {
                console.log('加载训练计划失败', e)
            }
        },
        createPlan() {
            uni.showToast({
                title: '创建计划功能开发中',
                icon: 'none'
            })
        },
        viewPlan(plan) {
            uni.showModal({
                title: plan.title,
                content: `状态：${this.getStatusText(plan.status)}\n开始日期：${plan.startDate}\n结束日期：${plan.endDate || '长期'}\n描述：${plan.description || '暂无描述'}`,
                showCancel: false
            })
        },
        useTemplate(template) {
            uni.showModal({
                title: '使用模板',
                content: `确定要使用"${template.name}"模板吗？`,
                success: (res) => {
                    if (res.confirm) {
                        this.createPlanFromTemplate(template)
                    }
                }
            })
        },
        async createPlanFromTemplate(template) {
            uni.showToast({
                title: '模板应用功能开发中',
                icon: 'none'
            })
        },
        getStatusText(status) {
            const statusMap = {
                'active': '进行中',
                'completed': '已完成',
                'cancelled': '已取消'
            }
            return statusMap[status] || status
        }
    }
}
</script>

<style scoped>
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20rpx;
}

.btn-small {
    padding: 10rpx 20rpx;
    background-color: #10B981;
    color: #FFFFFF;
    border-radius: 10rpx;
    font-size: 24rpx;
    border: none;
}

.empty-state {
    text-align: center;
    padding: 80rpx 20rpx;
}

.empty-icon {
    display: block;
    font-size: 80rpx;
    margin-bottom: 20rpx;
}

.empty-text {
    display: block;
    font-size: 32rpx;
    color: #6B7280;
    margin-bottom: 10rpx;
}

.empty-tip {
    font-size: 24rpx;
    color: #9CA3AF;
}

.plan-list {
    max-height: 600rpx;
    overflow-y: auto;
}

.plan-item {
    padding: 30rpx;
    background-color: #F5F5F5;
    border-radius: 20rpx;
    margin-bottom: 20rpx;
}

.plan-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15rpx;
}

.plan-title {
    font-size: 32rpx;
    font-weight: bold;
    color: #1F2937;
}

.plan-status {
    padding: 5rpx 15rpx;
    border-radius: 10rpx;
    font-size: 22rpx;
}

.plan-status.status-active {
    background-color: #DBEAFE;
    color: #3B82F6;
}

.plan-status.status-completed {
    background-color: #D1FAE5;
    color: #10B981;
}

.plan-status.status-cancelled {
    background-color: #FEE2E2;
    color: #EF4444;
}

.plan-date {
    display: block;
    font-size: 24rpx;
    color: #6B7280;
    margin-bottom: 10rpx;
}

.plan-desc {
    font-size: 26rpx;
    color: #9CA3AF;
}

.template-list {
    margin-top: 20rpx;
}

.template-item {
    display: flex;
    align-items: center;
    padding: 30rpx;
    background-color: #F5F5F5;
    border-radius: 20rpx;
    margin-bottom: 20rpx;
}

.template-icon {
    font-size: 48rpx;
    margin-right: 20rpx;
}

.template-info {
    flex: 1;
}

.template-name {
    display: block;
    font-size: 30rpx;
    font-weight: bold;
    color: #1F2937;
    margin-bottom: 5rpx;
}

.template-desc {
    font-size: 24rpx;
    color: #6B7280;
}

.template-arrow {
    font-size: 40rpx;
    color: #9CA3AF;
}
</style>
