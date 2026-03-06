# 🚀 快速启动指南

## 问题修复：启动错误已解决 ✅

之前的错误：`bash: .cozeproj/scripts/dev_run.sh: No such file or directory`

**已修复**：已创建启动脚本和配置文件！

---

## 🎯 启动方式

### 方式 1：使用启动脚本（推荐）

```bash
cd /workspace/projects
bash .cozeproj/scripts/dev_run.sh
```

这个脚本会：
1. ✅ 检查环境（Java、Maven）
2. ✅ 启动后端服务（Spring Boot）
3. 📱 显示前端启动说明

### 方式 2：手动启动

#### 启动后端

```bash
cd /workspace/projects/backend

# 方式 A：使用 Maven
mvn spring-boot:run

# 方式 B：后台运行
nohup mvn spring-boot:run > ../logs/backend.log 2>&1 &

# 查看日志
tail -f logs/backend.log
```

#### 启动前端

**注意**：前端（uni-app）需要使用 **HBuilderX** 启动

1. 下载 HBuilderX：https://www.dcloud.io/hbuilderx.html
2. 打开 HBuilderX
3. 文件 -> 导入 -> 从本地目录导入
4. 选择 `/workspace/projects/frontend` 目录
5. 点击工具栏的"运行" -> "运行到浏览器"

---

## 🛑 停止服务

```bash
cd /workspace/projects
bash .cozeproj/scripts/stop.sh
```

或者手动停止：

```bash
# 停止后端
kill $(cat logs/backend.pid)

# 停止前端（如果用命令行启动）
kill $(cat logs/frontend.pid)
```

---

## 📊 访问地址

- **后端 API**：http://localhost:8080
- **前端 H5**：启动后由 HBuilderX 提供

---

## 📋 启动前检查清单

### 必须完成

- [x] Java 已安装（JDK 8+）
- [x] Maven 已安装（3.6+）
- [x] MySQL 已安装（8.0+）
- [x] 项目代码已放置在 `/workspace/projects/`

### 可选完成

- [ ] MySQL 数据库已初始化（见下方）
- [ ] HBuilderX 已安装（用于启动前端）

---

## 💾 数据库初始化（可选，但推荐）

如果未初始化数据库，后端仍会启动，但 API 会报错。

### 1. 创建数据库

```bash
mysql -u root -p
```

```sql
CREATE DATABASE health_fitness CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
exit;
```

### 2. 执行 SQL 脚本

```bash
mysql -u root -p health_fitness < /workspace/projects/backend/src/main/resources/db/schema.sql
```

### 3. 验证

```bash
mysql -u root -p health_fitness
SHOW TABLES;
-- 应该显示 8 张表
```

---

## 🔍 测试后端

后端启动成功后，可以测试 API：

```bash
# 测试食物列表
curl http://localhost:8080/api/food/list

# 测试运动列表
curl http://localhost:8080/api/exercise/list
```

如果数据库未初始化，这两个接口会返回空列表。

---

## 📱 前端启动详细步骤

### 使用 HBuilderX（官方推荐）

1. **下载 HBuilderX**
   - 官网：https://www.dcloud.io/huilderx.html
   - 下载版本：标准版即可
   - 支持平台：Windows / Mac / Linux

2. **导入项目**
   - 打开 HBuilderX
   - 菜单：文件 -> 导入 -> 从本地目录导入
   - 选择目录：`/workspace/projects/frontend`

3. **运行项目**
   - 点击工具栏的"运行"按钮
   - 选择"运行到浏览器"（推荐 Chrome）
   - 或者选择"运行到小程序模拟器"

4. **访问项目**
   - H5 版本：浏览器会自动打开
   - 小程序版本：在微信开发者工具中查看

### 使用命令行（需要配置）

如果你已配置好 uni-app CLI，也可以使用命令行：

```bash
cd /workspace/projects/frontend
npm install
npm run dev:h5
```

---

## ⚠️ 常见问题

### 1. 后端启动失败

**错误**：`Cannot connect to database`

**解决**：初始化数据库（见上方"数据库初始化"）

---

### 2. Java 版本不兼容

**错误**：`Unsupported class file major version`

**解决**：安装 JDK 8 或 JDK 11

```bash
# 检查 Java 版本
java -version
```

---

### 3. Maven 下载依赖慢

**解决**：配置国内镜像源

编辑 `~/.m2/settings.xml`：

```xml
<mirrors>
  <mirror>
    <id>aliyun</id>
    <mirrorOf>central</mirrorOf>
    <name>Aliyun Maven</name>
    <url>https://maven.aliyun.com/repository/public</url>
  </mirror>
</mirrors>
```

---

### 4. 前端无法启动

**原因**：uni-app 必须使用 HBuilderX

**解决**：
1. 下载 HBuilderX
2. 按照上方"前端启动详细步骤"操作

---

### 5. 端口被占用

**错误**：`Port 8080 is already in use`

**解决**：修改后端端口

编辑 `/workspace/projects/backend/src/main/resources/application.yml`：

```yaml
server:
  port: 8081  # 改为其他端口
```

---

## 📚 相关文档

- **README.md** - 项目说明、技术栈、功能介绍
- **DEPLOYMENT.md** - 发版指南、部署步骤
- **UI_PREVIEW.md** - UI 预览、设计规范
- **QUICKSTART.md** - 本文档

---

## 🎉 启动成功标志

### 后端

- ✅ 控制台显示：`Started FitnessApplication in XX.XXX seconds`
- ✅ 可以访问：http://localhost:8080/api/food/list

### 前端

- ✅ HBuilderX 显示"编译完成"
- ✅ 浏览器自动打开（H5 版本）
- ✅ 显示首页（包含用户信息、今日概览、快捷功能）

---

## 💡 提示

- 首次启动 Maven 会下载依赖，可能需要几分钟
- 后端启动需要约 30 秒
- 前端使用 HBuilderX 启动更稳定
- 查看日志可以帮助排查问题

---

## 🆘 需要帮助？

如果遇到问题，请查看：

1. **后端日志**：`tail -f /workspace/projects/logs/backend.log`
2. **前端日志**：HBuilderX 控制台
3. **相关文档**：README.md、DEPLOYMENT.md

---

**启动脚本已修复，现在可以正常启动项目了！** 🚀
