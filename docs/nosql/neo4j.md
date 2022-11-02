## Neo4j client

### 一、插件包名称
名称：**Neo4j**

### 二、对应数据源sourceDTO及参数说明

[Neo4jSourceDTO](/core/src/main/java/com/dtstack/dtcenter/loader/dto/source/Neo4jSourceDTO.java)

[Neo4j40SourceDTO](/core/src/main/java/com/dtstack/dtcenter/loader/dto/source/Neo4j40SourceDTO.java)

参数说明：


- **username**
    - 描述：数据源的用户名
    - 必选：是
    - 默认值：无



- **password**
    - 描述：数据源指定用户名的密码
    - 必选：是
    - 默认值：无



- **uri**
    - 描述：数据源的路由
    - 必选：是
    - 默认值：无

    

#### 三、支持的方发及使用demo

##### IClient客户端使用

构造sourceDTO

```$java
    Neo4jSourceDTO source = Neo4jSourceDTO.builder()
            .uri("bolt://x.x.x.x:7687")
            .username("xxx")
            .password("xxxx")
            .build();
```
```$java
    Neo4j40SourceDTO source = Neo4j40SourceDTO.builder()
            .uri("bolt://x.x.x.x:7687")
            .username("xxx")
            .password("xxxx")
            .build();
```

###### 1. 校验数据源连通性
入参类型：
- ESSourceDTO：数据源连接信息

出参类型：
- Boolean：是否连通

使用：
```$java
        IClient client = ClientCache.getClient(DataSourceType.NEO4J.getVal());
        Boolean isConnected = client.testCon(sourceDTO);
```
```$java
        IClient client = ClientCache.getClient(DataSourceType.NEO4J40.getVal());
        Boolean isConnected = client.testCon(sourceDTO);
```
