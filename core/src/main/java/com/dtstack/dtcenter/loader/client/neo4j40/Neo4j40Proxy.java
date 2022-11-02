package com.dtstack.dtcenter.loader.client.neo4j40;

import com.dtstack.dtcenter.loader.ClassLoaderCallBackMethod;
import com.dtstack.dtcenter.loader.client.INeo4j40;
import com.dtstack.dtcenter.loader.dto.source.ISourceDTO;

import java.util.List;
import java.util.Map;

/**
 * @author wlq
 * @create 2022-10-31 16:03
 */
public class Neo4j40Proxy implements INeo4j40 {
    private INeo4j40 targetClient = null;

    public Neo4j40Proxy(INeo4j40 targetClient) {
        this.targetClient = targetClient;
    }

    @Override
    public Boolean testCon(ISourceDTO source) {
        return ClassLoaderCallBackMethod.callbackAndReset(() -> targetClient.testCon(source),
                targetClient.getClass().getClassLoader());
    }

    @Override
    public List<Map<String, Object>> executeWhatever(ISourceDTO source, String str) {
        return ClassLoaderCallBackMethod.callbackAndReset(() -> targetClient.executeWhatever(source, str),
                targetClient.getClass().getClassLoader());
    }
}
