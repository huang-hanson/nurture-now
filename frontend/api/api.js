const BASE_URL = 'http://localhost:8080/api'

const request = (url, method, data) => {
    return new Promise((resolve, reject) => {
        uni.request({
            url: BASE_URL + url,
            method: method,
            data: data,
            header: {
                'Content-Type': 'application/json'
            },
            success: (res) => {
                if (res.statusCode === 200) {
                    resolve(res.data)
                } else {
                    reject(res)
                }
            },
            fail: (err) => {
                reject(err)
            }
        })
    })
}

export default {
    // 用户相关
    saveUser: (data) => request('/user/save', 'POST', data),
    getUserByOpenId: (openId) => request(`/user/openId/${openId}`, 'GET'),

    // 食物相关
    getFoods: (params) => request('/food/list', 'GET', params),
    queryFoodByAI: (data) => request('/food/ai/query', 'POST', data),

    // 运动相关
    getExercises: (params) => request('/exercise/list', 'GET', params),
    queryExerciseByAI: (data) => request('/exercise/ai/query', 'POST', data),

    // 签到相关
    checkIn: (userId) => request(`/checkin/${userId}`, 'POST'),
    getCheckInRecords: (userId, limit) => request(`/checkin/records/${userId}?limit=${limit}`, 'GET'),
    getTodayCheckIn: (userId) => request(`/checkin/today/${userId}`, 'GET'),

    // 目标相关
    saveGoal: (data) => request('/goal/save', 'POST', data),
    getUserGoals: (userId) => request(`/goal/user/${userId}`, 'GET'),

    // 健康记录相关
    saveRecord: (data) => request('/record/save', 'POST', data),
    getUserRecords: (userId, params) => request(`/record/user/${userId}`, 'GET', params),
    getStats: (userId, params) => request(`/record/stats/${userId}`, 'GET', params),

    // 训练计划相关
    createPlan: (data) => request('/plan/create', 'POST', data),
    getUserPlans: (userId) => request(`/plan/user/${userId}`, 'GET'),
    getPlanDetail: (planId) => request(`/plan/detail/${planId}`, 'GET'),
    updatePlanStatus: (planId, data) => request(`/plan/status/${planId}`, 'PUT', data)
}
