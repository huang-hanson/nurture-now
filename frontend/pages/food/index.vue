<template>
    <view class="container">
        <!-- 搜索栏 -->
        <view class="search-box">
            <input class="input-field" placeholder="搜索食物..." v-model="searchKeyword" @input="handleSearch" />
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

        <!-- 食物列表 -->
        <view class="food-list">
            <view
                v-for="food in filteredFoods"
                :key="food.id"
                class="food-item"
                @click="showFoodDetail(food)"
            >
                <view class="food-icon">🍎</view>
                <view class="food-info">
                    <text class="food-name">{{ food.name }}</text>
                    <text class="food-category">{{ food.category }} · 每100g</text>
                </view>
                <text class="food-calories text-emerald">{{ food.calories }}</text>
                <text class="food-unit">kcal</text>
            </view>
        </view>

        <!-- AI查询按钮 -->
        <view class="ai-query-section">
            <button class="btn-primary" @click="showAIQuery">🔍 AI查询食物热量</button>
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
            categories: ['全部', '主食', '蔬菜', '水果', '肉类', '零食', '饮料'],
            foods: [],
            filteredFoods: []
        }
    },
    onLoad() {
        this.loadFoods()
    },
    methods: {
        async loadFoods() {
            try {
                const params = {
                    category: this.selectedCategory === '全部' ? undefined : this.selectedCategory
                }
                const res = await api.getFoods(params)
                if (res.code === 200) {
                    this.foods = res.data
                    this.filteredFoods = this.foods
                }
            } catch (e) {
                console.log('加载食物列表失败', e)
            }
        },
        selectCategory(category) {
            this.selectedCategory = category
            this.loadFoods()
        },
        handleSearch() {
            if (this.searchKeyword) {
                this.filteredFoods = this.foods.filter(food =>
                    food.name.includes(this.searchKeyword)
                )
            } else {
                this.filteredFoods = this.foods
            }
        },
        showFoodDetail(food) {
            uni.showModal({
                title: food.name,
                content: `每100g热量：${food.calories}kcal\n分类：${food.category}\n描述：${food.description || '无'}`,
                showCancel: false
            })
        },
        showAIQuery() {
            uni.showModal({
                title: 'AI查询',
                editable: true,
                placeholderText: '请输入食物名称',
                success: (res) => {
                    if (res.confirm && res.content) {
                        this.queryByAI(res.content)
                    }
                }
            })
        },
        async queryByAI(foodName) {
            uni.showLoading({ title: '查询中...' })
            try {
                const res = await api.queryFoodByAI({ name: foodName })
                uni.hideLoading()
                if (res.code === 200) {
                    uni.showModal({
                        title: '查询结果',
                        content: `名称：${res.data.name}\n热量：${res.data.calories}kcal/100g`,
                        showCancel: false
                    })
                    this.loadFoods() // 刷新列表
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
    background-color: #10B981;
    color: #FFFFFF;
}

.food-list {
    max-height: 800rpx;
    overflow-y: auto;
}

.food-item {
    display: flex;
    align-items: center;
    padding: 30rpx;
    background-color: #FFFFFF;
    border-radius: 20rpx;
    margin-bottom: 20rpx;
}

.food-icon {
    font-size: 48rpx;
    margin-right: 20rpx;
}

.food-info {
    flex: 1;
}

.food-name {
    display: block;
    font-size: 32rpx;
    font-weight: bold;
    color: #1F2937;
    margin-bottom: 5rpx;
}

.food-category {
    font-size: 24rpx;
    color: #6B7280;
}

.food-calories {
    font-size: 36rpx;
    font-weight: bold;
    margin-right: 5rpx;
}

.food-unit {
    font-size: 24rpx;
    color: #6B7280;
}

.ai-query-section {
    margin-top: 30rpx;
}
</style>
