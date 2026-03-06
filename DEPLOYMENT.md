# 发版指南 - 健身健身小程序

## 📋 发版前检查清单

### ✅ 已完成

- [x] 后端代码完整（8个Controller + Service + Mapper + Entity）
- [x] 前端页面完整（6个核心页面）
- [x] Maven 配置完整（pom.xml）
- [x] 数据库配置完整（application.yml）
- [x] 数据库表结构完整（8张表）
- [x] API 接口完整（22个接口）
- [x] LLM 集成完成

### ⚠️ 发版前必须完成

#### 1. 准备 TabBar 图标（必选）

**位置**: `/workspace/health-fitness/frontend/static/tabbar/`

**需要的图标**（12个PNG文件）：
```
home.png (首页-未选中)
home-active.png (首页-选中)
checkin.png (签到-未选中)
checkin-active.png (签到-选中)
food.png (食物库-未选中)
food-active.png (食物库-选中)
exercise.png (运动库-未选中)
exercise-active.png (运动库-选中)
plan.png (计划-未选中)
plan-active.png (计划-选中)
mine.png (我的-未选中)
mine-active.png (我的-选中)
```

**图标规格**：
- 格式：PNG
- 尺寸：81px × 81px
- 颜色：
  - 未选中：#999999
  - 选中：#10B981

**准备方式**：

**方式1：使用在线图标工具**
1. 访问：https://www.iconfont.cn/
2. 搜索图标：首页、签到、食物、运动、计划、我的
3. 下载 PNG 格式（81×81）
4. 分别制作未选中（灰色）和选中（绿色）版本
5. 放入 `/workspace/health-fitness/frontend/static/tabbar/` 目录

**方式2：使用临时方案（开发测试用）**

如果暂时没有图标，可以修改 `pages.json` 移除 tabBar，直接运行页面。

#### 2. 数据库初始化（必选）

**步骤**：

```bash
# 1. 安装 MySQL 8.0（如果还没安装）
# Windows: 下载 MySQL Installer
# Mac: brew install mysql
# Linux: apt install mysql-server

# 2. 启动 MySQL 服务
# Windows: 服务管理器中启动 MySQL80
# Mac/Linux: brew services start mysql 或 systemctl start mysql

# 3. 创建数据库
mysql -u root -p
```

```sql
CREATE DATABASE health_fitness CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
exit;
```

```bash
# 4. 执行表结构初始化
mysql -u root -p health_fitness < backend/src/main/resources/db/schema.sql
```

**验证数据库初始化**：
```sql
mysql -u root -p health_fitness

-- 检查表是否创建成功
SHOW TABLES;

-- 应该显示 8 张表：
-- check_in_records
-- exercise_goals
-- exercises
-- foods
-- health_records
-- training_plan_items
-- training_plans
-- users

-- 检查示例数据
SELECT COUNT(*) FROM foods;      -- 应该返回 10
SELECT COUNT(*) FROM exercises;  -- 应该返回 10

exit;
```

#### 3. 配置 LLM API Key（可选，但推荐）

**步骤**：

1. 注册 Coze 账号：https://www.coze.cn/
2. 创建应用，获取 API Key
3. 修改配置文件：

```bash
# 编辑配置文件
vi /workspace/health-fitness/backend/src/main/resources/application.yml
```

```yaml
# 修改 LLM 配置
llm:
  api-url: https://api.coze.cn/open_api/v2/chat
  api-key: your-real-api-key-here  # 替换为你的真实 API Key
  model: deepseek-chat
```

**如果没有 API Key**：
- 后端仍然可以运行
- 只是 AI 查询功能不可用
- 其他功能完全正常

#### 4. 后端编译测试（必选）

**步骤**：

```bash
cd /workspace/health-fitness/backend

# 编译项目
mvn clean compile

# 运行单元测试
mvn test

# 打包项目
mvn clean package

# 启动后端服务
java -jar target/fitness-backend-1.0.0.jar
```

**验证后端启动**：
```bash
# 检查后端是否启动成功
curl http://localhost:8080/api/food/list

# 应该返回食物列表数据（包含 10 条示例数据）
```

#### 5. 前端运行测试（必选）

**步骤**：

**方式1：使用 HBuilderX（推荐）**

1. 下载 HBuilderX：https://www.dcloud.io/hbuilderx.html
2. 打开 HBuilderX
3. 文件 → 导入 → 从本地目录导入
4. 选择 `/workspace/health-fitness/frontend` 目录
5. 点击工具栏的"运行"按钮
6. 选择"运行到浏览器"（Chrome）或"运行到小程序模拟器"

**方式2：使用命令行（H5）**

```bash
# 安装依赖（如果还没有）
cd /workspace/health-fitness/frontend
npm install

# 启动开发服务器
npm run dev:h5

# 浏览器访问
# http://localhost:5173
```

**方式3：构建微信小程序**

```bash
# 构建小程序
npm run build:mp-weixin

# 打开微信开发者工具
# 导入 /workspace/health-fitness/frontend/dist/dev/mp-weixin 目录
```

## 🚀 正式发版

### 后端部署

#### 开发环境

```bash
cd /workspace/health-fitness/backend
mvn spring-boot:run
```

#### 生产环境

```bash
# 1. 打包
mvn clean package

# 2. 上传 jar 包到服务器
scp target/fitness-backend-1.0.0.jar user@server:/app/

# 3. 服务器上运行
java -jar /app/fitness-backend-1.0.0.jar
```

#### 后台运行（推荐）

```bash
# 使用 nohup 后台运行
nohup java -jar fitness-backend-1.0.0.jar > app.log 2>&1 &

# 查看日志
tail -f app.log

# 停止服务
ps -ef | grep fitness-backend
kill -9 <pid>
```

### 前端部署

#### H5 部署

```bash
cd /workspace/health-fitness/frontend

# 构建 H5
npm run build:h5

# 构建产物在 dist/build/h5 目录
# 上传到 Web 服务器（Nginx/Apache）
```

#### 微信小程序部署

```bash
# 1. 构建小程序
npm run build:mp-weixin

# 2. 打开微信开发者工具
# 3. 导入项目：/workspace/health-fitness/frontend/dist/build/mp-weixin
# 4. 点击"上传"按钮
# 5. 在微信公众平台提交审核

# 微信公众平台
# https://mp.weixin.qq.com/
```

#### App 打包

```bash
# 1. 使用 HBuilderX
# 2. 发行 → 原生App-云打包
# 3. 选择平台（Android/iOS）
# 4. 等待打包完成
# 5. 下载 APK/IPA 文件
```

## 📱 预览效果

### 首页
```
┌─────────────────────────────┐
│    👤  我的健身之旅          │
│  身高: 170cm  体重: 65kg    │
│  BMI: 22.5  目标: 60kg      │
├─────────────────────────────┤
│  今日概览                   │
│  📅 已连续签到 5 天          │
│  🔥 今日消耗: 320 kcal       │
│  🍎 今日摄入: 1800 kcal      │
├─────────────────────────────┤
│  快捷功能                   │
│  [ 📋 训练计划 ]  [ 🏃 运动 ]│
│  [ 🍎 食物库   ]  [ ✅ 签到 ]│
└─────────────────────────────┘
```

### 签到打卡页面
```
┌─────────────────────────────┐
│        签到打卡 ✅          │
├─────────────────────────────┤
│  📅 今日签到               │
│  [  签  到  ]              │
│  已连续签到: 5 天           │
├─────────────────────────────┤
│  运动量目标                 │
│  每日: 30分钟 / 300kcal     │
│  进度: ████████░░ 80%       │
├─────────────────────────────┤
│  本周签到记录               │
│  一 二 三 四 五 六 日       │
│  ✅ ✅ ✅ ✅ ✅  ⬜  ⬜       │
└─────────────────────────────┘
```

### 食物库页面
```
┌─────────────────────────────┐
│  🔍 [ 搜索食物... ]         │
├─────────────────────────────┤
│  分类: [全部][主食][水果]   │
├─────────────────────────────┤
│  🍎 苹果                    │
│  卡路里: 52 kcal/100g       │
│  分类: 水果                 │
├─────────────────────────────┤
│  🍌 香蕉                    │
│  卡路里: 89 kcal/100g       │
│  分类: 水果                 │
├─────────────────────────────┤
│  🥚 鸡蛋                    │
│  卡路里: 155 kcal/100g      │
│  分类: 肉类                 │
└─────────────────────────────┘
```

### 运动库页面
```
┌─────────────────────────────┐
│  🔍 [ 搜索运动... ]         │
├─────────────────────────────┤
│  分类: [全部][有氧][力量]   │
├─────────────────────────────┤
│  🏃 跑步                    │
│  消耗: 300 kcal/小时        │
│  分类: 有氧                 │
│  建议时长: 30分钟           │
├─────────────────────────────┤
│  💪 深蹲                    │
│  消耗: 250 kcal/小时        │
│  分类: 力量                 │
│  建议时长: 15分钟           │
├─────────────────────────────┤
│  🏊 游泳                    │
│  消耗: 400 kcal/小时        │
│  分类: 有氧                 │
│  建议时长: 30分钟           │
└─────────────────────────────┘
```

### 训练计划页面
```
┌─────────────────────────────┐
│  我的训练计划      [ + 新建 ]│
├─────────────────────────────┤
│  🔥 减脂计划                 │
│  状态: 进行中 (active)       │
│  日期: 2024-01-01 ~ 长期    │
│  描述: 以有氧运动为主        │
├─────────────────────────────┤
│  💪 增肌计划                 │
│  状态: 已完成 (completed)    │
│  日期: 2023-12-01 ~ 长期    │
├─────────────────────────────┤
│  推荐训练计划               │
│  🔥 减脂计划         [ 应用 ]│
│  以有氧运动为主，每周5次    │
├─────────────────────────────┤
│  💪 增肌计划         [ 应用 ]│
│  力量训练为主，每周4次      │
└─────────────────────────────┘
```

### 我的页面
```
┌─────────────────────────────┤
│  👤  未登录                 │
│  完善个人信息开始健身之旅    │
│  [ 完善个人信息 ]            │
├─────────────────────────────┤
│  [📋 训练计划]  [📊 数据统计]│
│  [⚙️  设置  ]              │
├─────────────────────────────┤
│  运动量目标                 │
│  📅 每日目标      ›        │
│     30分钟 / 300kcal        │
│  📆 每周目标      ›        │
│     210分钟 / 2100kcal      │
│  🗓️ 每月目标      ›        │
│     900分钟 / 9000kcal      │
└─────────────────────────────┘
```

## 🎨 TabBar 底部导航栏

```
┌─────────────────────────────┐
│                             │
│      页面内容区域           │
│                             │
│                             │
├─────────────────────────────┤
│ [首页] [签到] [食物] [运动]  │
│ [计划] [我的]               │
└─────────────────────────────┘
```

**TabBar 配置**：
- 颜色：未选中 #999999，选中 #10B981（绿色）
- 背景：白色 #ffffff
- 图标尺寸：81px × 81px

## ⚡ 快速开始（开发环境）

### 一键启动（推荐）

```bash
# 1. 启动 MySQL（如果还没启动）
# Windows: net start MySQL80
# Mac: brew services start mysql

# 2. 初始化数据库（只执行一次）
mysql -u root -p health_fitness < backend/src/main/resources/db/schema.sql

# 3. 启动后端（终端1）
cd /workspace/health-fitness/backend
mvn spring-boot:run

# 4. 启动前端（终端2）
cd /workspace/health-fitness/frontend
npm run dev:h5

# 5. 浏览器访问
# http://localhost:5173
```

## 🔍 API 测试

### 测试后端接口

```bash
# 1. 测试食物列表
curl http://localhost:8080/api/food/list

# 2. 测试运动列表
curl http://localhost:8080/api/exercise/list

# 3. 测试签到
curl -X POST http://localhost:8080/api/checkin/1

# 4. 测试 AI 查询（需要配置 API Key）
curl -X POST http://localhost:8080/api/food/ai/query \
  -H "Content-Type: application/json" \
  -d '{"name":"西瓜"}'
```

## 📞 常见问题

### Q1: 后端启动失败，提示数据库连接错误？

**A**: 检查以下几点：
1. MySQL 服务是否启动
2. 数据库是否已创建：`CREATE DATABASE health_fitness;`
3. 配置文件中的数据库用户名密码是否正确
4. 检查 application.yml 中的数据库地址

### Q2: 前端 H5 运行后 TabBar 不显示？

**A**: 检查以下几点：
1. 图标文件是否存在于 `static/tabbar/` 目录
2. pages.json 中的 iconPath 路径是否正确
3. 图标文件名是否与 pages.json 中的一致

### Q3: AI 查询功能不可用？

**A**: 检查以下几点：
1. 是否已配置正确的 Coze API Key
2. 网络是否可以访问 api.coze.cn
3. 检查 application.yml 中的 LLM 配置

### Q4: 微信小程序报错？

**A**: 检查以下几点：
1. 是否已在微信公众平台注册小程序
2. 是否已配置小程序的 AppID
3. manifest.json 中的微信小程序配置是否正确

## 📝 发版检查清单（最终）

### 代码层面
- [x] 所有代码已完成
- [x] 22 个 API 接口已实现
- [x] 8 张数据库表已设计
- [x] LLM 集成已完成
- [ ] TabBar 图标已准备
- [ ] 后端编译通过
- [ ] 前端编译通过

### 环境层面
- [ ] MySQL 已安装并启动
- [ ] 数据库已初始化（8张表）
- [ ] 示例数据已插入
- [ ] LLM API Key 已配置（可选）

### 测试层面
- [ ] 后端服务启动成功
- [ ] 前端页面能正常访问
- [ ] API 接口能正常调用
- [ ] 所有功能测试通过

## 🎉 发版成功

完成以上所有检查后，项目即可正式发版！

**恭喜你，健身健身小程序发版成功！** 🎊
