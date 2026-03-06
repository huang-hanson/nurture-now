<template>
    <view class="container">
        <!-- 搜索栏 -->
        <view class="search-box">
            <input class="input-field" placeholder="搜索运动..." v-model="searchKeyword" @input="handleSearch" />
        </view>

        <!-- 分类筛选 -->
        <view class="category-tabs">
            <view
                v-for="cat in categories"
                :key="cat"
                class="category-tab"
                :class="{ 'active': selectedCategory === cat }"
                @click="selectCategory(cat)"
            >
                {{ cat }}
            </view>
        </view>

        <!-- 运动列表 -->
        <view class="exercise-list">
            <view
                v-for="exercise in filteredExercises"
                :key="exercise.id"
                class="exercise-item"
                @click="showExerciseDetail(exercise)"
            >
                <view class="exercise-icon">🏃</view>
                <view class="exercise-info">
                    <text class="exercise-name">{{ exercise.name }}</text>
                    <text class="exercise-category">{{ exercise.category }} · {{ exercise.duration || 30 }}分钟</text>
                </view>
                <text class="exercise-calories text-amber">{{ exercise.caloriesBurned }}</text>
                <text class="exercise-unit">kcal/h</text>
            </view>
        </view>

        <!-- AI查询按钮 -->
        <view class="ai-query-section">
            <button class="btn-primary" @click="showAIQuery">🔍 AI查询运动热量</button>
        </view>
    </view>
</template>

<script>
import api from '@/api/api.js'

export default {
    data() {
        return {
            searchKeyword: '',
            selectedCategory: '全部',
            categories: ['全部', '有氧', '力量', '拉伸', '球类', '其他'],
            exercises: [],
            filteredExercises: []
        }
    },
    onLoad() {
        this.loadExercises()
    },
    methods: {
        async loadExercises() {
            try {
                const params = {
                    category: this.selectedCategory === '全部' ? undefined : this.selectedCategory
                }
                const res = await api.getExercises(params)
                if (res.code === 200) {
                    this.exercises = res.data
                    this.filteredExercises = this.exercises
                }
            } catch (e) {
                console.log('加载运动列表失败', e)
            }
        },
        selectCategory(category) {
            this.selectedCategory = category
            this.loadExercises()
        },
        handleSearch() {
            if (this.searchKeyword) {
                this.filteredExercises = this.exercises.filter(exercise =>
                    exercise.name.includes(this.searchKeyword)
                )
            } else {
                this.filteredExercises = this.exercises
            }
        },
        showExerciseDetail(exercise) {
            uni.showModal({
                title: exercise.name,
                content: `每小时消耗：${exercise.caloriesBurned}kcal\n分类：${exercise.category}\n建议时长：${exercise.duration || 30}分钟\n描述：${exercise.description || '无'}`,
                showCancel: false
            })
        },
        showAIQuery() {
            uni.showModal({
                title: 'AI查询',
                editable: true,
                placeholderText: '请输入运动名称',
                success: (res) => {
                    if (res.confirm && res.content) {
                        this.queryByAI(res.content)
                    }
                }
            })
        },
        async queryByAI(exerciseName) {
            uni.showLoading({ title: '查询中...' })
            try {
                const res = await api.queryExerciseByAI({ name: exerciseName })
                uni.hideLoading()
                if (res.code === 200) {
                    uni.showModal({
                        title: '查询结果',
                        content: `名称：${res.data.name}\n每小时消耗：${res.data.caloriesBurned}kcal\n建议时长：${res.data.duration}分钟`,
                        showCancel: false
                    })
                }
            } catch (e) {
                uni.hideLoading()
                uni.showToast({
                    title: '查询失败',
                    icon: 'none'
                })
            }
        }
    }
}
</script>

<style scoped>
.search-box {
    margin-bottom: 20rpx;
}

.category-tabs {
    display: flex;
    gap: 15rpx;
    margin-bottom: 30rpx;
    overflow-x: auto;
}

.category-tab {
    padding: 15rpx 30rpx;
    border-radius: 40rpx;
    background-color: #F5F5F5;
    font-size: 26rpx;
    color: #6B7280;
    white-space: nowrap;
}

.category-tab.active {
    background-color: #F59E0B;
    color: #FFFFFF;
}

.exercise-list {
    max-height: 800rpx;
    overflow-y: auto;
}

.exercise-item {
    display: flex;
    align-items: center;
    padding: 30rpx;
    background-color: #FFFFFF;
    border-radius: 20rpx;
    margin-bottom: 20rpx;
}

.exercise-icon {
    font-size: 48rpx;
    margin-right: 20rpx;
}

.exercise-info {
    flex: 1;
}

.exercise-name {
    display: block;
    font-size: 32rpx;
    font-weight: bold;
    color: #1F2937;
    margin-bottom: 5rpx;
}

.exercise-category {
    font-size: 24rpx;
    color: #6B7280;
}

.exercise-calories {
    font-size: 36rpx;
    font-weight: bold;
    margin-right: 5rpx;
}

.exercise-unit {
    font-size: 24rpx;
    color: #6B7280;
}

.ai-query-section {
    margin-top: 30rpx;
}
</style>
