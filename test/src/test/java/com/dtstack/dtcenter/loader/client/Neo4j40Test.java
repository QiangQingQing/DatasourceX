package com.dtstack.dtcenter.loader.client;

import com.dtstack.dtcenter.common.loader.neo4j40.Neo4j40ClientSpecial;
import com.dtstack.dtcenter.common.loader.neo4j40.Neo4j40Client;
import com.dtstack.dtcenter.loader.dto.source.Neo4j40SourceDTO;
import com.dtstack.dtcenter.loader.source.DataSourceType;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author wlq
 * @create 2022-10-27 9:14
 */
public class Neo4j40Test extends BaseTest {

    // 构建client
    private static final IClient client = ClientCache.getClient(DataSourceType.NEO4J40.getVal());
    private static final INeo4j40 client1 = ClientCache.getNeo4j40(DataSourceType.NEO4J40.getVal());
    // 构建Neo4j数据源信息
    private static final Neo4j40SourceDTO source = Neo4j40SourceDTO.builder()
            .uri("bolt://localhost:7687")
            .username("neo4j")
            .password("root")
            .build();

    /**
     * 连通性测试
     */
    @Test
    public void testConNeo4j40() {
        Boolean isConnected = client.testCon(source);
        if (Boolean.FALSE.equals(isConnected)) {
            System.out.println("false");
        }else {
            System.out.println("connection ok");
        }
    }
    /**
     * 测试直接输入语句执行命令
     */
    @Test
    public void testAllOrder(){
        //match (n) return count(n)
        String str= "MATCH (n) RETURN n";
        List<Map<String, Object>> result = client1.executeWhatever(source,str);
        Gson gson = new Gson();
        String record = gson.toJson(result);
        System.out.println(record);
        System.out.println("execute ok");
    }



}
