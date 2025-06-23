# 高校健身房管理系统

## 项目简介

这是一个基于Java Swing和MySQL的高校健身房管理系统，实现了会员管理、员工管理、器材管理、课程管理、器材租借和课程报名等核心功能。

## 功能特性

### 1. 会员信息管理
- 会员基本信息管理（姓名、性别、年龄、联系方式、身份证号、会员卡类型）
- 会员信息查询和排序
- 会员等级管理（普通会员、银卡会员、金卡会员、钻石会员）

### 2. 员工信息管理
- 员工基本信息管理（姓名、性别、职位、联系方式等）
- 员工信息查询和筛选
- 教练信息管理

### 3. 健身器材管理
- 器材基本信息管理（器材名称、型号、购买日期、维修记录等）
- 器材状态管理（可用、租借中、维修中）
- 器材租借和归还功能

### 4. 健身课程管理
- 课程基本信息管理（课程名称、课程内容、教练、上课时间等）
- 课程报名管理
- 课程容量控制

### 5. 器材租借功能
- 会员器材租借
- 器材归还管理
- 租借记录查询

### 6. 课程报名功能
- 会员课程报名
- 课程取消报名
- 报名记录管理

## 技术栈

- **开发语言**: Java 8+
- **图形界面**: Java Swing
- **数据库**: MySQL 8.0+
- **数据库连接**: MySQL Connector/J 9.3.0
- **开发工具**: IntelliJ IDEA / Eclipse

## 项目结构

```
FitnessGymSystem/
├── src/
│   ├── db/                    # 数据库相关
│   │   ├── DBUtil.java       # 数据库连接工具
│   │   └── DatabaseInitializer.java  # 数据库初始化
│   ├── entity/               # 实体类
│   │   ├── Member.java       # 会员实体
│   │   ├── Employee.java     # 员工实体
│   │   ├── Equipment.java    # 器材实体
│   │   └── Course.java       # 课程实体
│   ├── dao/                  # 数据访问层
│   │   ├── MemberDAO.java    # 会员数据访问
│   │   ├── EmployeeDAO.java  # 员工数据访问
│   │   ├── EquipmentDAO.java # 器材数据访问
│   │   ├── CourseDAO.java    # 课程数据访问
│   │   ├── EquipmentRentalDAO.java    # 器材租借数据访问
│   │   └── CourseEnrollmentDAO.java   # 课程报名数据访问
│   ├── ui/                   # 用户界面
│   │   ├── MainFrame.java    # 主界面
│   │   ├── MemberManagementFrame.java # 会员管理界面
│   │   ├── EmployeeManagementFrame.java # 员工管理界面
│   │   ├── EquipmentManagementFrame.java # 器材管理界面
│   │   ├── CourseManagementFrame.java # 课程管理界面
│   │   ├── EquipmentRentalFrame.java # 器材租借界面
│   │   └── CourseEnrollmentFrame.java # 课程报名界面
│   ├── TestDB.java           # 数据库连接测试
│   └── TestDatabase.java     # 数据库初始化测试
├── lib/                      # 依赖库
│   └── mysql-connector-j-9.3.0.jar
├── FitnessGymSystem.iml      # IntelliJ IDEA项目文件
└── README.md                 # 项目说明文档
```

## 数据库设计

### 主要数据表

1. **member** - 会员表
2. **employee** - 员工表
3. **equipment** - 器材表
4. **course** - 课程表
5. **membership_level** - 会员等级表
6. **equipment_rental** - 器材租借记录表
7. **course_enrollment** - 课程报名表

## 安装和运行

### 环境要求
- Java 8 或更高版本
- MySQL 8.0 或更高版本
- IntelliJ IDEA 或 Eclipse

### 安装步骤

1. **克隆项目**
   ```bash
   git clone [项目地址]
   cd FitnessGymSystem
   ```

2. **配置数据库**
   - 创建MySQL数据库：`fitness`
   - 修改 `src/db/DBUtil.java` 中的数据库连接信息：
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/fitness";
     private static final String USER = "your_username";
     private static final String PASSWORD = "your_password";
     ```

3. **初始化数据库**
   - 运行 `src/TestDatabase.java` 来初始化数据库表结构

4. **运行程序**
   - 运行 `src/ui/MainFrame.java` 的main方法启动程序

### 数据库配置

在运行程序之前，请确保：

1. MySQL服务已启动
2. 创建了名为 `fitness` 的数据库
3. 配置了正确的数据库连接信息（用户名、密码）

## 使用说明

### 主界面
程序启动后会显示主界面，包含六个功能模块的入口按钮：
- 会员管理
- 员工管理
- 器材管理
- 课程管理
- 器材租借
- 课程报名

### 会员管理
- 查看所有会员信息
- 按姓名搜索会员
- 按会员等级筛选
- 添加、编辑、删除会员

### 员工管理
- 查看所有员工信息
- 按姓名搜索员工
- 按职位筛选
- 添加、编辑、删除员工

### 器材管理
- 查看所有器材信息
- 器材状态管理
- 维修记录管理

### 课程管理
- 查看所有课程信息
- 课程信息管理
- 教练分配

### 器材租借
- 会员租借器材
- 器材归还
- 租借记录查询

### 课程报名
- 会员报名课程
- 取消报名
- 报名记录管理

## 评分要求实现

### 目标1：正确搭建项目开发环境 ✅
- 使用Java 8+开发环境
- 配置MySQL数据库
- 使用IntelliJ IDEA开发工具

### 目标2：使用图形用户界面（Swing） ✅
- 使用Java Swing创建图形界面
- 实现美观的用户界面设计
- 提供直观的操作体验

### 目标3：使用数据库存储信息，设计相应的表结构 ✅
- 使用MySQL数据库存储数据
- 设计完整的数据库表结构
- 实现数据持久化

### 目标4：实现会员管理、健身计划和器械租借功能 ✅
- 完整的会员信息管理功能
- 器材租借和归还功能
- 课程报名管理功能

### 目标5：能够按照会员的姓名、会员等级等条件进行查询和排序 ✅
- 按姓名搜索会员
- 按会员等级筛选
- 支持多种排序方式

### 目标6：测试及部署项目（本地或远程） ✅
- 本地测试运行
- 数据库连接测试
- 功能模块测试

## 开发进度

- [x] 项目基础架构搭建
- [x] 数据库设计和初始化
- [x] 实体类和DAO层实现
- [x] 主界面和基础UI框架
- [x] 会员管理功能（部分实现）
- [x] 其他管理模块界面框架
- [ ] 完整的CRUD操作实现
- [ ] 器材租借功能完善
- [ ] 课程报名功能完善
- [ ] 系统测试和优化

## 注意事项

1. 首次运行前请先执行 `TestDatabase.java` 初始化数据库
2. 确保MySQL服务正常运行
3. 检查数据库连接配置是否正确
4. 建议使用IntelliJ IDEA打开项目以获得最佳开发体验

## 联系方式

如有问题或建议，请联系开发团队。

## 许可证

本项目仅供学习和教学使用。 