## GoFDFS  client

### 一、插件包名称
名称：**GoFDFS**

### 二、对应数据源sourceDTO及参数说明

[GfdfsSourceDTO](/core/src/main/java/com/dtstack/dtcenter/loader/dto/source/GfdfsSourceDTO.java)

参数说明：


- **ip**
    - 描述：文件服务器的ip地址
    - 必选：是
    - 默认值：无



- **port**
    - 描述：文件服务器的端口号
    - 必选：是
    - 默认值：无



- **group**
    - 描述：文件服务器的组名
    - 必选：是
    - 默认值：无



- **scene**
    - 描述：存储场景
    - 必选：否
    - 默认值：default

    

#### 三、支持的方发及使用demo

##### IClient客户端使用

构造sourceDTO

```$java
         GfdfsSourceDTO source = GfdfsSourceDTO.builder()
                    .ip("x.x.x.x")
                    .port("xxxx")
                    .group("group1")
                    .build();
```

###### 1. 校验数据源连通性
入参类型：
- GfdfsSourceDTO：数据源连接信息

出参类型：
- Boolean：是否连通

使用：
```$java
        IClient client = ClientCache.getClient(DataSourceType.GFDFS.getVal());
        Boolean isConnected = client.testCon(sourceDTO);
```