# 养时小程序 - uni-app + Spring Boot

基于 uni-app (Vue 3) + Spring Boot 2.4.2 + MyBatis Plus 的养时小程序(养生的时刻)

## 技术栈

### 前端
- **框架**: uni-app (Vue 3)
- **样式**: 原生 CSS
- **跨端**: 微信小程序、H5、App

### 后端
- **框架**: Spring Boot 2.4.2
- **ORM**: MyBatis Plus 3.4.2
- **数据库**: MySQL 8.0
- **连接池**: Druid 1.2.6
- **AI 集成**: Coze LLM API

## 项目结构

```
health-fitness/
├── backend/                    # 后端项目
│   ├── src/main/
│   │   ├── java/com/health/fitness/
│   │   │   ├── controller/    # 控制器
│   │   │   ├── service/       # 业务逻辑
│   │   │   ├── mapper/        # MyBatis Mapper
│   │   │   ├── entity/        # 实体类
│   │   │   ├── common/        # 通用类
│   │   │   └── FitnessApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/schema.sql
│   └── pom.xml
└── frontend/                   # 前端项目
    ├── pages/                 # 页面
    │   ├── index/             # 首页
    │   ├── checkin/           # 签到打卡
    │   ├── food/              # 食物库
    │   ├── exercise/          # 运动库
    │   ├── plan/              # 训练计划
    │   └── mine/              # 个人中心
    ├── api/                   # API 接口
    ├── static/                # 静态资源
    ├── App.vue
    ├── manifest.json
    └── pages.json
```

## 核心功能

### 1. 用户管理
- 微信登录
- 个人信息管理（身高、体重、年龄、性别）
- 目标体重设置

### 2. 签到打卡
- 每日签到
- 连续签到统计
- 签到日历展示

### 3. 运动量目标
- 每日运动量目标（时长/卡路里）
- 每周运动量目标
- 每月运动量目标
- 目标完成度追踪

### 4. 食物库
- 食物卡路里查询
- 分类筛选（主食/蔬菜/水果/肉类/零食/饮料）
- **AI 智能查询**：数据库不存在时调用 LLM 查询

### 5. 运动库
- 运动热量消耗查询
- 分类筛选（有氧/力量/拉伸/球类/其他）
- **AI 智能查询**：数据库不存在时调用 LLM 查询

### 6. 训练计划
- 创建自定义训练计划
- 训练计划模板（减脂/增肌/塑形/初学者）
- 计划执行进度追踪
- 计划状态管理

### 7. 健康记录
- 体重记录
- 每日热量摄入/消耗
- 周/月统计数据
- 数据可视化

## 数据库表设计

| 表名 | 说明 |
|------|------|
| users | 用户信息 |
| foods | 食物库 |
| exercises | 运动库 |
| check_in_records | 签到记录 |
| exercise_goals | 运动量目标 |
| health_records | 健康记录 |
| training_plans | 训练计划 |
| training_plan_items | 训练计划项 |

## 快速开始

### 后端启动

1. **配置数据库**
   ```bash
   # 创建数据库
   CREATE DATABASE health_fitness CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

   # 执行 SQL 脚本
   mysql -u root -p health_fitness < backend/src/main/resources/db/schema.sql
   ```

2. **修改配置**
   ```yaml
   # backend/src/main/resources/application.yml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/health_fitness
       username: your_username
       password: your_password
   ```

3. **启动后端**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

   后端服务将运行在 `http://localhost:8080`

### 前端启动

1. **安装 HBuilderX**
   - 下载 HBuilderX: https://www.dcloud.io/hbuilderx.html

2. **导入项目**
   - 打开 HBuilderX
   - 文件 -> 导入 -> 从本地目录导入
   - 选择 `frontend` 目录

3. **运行项目**
   - 点击工具栏中的"运行"
   - 选择"运行到浏览器"（H5）或"运行到小程序模拟器"

## API 接口文档

### 用户相关
- `POST /api/user/save` - 保存/更新用户信息
- `GET /api/user/openId/{openId}` - 通过 OpenID 获取用户

### 食物相关
- `GET /api/food/list` - 获取食物列表（支持分类/搜索）
- `GET /api/food/{id}` - 获取食物详情
- `POST /api/food/ai/query` - AI 查询食物热量

### 运动相关
- `GET /api/exercise/list` - 获取运动列表（支持分类/搜索）
- `GET /api/exercise/{id}` - 获取运动详情
- `POST /api/exercise/ai/query` - AI 查询运动热量

### 签到相关
- `POST /api/checkin/{userId}` - 签到
- `GET /api/checkin/records/{userId}` - 获取签到记录
- `GET /api/checkin/today/{userId}` - 获取今日签到状态

### 目标相关
- `POST /api/goal/save` - 保存运动量目标
- `GET /api/goal/user/{userId}` - 获取用户所有目标

### 健康记录相关
- `POST /api/record/save` - 保存健康记录
- `GET /api/record/user/{userId}` - 获取健康记录
- `GET /api/record/stats/{userId}` - 获取统计数据

### 训练计划相关
- `POST /api/plan/create` - 创建训练计划
- `GET /api/plan/user/{userId}` - 获取用户训练计划
- `GET /api/plan/detail/{planId}` - 获取训练计划详情
- `PUT /api/plan/status/{planId}` - 更新计划状态

## AI 查询功能

### 食物热量查询
当用户查询的食物在数据库中不存在时，系统会自动调用 LLM API 查询食物热量：

```javascript
// 前端调用
api.queryFoodByAI({ name: '西瓜' })

// 后端处理
1. 调用 LLM API 查询食物热量
2. 解析返回结果
3. 自动保存到数据库
4. 返回给前端
```

### 运动热量查询
同样支持 AI 查询运动热量消耗：

```javascript
// 前端调用
api.queryExerciseByAI({ name: '打篮球' })
```

## 训练计划逻辑

### 推荐模板
1. **减脂计划**
   - 重点：有氧运动
   - 频率：每周 5 次
   - 示例：跑步、游泳、跳绳

2. **增肌计划**
   - 重点：力量训练
   - 频率：每周 4 次
   - 示例：俯卧撑、深蹲、卧推

3. **塑形计划**
   - 重点：有氧 + 力量结合
   - 频率：每周 3 次
   - 示例：瑜伽、全身训练

4. **初学者计划**
   - 重点：低强度入门
   - 频率：每周 3 次
   - 示例：快走、简单拉伸

### 自定义计划
用户可以创建自己的训练计划：
- 选择训练周期（每日/每周/每月）
- 添加运动项目
- 设置组数、次数、时长
- 追踪执行进度

## 运动量目标系统

### 目标类型
- **每日目标**：每天的运动量目标
- **每周目标**：每周的运动量目标
- **每月目标**：每月的运动量目标

### 目标指标
- 时长（分钟）
- 消耗卡路里（kcal）

### 完成度追踪
系统会根据用户的健康记录自动计算目标完成度。

## 配置说明

### LLM API 配置
```yaml
# backend/src/main/resources/application.yml
llm:
  api-url: https://api.coze.cn/open_api/v2/chat
  api-key: ${COZE_API_KEY:your-api-key}
  model: deepseek-chat
```

### 跨域配置
所有 Controller 已添加 `@CrossOrigin` 注解，支持跨域请求。

## 开发说明

### 后端
- 使用 MyBatis Plus 的 BaseMapper 提供基础 CRUD
- Service 层处理业务逻辑
- Controller 层提供 RESTful API

### 前端
- 使用 uni-app 框架
- API 调用统一封装在 `api/api.js`
- 页面采用 Vue 3 Composition API

## 后续优化建议

1. **用户认证**
   - 集成微信登录
   - JWT Token 鉴权

2. **数据可视化**
   - 添加图表库（如 ECharts）
   - 展示体重变化趋势
   - 运动量完成度图表

3. **社交功能**
   - 好友系统
   - 分享训练成果
   - 排行榜

4. **智能推荐**
   - 基于用户数据推荐训练计划
   - 个性化饮食建议

5. **拍照识别**
   - 集成图片识别
   - 自动识别食物

## 许可证

MIT License
