/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtstack.dtcenter.loader.client;

import com.dtstack.dtcenter.loader.client.gfdfs.GfdfsClientFactory;
import com.dtstack.dtcenter.loader.client.hbase.HbaseClientFactory;
import com.dtstack.dtcenter.loader.client.hdfs.HdfsFileClientFactory;
import com.dtstack.dtcenter.loader.client.kerberos.KerberosClientFactory;
import com.dtstack.dtcenter.loader.client.mq.KafkaClientFactory;
import com.dtstack.dtcenter.loader.client.neo4j.Neo4jClientFactory;
import com.dtstack.dtcenter.loader.client.neo4j40.Neo4j40ClientFactory;
import com.dtstack.dtcenter.loader.client.redis.RedisClientFactory;
import com.dtstack.dtcenter.loader.client.restful.RestfulClientFactory;
import com.dtstack.dtcenter.loader.client.sql.DataSourceClientFactory;
import com.dtstack.dtcenter.loader.client.table.TableClientFactory;
import com.dtstack.dtcenter.loader.client.tsdb.TsdbClientFactory;
import com.dtstack.dtcenter.loader.exception.ClientAccessException;
import com.dtstack.dtcenter.loader.source.DataSourceType;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @company: www.dtstack.com
 * @Author ：Nanqi
 * @Date ：Created in 10:18 2020/1/13
 * 如果需要开启插件校验，请使用 startCheckFile 来校验文件
 * @Description：抽象客户端缓存
 */
@Slf4j
public class ClientCache {
    /**
     * Sql Client 客户端缓存
     */
    private static final Map<String, IClient> SQL_CLIENT = Maps.newConcurrentMap();

    /**
     * HDFS 文件客户端缓存
     */
    private static final Map<String, IHdfsFile> HDFS_FILE_CLIENT = Maps.newConcurrentMap();

    /**
     * KAFKA 客户端缓存
     */
    private static final Map<String, IKafka> KAFKA_CLIENT = Maps.newConcurrentMap();

    /**
     * Kerberos 认证服务客户端缓存
     */
    private static final Map<String, IKerberos> KERBEROS_CLIENT = Maps.newConcurrentMap();

    /**
     * hbase 服务客户端缓存
     */
    private static final Map<String, IHbase> HBASE_CLIENT = Maps.newConcurrentMap();

    /**
     * table 客户端缓存
     */
    private static final Map<String, ITable> TABLE_CLIENT = Maps.newConcurrentMap();

    /**
     * tsdb 客户端缓存
     */
    private static final Map<String, ITsdb> TSDB_CLIENT = Maps.newConcurrentMap();

    /**
     * restful 客户端缓存
     */
    private static final Map<String, IRestful> RESTFUL_CLIENT = Maps.newConcurrentMap();

    /**
     * redis 客户端缓存
     */
    private static final Map<String, IRedis> REDIS_CLIENT = Maps.newConcurrentMap();
    /**
     * GoFdfs 客户端缓存
     */
    private static final Map<String, IGfdfs> GFDFS_CLIENT = Maps.newConcurrentMap();

    /**
     * neo4j 客户端缓存
     */
    private static final Map<String,INeo4j> NEO4J_CLIENT = Maps.newConcurrentMap();
    /**
     * neo4j4.0+ 客户端缓存
     */
    private static final Map<String,INeo4j40> NEO4J40_CLIENT = Maps.newConcurrentMap();

    protected static String userDir = String.format("%s/pluginLibs/", System.getProperty("user.dir"));

    /**
     * 修改插件包文件夹路径
     *
     * @param dir
     */
    public static void setUserDir(String dir) {
        ClientCache.userDir = dir;
    }

    /**
     * 获取插件包文件夹路径
     *
     * @return
     */
    public static String getUserDir() {
        return userDir;
    }

    /**
     * 获取 Sql Client 客户端
     *
     * @param sourceType
     * @return
     * @throws ClientAccessException
     */
    public static IClient getClient(Integer sourceType) throws ClientAccessException {
        String pluginName = DataSourceType.getSourceType(sourceType).getPluginName();
        return getClient(pluginName);
    }

    /**
     * 获取 Sql Client 客户端
     *
     * @param pluginName
     * @return
     * @throws ClientAccessException
     */
    @Deprecated
    public static IClient getClient(String pluginName) throws ClientAccessException {
        try {
            IClient client = SQL_CLIENT.get(pluginName);
            if (client == null) {
                synchronized (SQL_CLIENT) {
                    client = SQL_CLIENT.get(pluginName);
                    if (client == null) {
                        client = DataSourceClientFactory.createPluginClass(pluginName);
                        SQL_CLIENT.put(pluginName, client);
                    }
                }
            }

            return client;
        } catch (Throwable e) {
            throw new ClientAccessException(e);
        }
    }

    /**
     * 获取 HDFS 文件客户端
     *
     * @param sourceType
     * @return
     * @throws ClientAccessException
     */
    public static IHdfsFile getHdfs(Integer sourceType) throws ClientAccessException {
        String pluginName = DataSourceType.getSourceType(sourceType).getPluginName();
        return getHdfs(pluginName);
    }

    /**
     * 获取 HDFS 文件客户端
     *
     * @param pluginName
     * @return
     * @throws ClientAccessException
     */
    @Deprecated
    public static IHdfsFile getHdfs(String pluginName) throws ClientAccessException {
        try {
            IHdfsFile hdfsFile = HDFS_FILE_CLIENT.get(pluginName);
            if (hdfsFile == null) {
                synchronized (HDFS_FILE_CLIENT) {
                    hdfsFile = HDFS_FILE_CLIENT.get(pluginName);
                    if (hdfsFile == null) {
                        hdfsFile = HdfsFileClientFactory.createPluginClass(pluginName);
                        HDFS_FILE_CLIENT.put(pluginName, hdfsFile);
                    }
                }
            }

            return hdfsFile;
        } catch (Throwable e) {
            throw new ClientAccessException(e);
        }
    }

    /**
     * 获取 KAFKA 客户端
     *
     * @param sourceType
     * @return
     * @throws ClientAccessException
     */
    public static IKafka getKafka(Integer sourceType) throws ClientAccessException {
        String pluginName = DataSourceType.getSourceType(sourceType).getPluginName();
        return getKafka(pluginName);
    }


    /**
     * 获取 KAFKA 客户端
     *
     * @param pluginName
     * @return
     * @throws ClientAccessException
     */
    @Deprecated
    public static IKafka getKafka(String pluginName) throws ClientAccessException {
        try {
            IKafka kafka = KAFKA_CLIENT.get(pluginName);
            if (kafka == null) {
                synchronized (KAFKA_CLIENT) {
                    kafka = KAFKA_CLIENT.get(pluginName);
                    if (kafka == null) {
                        kafka = KafkaClientFactory.createPluginClass(pluginName);
                        KAFKA_CLIENT.put(pluginName, kafka);
                    }
                }
            }

            return kafka;
        } catch (Throwable e) {
            throw new ClientAccessException(e);
        }
    }

    /**
     * 获取 Kerberos 服务客户端
     *
     * @param sourceType
     * @return
     * @throws ClientAccessException
     */
    public static IKerberos getKerberos(Integer sourceType) throws ClientAccessException {
        String pluginName = DataSourceType.getSourceType(sourceType).getPluginName();
        return getKerberos(pluginName);
    }

    /**
     * 获取 Kerberos 服务客户端
     *
     * @param pluginName
     * @return
     * @throws ClientAccessException
     */
    private static IKerberos getKerberos(String pluginName) throws ClientAccessException {
        try {
            IKerberos kerberos = KERBEROS_CLIENT.get(pluginName);
            if (kerberos == null) {
                synchronized (KERBEROS_CLIENT) {
                    kerberos = KERBEROS_CLIENT.get(pluginName);
                    if (kerberos == null) {
                        kerberos = KerberosClientFactory.createPluginClass(pluginName);
                        KERBEROS_CLIENT.put(pluginName, kerberos);
                    }
                }
            }

            return kerberos;
        } catch (Throwable e) {
            throw new ClientAccessException(e);
        }
    }

    /**
     * 获取 hbase 服务客户端
     *
     * @param sourceType 数据源类型
     * @return hbase新客户端
     * @throws ClientAccessException 插件化加载异常
     */
    public static IHbase getHbase(Integer sourceType) throws ClientAccessException {
        String pluginName = DataSourceType.getSourceType(sourceType).getPluginName();
        return getHbase(pluginName);
    }

    /**
     * 获取 hbase 服务客户端
     *
     * @param pluginName 数据源插件包名称
     * @return hbase新客户端
     * @throws ClientAccessException 插件化加载异常
     */
    private static IHbase getHbase(String pluginName) throws ClientAccessException {
        try {
            IHbase hbase = HBASE_CLIENT.get(pluginName);
            if (hbase == null) {
                synchronized (HBASE_CLIENT) {
                    hbase = HBASE_CLIENT.get(pluginName);
                    if (hbase == null) {
                        hbase = HbaseClientFactory.createPluginClass(pluginName);
                        HBASE_CLIENT.put(pluginName, hbase);
                    }
                }
            }
            return hbase;
        } catch (Throwable e) {
            throw new ClientAccessException(e);
        }
    }

    /**
     * 获取 table Client 客户端
     *
     * @param sourceType
     * @return
     * @throws ClientAccessException
     */
    public static ITable getTable(Integer sourceType) throws ClientAccessException {
        String pluginName = DataSourceType.getSourceType(sourceType).getPluginName();
        return getTable(pluginName);
    }

    private static ITable getTable(String pluginName) {
        try {
            ITable table = TABLE_CLIENT.get(pluginName);
            if (table == null) {
                synchronized (TABLE_CLIENT) {
                    table = TABLE_CLIENT.get(pluginName);
                    if (table == null) {
                        table = TableClientFactory.createPluginClass(pluginName);
                        TABLE_CLIENT.put(pluginName, table);
                    }
                }
            }

            return table;
        } catch (Throwable e) {
            throw new ClientAccessException(e);
        }
    }

    /**
     * 获取 tsdb Client 客户端
     *
     * @param sourceType 数据源类型
     * @return tsdb Client 客户端
     */
    public static ITsdb getTsdb(Integer sourceType) {
        String pluginName = DataSourceType.getSourceType(sourceType).getPluginName();
        return getTsdb(pluginName);
    }

    private static ITsdb getTsdb(String pluginName) {
        try {
            ITsdb tsdb = TSDB_CLIENT.get(pluginName);
            if (tsdb == null) {
                synchronized (TSDB_CLIENT) {
                    tsdb = TSDB_CLIENT.get(pluginName);
                    if (tsdb == null) {
                        tsdb = TsdbClientFactory.createPluginClass(pluginName);
                        TSDB_CLIENT.put(pluginName, tsdb);
                    }
                }
            }

            return tsdb;
        } catch (Throwable e) {
            throw new ClientAccessException(e);
        }
    }

    /**
     * 获取 restful Client 客户端
     *
     * @param sourceType 数据源类型
     * @return restful Client 客户端
     */
    public static IRestful getRestful(Integer sourceType) {
        String pluginName = DataSourceType.getSourceType(sourceType).getPluginName();
        return getRestful(pluginName);
    }

    private static IRestful getRestful(String pluginName) {
        try {
            IRestful restful = RESTFUL_CLIENT.get(pluginName);
            if (restful == null) {
                synchronized (RESTFUL_CLIENT) {
                    restful = RESTFUL_CLIENT.get(pluginName);
                    if (restful == null) {
                        restful = RestfulClientFactory.createPluginClass(pluginName);
                        RESTFUL_CLIENT.put(pluginName, restful);
                    }
                }
            }
            return restful;
        } catch (Throwable e) {
            throw new ClientAccessException(e);
        }
    }

    public static IRedis getRedis(Integer sourceType) {
        String pluginName = DataSourceType.getSourceType(sourceType).getPluginName();
        return getRedis(pluginName);
    }

    private static IRedis getRedis(String pluginName) {
        try {
            IRedis iRedis = REDIS_CLIENT.get(pluginName);
            if (iRedis == null) {
                synchronized (REDIS_CLIENT) {
                    iRedis = REDIS_CLIENT.get(pluginName);
                    if (iRedis == null) {
                        iRedis = RedisClientFactory.createPluginClass(pluginName);
                        REDIS_CLIENT.put(pluginName, iRedis);
                    }
                }
            }
            return iRedis;
        } catch (Throwable e) {
            throw new ClientAccessException(e);
        }
    }
    /**
     * 获取 Go-fastdfs Client 客户端
     *
     * @param sourceType 数据源类型
     * @return restful Client 客户端
     */
    public static IGfdfs getGfdfs(Integer sourceType) {
        String pluginName = DataSourceType.getSourceType(sourceType).getPluginName();
        return getGfdfs(pluginName);
    }

    private static IGfdfs getGfdfs(String pluginName) {
        try {
            IGfdfs iGfdfs = GFDFS_CLIENT.get(pluginName);
            if (iGfdfs == null) {
                synchronized (GFDFS_CLIENT) {
                    iGfdfs = GFDFS_CLIENT.get(pluginName);
                    if (iGfdfs == null) {
                        iGfdfs = GfdfsClientFactory.createPluginClass(pluginName);
                        GFDFS_CLIENT.put(pluginName, iGfdfs);
                    }
                }
            }
            return iGfdfs;
        } catch (Throwable e) {
            throw new ClientAccessException(e);
        }
    }

    /**
     *
     * @param sourceType 数据源类型
     * @return Neo4j Client 客户端
     * @throws ClientAccessException
     */
    public static INeo4j getNeo4j(Integer sourceType) throws ClientAccessException{
        String pluginName = DataSourceType.getSourceType(sourceType).getPluginName();
        return getNeo4j(pluginName);
    }

    private static INeo4j getNeo4j(String pluginName) throws ClientAccessException{
        try {
            INeo4j iNeo4j = NEO4J_CLIENT.get(pluginName);
            if (iNeo4j == null) {
                synchronized (NEO4J_CLIENT){
                    iNeo4j = NEO4J_CLIENT.get(pluginName);
                    if (iNeo4j == null) {
                        iNeo4j = Neo4jClientFactory.createPluginClass(pluginName);
                        NEO4J_CLIENT.put(pluginName, iNeo4j);
                    }
                }
            }
            return iNeo4j;
        }catch(Throwable e){
            throw new ClientAccessException(e);
        }
    }
    /**
     *
     * @param sourceType 数据源类型
     * @return Neo4j4.0+ Client 客户端
     * @throws ClientAccessException
     */
    public static INeo4j40 getNeo4j40(Integer sourceType) throws ClientAccessException{
        String pluginName = DataSourceType.getSourceType(sourceType).getPluginName();
        return getNeo4j40(pluginName);
    }

    private static INeo4j40 getNeo4j40(String pluginName) throws ClientAccessException{
        try {
            INeo4j40 iNeo4j40 = NEO4J40_CLIENT.get(pluginName);
            if (iNeo4j40 == null) {
                synchronized (NEO4J40_CLIENT){
                    iNeo4j40 = NEO4J40_CLIENT.get(pluginName);
                    if (iNeo4j40 == null) {
                        iNeo4j40 = Neo4j40ClientFactory.createPluginClass(pluginName);
                        NEO4J40_CLIENT.put(pluginName, iNeo4j40);
                    }
                }
            }
            return iNeo4j40;
        }catch(Throwable e){
            throw new ClientAccessException(e);
        }
    }
}
