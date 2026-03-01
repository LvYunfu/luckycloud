# AI陪练系统

## 项目简介

这是一个基于Spring AI的AI陪练项目，主要用于帮助运营人员进行客户面谈培训。系统通过大模型扮演不同类型的刁难客户，让运营人员在模拟环境中提升沟通技巧和服务能力。

## 核心功能

### 1. 动态客户画像设置
- 支持自定义客户基本信息（姓名、年龄、职业等）
- 可配置客户特征（收入水平、消费习惯、痛点问题）
- 支持多种沟通风格（直接型、委婉型、激进型等）
- 可调节刁难程度（1-10级）

### 2. 多模型切换支持
- 支持多种AI模型（GPT系列、豆包等）
- 运行时动态切换模型
- 模型可用性检测

### 3. 多轮会话管理
- 完整的对话历史记录
- 会话状态跟踪
- 会话超时管理
- 支持会话暂停和恢复

### 4. 智能回复测评机制
- 多维度评估（回复质量、问题解决能力、沟通技巧、耐心程度）
- 自动生成详细反馈建议
- 量化评分系统（1-100分）
- 个性化评估标准

### 5. 系统健壮性保障
- 完善的异常处理机制
- 参数校验和边界检查
- 会话生命周期管理
- 日志记录和监控

## 技术架构

### 核心组件

```
org.luckycloud.ai
├── controller          # 控制层
│   └── TrainingController.java     # 培训接口控制器
├── service             # 服务层
│   ├── AiTrainingService.java      # 陪练服务接口
│   ├── EvaluationService.java      # 测评服务接口
│   └── impl                        # 服务实现
│       ├── AiTrainingServiceImpl.java
│       └── EvaluationServiceImpl.java
├── domain              # 领域模型
│   ├── CustomerProfile.java        # 客户画像
│   ├── TrainingSession.java        # 培训会话
│   └── EvaluationResult.java       # 测评结果
├── dto                 # 数据传输对象
│   ├── TrainingRequest.java        # 培训请求
│   └── TrainingResponse.java       # 培训响应
├── enums               # 枚举类
│   └── AiModelEnum.java           # AI模型枚举
├── data                # 示例数据
│   └── SampleProfiles.java        # 示例客户画像
├── utils               # 工具类
│   └── SessionManager.java        # 会话管理器
└── exception           # 异常处理
    └── GlobalExceptionHandler.java # 全局异常处理器
```

## API接口说明

### 1. 开始新会话
```
POST /api/training/start
Content-Type: application/json

{
    "userId": "user001",
    "customerProfile": {
        "customerName": "张总",
        "age": 35,
        "gender": "男",
        "occupation": "IT公司部门经理",
        "incomeLevel": "HIGH",
        "consumptionHabits": ["注重性价比", "喜欢对比"],
        "painPoints": ["产品价格偏高", "售后服务不明确"],
        "communicationStyle": "ANALYTICAL",
        "difficultyLevel": 8
    },
    "aiModel": "DOUBAO_SEED",
    "userMessage": "您好，我想了解一下你们的产品"
}
```

### 2. 继续会话
```
POST /api/training/continue/{sessionId}?userMessage=具体消息&needEvaluation=true
```

### 3. 结束会话
```
POST /api/training/end/{sessionId}
```

### 4. 获取会话详情
```
GET /api/training/session/{sessionId}
```

### 5. 获取用户会话列表
```
GET /api/training/sessions/user/{userId}
```

### 6. 获取可用模型列表
```
GET /api/training/models
```

### 7. 切换AI模型
```
POST /api/training/switch-model/{sessionId}?modelId=doubao-seed-1-8-251228
```

## 快速开始

### 1. 环境准备
- JDK 17+
- Maven 3.8+
- Spring Boot 3.x

### 2. 配置API密钥
在 `application-ai.yml` 中配置你的OpenAI API密钥：
```yaml
spring:
  ai:
    openai:
      api-key: your-api-key-here
```

### 3. 启动服务
```bash
cd cloud-ai
mvn spring-boot:run
```

### 4. 测试接口
服务启动后访问：http://localhost:8080/api/training/health

## 使用示例

### Python客户端示例
```python
import requests
import json

# 开始新会话
response = requests.post('http://localhost:8080/api/training/start', 
                        json={
                            "userId": "trainer001",
                            "customerProfile": {
                                "customerName": "李女士",
                                "age": 28,
                                "gender": "女",
                                "occupation": "自媒体博主",
                                "incomeLevel": "MEDIUM",
                                "communicationStyle": "EMOTIONAL",
                                "difficultyLevel": 6
                            },
                            "aiModel": "DOUBAO_SEED",
                            "userMessage": "你好，我对你们的产品有些疑问"
                        })

result = response.json()
session_id = result['sessionId']
print(f"会话ID: {session_id}")
print(f"AI回复: {result['aiResponse']}")

# 继续对话
response = requests.post(
    f'http://localhost:8080/api/training/continue/{session_id}',
    params={
        'userMessage': '能便宜点吗？',
        'needEvaluation': True
    }
)

result = response.json()
print(f"AI回复: {result['aiResponse']}")
if 'evaluations' in result:
    for eval in result['evaluations']:
        print(f"测评维度: {eval['dimension']}, 分数: {eval['score']}")
```

## 测评维度说明

### 1. 回复质量 (RESPONSE_QUALITY)
评估标准：相关性、准确性、完整性
- 80-100分：回复高度相关，信息准确完整
- 60-79分：回复基本相关，信息较为准确
- 40-59分：回复相关性一般，存在少量错误
- 0-39分：回复偏离主题，信息不准确

### 2. 问题解决能力 (PROBLEM_SOLVING)
评估标准：针对性、实用性、创新性
- 80-100分：解决方案精准有效，具有创新性
- 60-79分：解决方案实用，能够解决问题
- 40-59分：解决方案一般，效果有限
- 0-39分：解决方案不切实际，无法解决问题

### 3. 沟通技巧 (COMMUNICATION_SKILL)
评估标准：适应性、亲和力、说服力
- 80-100分：沟通自然流畅，极具说服力
- 60-79分：沟通良好，具有一定说服力
- 40-59分：沟通基本顺畅，说服力一般
- 0-39分：沟通生硬，缺乏说服力

### 4. 耐心程度 (PATIENCE_LEVEL)
评估标准：容忍度、回应态度、情绪控制
- 80-100分：极度耐心，始终保持专业态度
- 60-79分：比较耐心，能够控制情绪
- 40-59分：耐心一般，偶尔表现出不耐烦
- 0-39分：缺乏耐心，态度急躁

## 系统配置

### application-ai.yml 主要配置项：
```yaml
ai-training:
  session-timeout: 30          # 会话超时时间（分钟）
  max-sessions-per-user: 10    # 单用户最大会话数
  evaluation-enabled: true     # 是否启用测评功能
  log-level: INFO             # 日志级别

ai-models:                     # 模型配置
  - id: gpt-3.5-turbo
    name: GPT-3.5 Turbo
    enabled: true
    timeout: 30000
```

## 性能优化建议

1. **会话存储优化**
   - 生产环境建议使用Redis替代内存存储
   - 设置合理的会话过期时间
   - 实现会话持久化机制

2. **模型调用优化**
   - 实现模型调用缓存
   - 设置合理的超时时间
   - 实现熔断降级机制

3. **测评性能优化**
   - 批量处理测评请求
   - 缓存常用测评模板
   - 异步执行耗时测评

## 故障排除

### 常见问题

1. **API调用失败**
   - 检查API密钥是否正确配置
   - 确认网络连接正常
   - 查看日志中的错误详情

2. **测评功能异常**
   - 确认测评服务是否正常启动
   - 检查模型调用是否超时
   - 验证客户画像数据完整性

3. **会话管理问题**
   - 检查会话存储是否正常
   - 确认会话ID是否有效
   - 查看会话状态是否正确

## 贡献指南

欢迎提交Issue和Pull Request来改进这个项目！

## 许可证

MIT License