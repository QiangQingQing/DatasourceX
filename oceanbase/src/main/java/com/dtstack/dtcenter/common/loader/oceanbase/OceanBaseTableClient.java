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

package com.dtstack.dtcenter.common.loader.oceanbase;

import com.dtstack.dtcenter.common.loader.rdbms.AbsTableClient;
import com.dtstack.dtcenter.common.loader.rdbms.ConnFactory;
import com.dtstack.dtcenter.loader.dto.UpsertColumnMetaDTO;
import com.dtstack.dtcenter.loader.dto.source.ISourceDTO;
import com.dtstack.dtcenter.loader.dto.source.RdbmsSourceDTO;
import com.dtstack.dtcenter.loader.exception.DtLoaderException;
import com.dtstack.dtcenter.loader.source.DataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @company: www.dtstack.com
 * @Author ：qianyi
 * @Date ：Created in 14:18 2021/4/21
 */
@Slf4j
public class OceanBaseTableClient extends AbsTableClient {

    // 获取表占用存储sql
    private static final String TABLE_SIZE_SQL = "select (data_length + index_length) as table_size from information_schema.tables where TABLE_SCHEMA = '%s' and TABLE_NAME = '%s'";

    //新增表字段
    private static final String ADD_COLUMN_SQL = "ALTER TABLE %s ADD COLUMN %s %s COMMENT '%s'";


    @Override
    protected ConnFactory getConnFactory() {
        return new OceanBaseConnFactory();
    }

    @Override
    protected DataSourceType getSourceType() {
        return DataSourceType.OceanBase;
    }

    @Override
    public List<String> showPartitions(ISourceDTO source, String tableName) {
        throw new DtLoaderException("The data source does not support get partition operation！");
    }

    /**
     * 更改表相关参数，暂时只支持更改表注释
     * @param source 数据源信息
     * @param tableName 表名
     * @param params 修改的参数，map集合
     * @return 执行结果
     */
    @Override
    public Boolean alterTableParams(ISourceDTO source, String tableName, Map<String, String> params) {
        String comment = params.get("comment");
        log.info("update table comment，comment：{}！", comment);
        if (StringUtils.isEmpty(comment)) {
            return true;
        }
        String alterTableParamsSql = String.format("alter table %s comment '%s'", tableName, comment);
        return executeSqlWithoutResultSet(source, alterTableParamsSql);
    }

    @Override
    protected String getTableSizeSql(String schema, String tableName) {
        if (StringUtils.isBlank(schema)) {
            throw new DtLoaderException("schema is not empty");
        }
        return String.format(TABLE_SIZE_SQL, schema, tableName);
    }


    protected Boolean addTableColumn(ISourceDTO source, UpsertColumnMetaDTO columnMetaDTO) {
        RdbmsSourceDTO rdbmsSourceDTO = (RdbmsSourceDTO) source;
        String schema = StringUtils.isNotBlank(columnMetaDTO.getSchema()) ? columnMetaDTO.getSchema() : rdbmsSourceDTO.getSchema();
        String comment = StringUtils.isNotEmpty(columnMetaDTO.getColumnComment()) ? columnMetaDTO.getColumnComment() : "";
        String sql = String.format(ADD_COLUMN_SQL, transferSchemaAndTableName(schema, columnMetaDTO.getTableName()), columnMetaDTO.getColumnName(), columnMetaDTO.getColumnType(), comment);
        return executeSqlWithoutResultSet(source, sql);
    }
}
