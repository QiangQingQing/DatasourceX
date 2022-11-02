package com.dtstack.dtcenter.loader.client.neo4j40;

import com.dtstack.dtcenter.loader.ClassLoaderCallBack;
import com.dtstack.dtcenter.loader.ClassLoaderCallBackMethod;
import com.dtstack.dtcenter.loader.client.ClientFactory;
import com.dtstack.dtcenter.loader.client.INeo4j40;
import com.dtstack.dtcenter.loader.exception.DtLoaderException;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author wlq
 * @create 2022-10-31 16:02
 */
public class Neo4j40ClientFactory {
    public static INeo4j40 createPluginClass(String pluginName) throws Exception {
        ClassLoader classLoader = ClientFactory.getClassLoader(pluginName);
        return ClassLoaderCallBackMethod.callbackAndReset((ClassLoaderCallBack<INeo4j40>) () -> {
            ServiceLoader<INeo4j40> neo4j40 = ServiceLoader.load(INeo4j40.class);
            Iterator<INeo4j40> iClientIterator = neo4j40.iterator();
            if (!iClientIterator.hasNext()){
               throw new DtLoaderException("This plugin type is not supported: " + pluginName);
            }
            INeo4j40 iNeo4j40 = iClientIterator.next();
            return new Neo4j40Proxy(iNeo4j40);
        }, classLoader);
    }
}