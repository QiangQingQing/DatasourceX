package com.dtstack.dtcenter.loader.dto.source;

import com.dtstack.dtcenter.loader.exception.DtLoaderException;
import com.dtstack.dtcenter.loader.source.DataSourceType;
import lombok.*;

import java.sql.Connection;

/**
 * @author wlq
 * @create 2022-10-26 16:33
 */
@Data
@ToString
@Builder
@NoArgsConstructor    //添加一个无参数的构造器
@AllArgsConstructor  //使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
public class Neo4j40SourceDTO implements ISourceDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 路由
     */
    private String uri;

    @Override
    public Integer getSourceType() {
        return DataSourceType.NEO4J40.getVal();
    }

    @Override
    public Connection getConnection() {
        throw new DtLoaderException("The method is not supported");
    }

    @Override
    public void setConnection(Connection connection) {
        throw new DtLoaderException("The method is not supported");
    }
}
